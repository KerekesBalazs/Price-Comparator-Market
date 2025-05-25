package edu.ubb.pricecomparator.application.useCase;

import edu.ubb.pricecomparator.domain.model.Discount;

import java.time.LocalDate;
import java.util.List;

public interface GetNewDiscountsUseCase {
    List<Discount> getNewDiscounts(LocalDate date);
}
