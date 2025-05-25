package edu.ubb.pricecomparator.application.service;

import edu.ubb.pricecomparator.application.useCase.GetNewDiscountsUseCase;
import edu.ubb.pricecomparator.application.useCase.GetAllProductsWithMaxDiscountUseCase;
import edu.ubb.pricecomparator.domain.model.Discount;
import edu.ubb.pricecomparator.domain.model.Product;
import edu.ubb.pricecomparator.domain.repository.DiscountRepository;
import edu.ubb.pricecomparator.domain.repository.ProductRepository;
import edu.ubb.pricecomparator.infrastructure.utils.Pair;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class DiscountService implements GetNewDiscountsUseCase, GetAllProductsWithMaxDiscountUseCase {
    private final DiscountRepository discountRepository;
    private final ProductRepository productRepository;

    @Override
    public List<Discount> getNewDiscounts(LocalDate date) {
        return discountRepository.findNewDiscountsSince(date);
    }

    @Override
    public List<Pair<Product, Discount>> getAllProductsWithMaxDiscountUseCase() {
        List<Product> products = productRepository.findAll();
        List<Pair<Product, Discount>> results = new ArrayList<>();
        for (Product product : products) {
            // Here need LocalDate.now(), but the data dont contain discount like this range
            results.add(new Pair<>(product, discountRepository.findMaximumActiveDiscountByProductId(product.getId(), LocalDate.of(2025,5,2))));
        }
        return results;
    }
}