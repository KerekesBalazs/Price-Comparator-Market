package edu.ubb.pricecomparator.application.service;

import edu.ubb.pricecomparator.application.useCase.BasketOptimizationUseCase;
import edu.ubb.pricecomparator.domain.model.Discount;
import edu.ubb.pricecomparator.domain.model.PriceEntry;
import edu.ubb.pricecomparator.domain.model.Product;
import edu.ubb.pricecomparator.domain.repository.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@AllArgsConstructor
public class BasketService implements BasketOptimizationUseCase {
    private PriceEntryRepository priceEntryRepository;
    private DiscountRepository discountRepository;

    @Override
    public Map<String, List<Product>> getOptimizedBasket(List<Long> productIds) {
        Map<String, List<Product>> optimizedBasket = new HashMap<>();

        for (Long productId : productIds) {
            List<PriceEntry> prices = priceEntryRepository.findByProductId(productId);
            if (prices.isEmpty()) continue;

            double lowestFinalPrice = Double.MAX_VALUE;
            PriceEntry bestEntry = null;

            for (PriceEntry entry : prices) {
                double basePrice = entry.getPrice();

                // Get active discounts for this product-store pair
                Discount discount = discountRepository.findValidDiscountsByDate(
                        productId,
                        entry.getStore().getId(),
                        LocalDate.now()
                );

                double finalPrice = basePrice;

                if (discount != null) {
                    finalPrice = finalPrice * (1 - discount.getPercentageOfDiscount());
                }

                if (finalPrice < lowestFinalPrice) {
                    lowestFinalPrice = finalPrice;
                    bestEntry = entry;
                }
            }

            if (bestEntry != null) {
                String storeName = bestEntry.getStore().getName();
                Product product = bestEntry.getProduct();

                if (optimizedBasket.containsKey(storeName)) {
                    optimizedBasket.get(storeName).add(product);
                } else {
                    List<Product> products = new ArrayList<>();
                    products.add(product);
                    optimizedBasket.put(storeName, products);
                }
            }
        }

        return optimizedBasket;
    }
}
