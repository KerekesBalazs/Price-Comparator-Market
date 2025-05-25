package edu.ubb.pricecomparator.application.useCase;

import edu.ubb.pricecomparator.domain.model.Discount;
import edu.ubb.pricecomparator.domain.model.Product;
import edu.ubb.pricecomparator.infrastructure.utils.Pair;

import java.util.List;

public interface GetAllProductsWithMaxDiscountUseCase {
    List<Pair<Product, Discount>> getAllProductsWithMaxDiscountUseCase();
}
