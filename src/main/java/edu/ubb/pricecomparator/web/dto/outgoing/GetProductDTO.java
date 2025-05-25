package edu.ubb.pricecomparator.web.dto.outgoing;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class GetProductDTO {
    private long id;
    private String name;
    private String category;
    private String brand;
    private Double packageQuantity;
    private String packageUnit;
}
