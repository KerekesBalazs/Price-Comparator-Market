package edu.ubb.pricecomparator.web.dto.outgoing;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class GetDiscountDTO {
    private long id;
    private String productId;
    private String storeName;
    private LocalDate fromDate;
    private LocalDate toDate;
    private Double percentageOfDiscount;
    private LocalDate createdAt;
}
