package edu.ubb.pricecomparator.application.useCase;

import edu.ubb.pricecomparator.domain.model.Product;

import java.util.List;
import java.util.Map;

public interface BasketOptimizationUseCase {
    Map<String, List<Product>> getOptimizedBasket(List<Long> productIds);
}
