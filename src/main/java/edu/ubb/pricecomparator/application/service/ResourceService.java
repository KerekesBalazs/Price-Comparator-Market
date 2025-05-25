package edu.ubb.pricecomparator.application.service;

import edu.ubb.pricecomparator.application.useCase.UploadDiscountsUseCase;
import edu.ubb.pricecomparator.application.useCase.UploadProductPricesUseCase;
import edu.ubb.pricecomparator.domain.repository.*;
import edu.ubb.pricecomparator.web.dto.incoming.UploadResourceDTO;
import edu.ubb.pricecomparator.domain.model.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Slf4j
@Service
@AllArgsConstructor
public class ResourceService implements UploadProductPricesUseCase, UploadDiscountsUseCase {
    private StoreRepository storeRepository;
    private ProductRepository productRepository;
    private PriceEntryRepository priceEntryRepository;
    private PriceEntryHistoryRepository priceEntryHistoryRepository;
    private DiscountRepository discountRepository;

    @Override
    public void uploadProductPrices(UploadResourceDTO request) {
        Store store = getStoreFromTheFileName(request.getFileName());

        LocalDate createDate = LocalDate.now();

        // To track and skip duplicate product IDs within the same file
        Set<Long> products = new HashSet<>();

        // Iterate over each row (product) in the uploaded CSV data
        for (Map<String, String> i : request.getData()) {

            // Extract numeric product ID from something like "P001"
            long productId = Long.parseLong(i.get("product_id").replaceAll("\\D", ""));

            // Skip if product was already processed in this file
            if (products.contains(productId)) {
                continue;
            } else {
                products.add(productId);
            }

            Product existingProduct = productRepository.findById(productId).orElse(null);

            if (existingProduct != null) {
                PriceEntry existingPriceEntry = priceEntryRepository.findByStoreIdAndProductId(
                                store.getId(),
                                productId)
                        .orElse(null);

                if (existingPriceEntry == null) {
                    // Create the new PriceEntry for the existing product, but new store
                    PriceEntry priceEntry = PriceEntry.builder()
                            .product(existingProduct)
                            .store(store)
                            .price(Double.parseDouble(i.get("price")))
                            .currency(i.get("currency"))
                            .createdAt(createDate)
                            .build();

                    priceEntryRepository.save(priceEntry);

                    continue;
                }

                // Get discounts valid between the time the existing price was created and now
                List<Discount> discounts = discountRepository.findValidDiscountsBetweenDates(
                        productId,
                        store.getId(),
                        existingPriceEntry.getCreatedAt(),
                        LocalDate.now()
                );

                // Generate a list of historical price entries based on applicable discounts
                List<PriceEntryHistory> priceEntryHistoryList = new ArrayList<>();
                for (Discount discount : discounts) {
                    PriceEntryHistory priceEntryHistory = PriceEntryHistory.builder()
                            .product(existingProduct)
                            .store(store)
                            .appliedDiscount(discount)
                            .price(existingPriceEntry.getPrice() * discount.getPercentageOfDiscount())
                            .currency(existingPriceEntry.getCurrency())
                            .from_date(discount.getFromDate())
                            .to_date(discount.getToDate())
                            .createdAt(createDate)
                            .build();

                    priceEntryHistoryList.add(priceEntryHistory);
                }

                // If there were no discounts, add a single historical entry covering the full range
                if (priceEntryHistoryList.isEmpty()) {
                    priceEntryHistoryList.add(
                            PriceEntryHistory.builder()
                                    .product(existingProduct)
                                    .store(store)
                                    .appliedDiscount(null)
                                    .price(existingPriceEntry.getPrice())
                                    .currency(existingPriceEntry.getCurrency())
                                    .from_date(existingPriceEntry.getCreatedAt())
                                    .to_date(LocalDate.now())
                                    .createdAt(createDate)
                                    .build()
                    );
                } else {
                    // Add missing beginning interval if the first discount starts after the original price entry
                    if (priceEntryHistoryList.getFirst().getFrom_date().isAfter(existingPriceEntry.getCreatedAt())) {
                        priceEntryHistoryList.addFirst(
                                PriceEntryHistory.builder()
                                        .product(existingProduct)
                                        .store(store)
                                        .appliedDiscount(null)
                                        .price(existingPriceEntry.getPrice())
                                        .currency(existingPriceEntry.getCurrency())
                                        .from_date(existingPriceEntry.getCreatedAt())
                                        .to_date(priceEntryHistoryList.getFirst().getFrom_date())
                                        .createdAt(createDate)
                                        .build());
                    }
                    // Add missing ending interval if the last discount ends before today
                    if (priceEntryHistoryList.getLast().getTo_date().isBefore(LocalDate.now())) {
                        priceEntryHistoryList.add(PriceEntryHistory.builder()
                                .product(existingProduct)
                                .store(store)
                                .appliedDiscount(null)
                                .price(existingPriceEntry.getPrice())
                                .currency(existingPriceEntry.getCurrency())
                                .from_date(priceEntryHistoryList.getLast().getTo_date())
                                .to_date(LocalDate.now())
                                .createdAt(createDate)
                                .build());
                    }
                }

                PriceEntry newPriceEntry = PriceEntry.builder()
                        .product(existingProduct)
                        .store(store)
                        .price(Double.parseDouble(i.get("price")))
                        .currency(i.get("currency"))
                        .createdAt(createDate)
                        .build();

                // Replace the old PriceEntry with the new one
                priceEntryRepository.save(newPriceEntry);
                priceEntryRepository.delete(existingPriceEntry);

                // Save all the generated historical price entries
                priceEntryHistoryRepository.saveAll(priceEntryHistoryList);
            } else {
                // If the product doesn't exist, build and save it first
                Product product = Product.builder()
                        .id(productId)
                        .name(i.get("product_name"))
                        .category(i.get("product_category"))
                        .brand(i.get("brand"))
                        .packageQuantity(Double.parseDouble(i.get("package_quantity")))
                        .packageUnit(i.get("package_unit"))
                        .build();

                // Create the new PriceEntry for the new product
                PriceEntry priceEntry = PriceEntry.builder()
                        .product(product)
                        .store(store)
                        .price(Double.parseDouble(i.get("price")))
                        .currency(i.get("currency"))
                        .createdAt(createDate)
                        .build();

                productRepository.save(product);
                priceEntryRepository.save(priceEntry);
            }
        }
    }

    @Override
    public void uploadDiscounts(UploadResourceDTO request) {
        Store store = getStoreFromTheFileName(request.getFileName());

        LocalDate createDate = LocalDate.now();

        // To track and skip duplicate product IDs within the same file
        Set<Long> products = new HashSet<>();

        // Iterate over each row (product) in the uploaded CSV data
        for (Map<String, String> i : request.getData()) {
            // Extract numeric product ID from something like "P001"
            long productId = Long.parseLong(i.get("product_id").replaceAll("\\D", ""));

            // Skip if product was already processed in this file
            if (products.contains(productId)) {
                continue;
            } else {
                products.add(productId);
            }

            Product existingProduct = productRepository.findById(productId).orElse(null);

            if (existingProduct != null) {
                LocalDate newFrom = LocalDate.parse(i.get("from_date"));
                LocalDate newTo = LocalDate.parse(i.get("to_date"));

                // Get discounts valid between the time the existing price was created and now
                List<Discount> discounts = discountRepository.findValidDiscountsBetweenDates(
                        productId,
                        store.getId(),
                        newFrom,
                        newTo
                );

                for (Discount discount : discounts) {
                    LocalDate existFrom = discount.getFromDate();
                    LocalDate existTo = discount.getToDate();

                    // Check full overlap (existing fully inside new)
                    if (!existFrom.isBefore(newFrom) && !existTo.isAfter(newTo)) {
                        // fully inside, delete
                        discountRepository.delete(discount);
                        discountRepository.save(Discount.builder()
                                .store(store)
                                .product(existingProduct)
                                .fromDate(newFrom)
                                .toDate(newTo)
                                .percentageOfDiscount(Double.parseDouble(i.get("percentage_of_discount")) / 100)
                                .createdAt(createDate)
                                .build()
                        );
                    }
                    // existing partially overlaps left
                    else if (existFrom.isBefore(newFrom) && (existTo.isAfter(newFrom) || existTo.equals(newFrom)) && (existTo.isBefore(newTo) || existTo.equals(newTo))) {
                        // modify existTo to newFrom
                        discount.setToDate(newFrom);
                        discountRepository.save(discount);
                        discountRepository.save(Discount.builder()
                                .store(store)
                                .product(existingProduct)
                                .fromDate(newFrom)
                                .toDate(newTo)
                                .percentageOfDiscount(Double.parseDouble(i.get("percentage_of_discount")) / 100)
                                .createdAt(createDate)
                                .build()
                        );
                    }
                    // existing partially overlaps right
                    else if ((!existFrom.isBefore(newFrom)) && existFrom.isBefore(newTo) && existTo.isAfter(newTo)) {
                        // modify existFrom to newTo
                        discount.setFromDate(newTo);
                        discountRepository.save(discount);
                        discountRepository.save(Discount.builder()
                                .store(store)
                                .product(existingProduct)
                                .fromDate(newFrom)
                                .toDate(newTo)
                                .percentageOfDiscount(Double.parseDouble(i.get("percentage_of_discount")) / 100)
                                .createdAt(createDate)
                                .build()
                        );
                    }
                    // existing fully contains new discount, need to split
                    else if (existFrom.isBefore(newFrom) && existTo.isAfter(newTo)) {
                        LocalDate pastToDate = discount.getToDate();

                        // modify existing to end at newFrom
                        discount.setToDate(newFrom);
                        discountRepository.save(discount);

                        discountRepository.save(Discount.builder()
                                .store(store)
                                .product(existingProduct)
                                .fromDate(newFrom)
                                .toDate(newTo)
                                .percentageOfDiscount(Double.parseDouble(i.get("percentage_of_discount")) / 100)
                                .createdAt(createDate)
                                .build()
                        );

                        discountRepository.save(Discount.builder()
                                .store(store)
                                .product(existingProduct)
                                .fromDate(newTo)
                                .toDate(pastToDate)
                                .percentageOfDiscount(discount.getPercentageOfDiscount())
                                .createdAt(createDate)
                                .build()
                        );
                    }
                }

                if (discounts.isEmpty()) {
                    discountRepository.save(Discount.builder()
                            .store(store)
                            .product(existingProduct)
                            .fromDate(newFrom)
                            .toDate(newTo)
                            .percentageOfDiscount(Double.parseDouble(i.get("percentage_of_discount")) / 100)
                            .createdAt(createDate)
                            .build()
                    );
                }
            } else {
                // Do nothing, because the product don't exist now.
            }
        }
    }

    private Store getStoreFromTheFileName(String fileName) {
        String storeName = fileName.split("_")[0];

        return storeRepository.findByName(storeName)
                .orElseGet(() -> {
                    Store newStore = new Store();
                    newStore.setName(storeName);
                    return storeRepository.save(newStore);
                });
    }
}
