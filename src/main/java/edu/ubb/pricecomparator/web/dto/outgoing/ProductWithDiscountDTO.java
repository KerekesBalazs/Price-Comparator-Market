package edu.ubb.pricecomparator.web.dto.outgoing;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductWithDiscountDTO {
    private GetProductDTO product;
    private GetDiscountDTO discount;
}
