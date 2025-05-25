package edu.ubb.pricecomparator.application.mapper;

import edu.ubb.pricecomparator.domain.model.Product;
import edu.ubb.pricecomparator.web.dto.outgoing.GetProductDTO;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public GetProductDTO toDto(Product product) {
        if (product == null) return null;

        GetProductDTO dto = new GetProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setCategory(product.getCategory());
        dto.setBrand(product.getBrand());
        dto.setPackageQuantity(product.getPackageQuantity());
        dto.setPackageUnit(product.getPackageUnit());
        return dto;
    }
}