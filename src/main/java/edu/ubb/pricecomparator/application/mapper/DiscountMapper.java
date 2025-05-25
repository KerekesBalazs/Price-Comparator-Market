package edu.ubb.pricecomparator.application.mapper;

import edu.ubb.pricecomparator.domain.model.Discount;
import edu.ubb.pricecomparator.web.dto.outgoing.GetDiscountDTO;
import org.springframework.stereotype.Component;

@Component
public class DiscountMapper {
    public GetDiscountDTO toDto(Discount discount) {
        if (discount == null) return null;

        return new GetDiscountDTO(
                discount.getId(),
                discount.getProduct() != null ? String.valueOf(discount.getProduct().getId()) : null,
                discount.getStore() != null ? discount.getStore().getName() : null,
                discount.getFromDate(),
                discount.getToDate(),
                discount.getPercentageOfDiscount(),
                discount.getCreatedAt()
        );
    }
}