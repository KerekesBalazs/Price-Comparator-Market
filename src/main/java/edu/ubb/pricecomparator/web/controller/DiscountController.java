package edu.ubb.pricecomparator.web.controller;

import edu.ubb.pricecomparator.application.mapper.DiscountMapper;
import edu.ubb.pricecomparator.application.mapper.ProductMapper;
import edu.ubb.pricecomparator.application.useCase.GetAllProductsWithMaxDiscountUseCase;
import edu.ubb.pricecomparator.application.useCase.GetNewDiscountsUseCase;
import edu.ubb.pricecomparator.domain.model.Discount;
import edu.ubb.pricecomparator.domain.model.Product;
import edu.ubb.pricecomparator.domain.repository.DiscountRepository;
import edu.ubb.pricecomparator.infrastructure.utils.Pair;
import edu.ubb.pricecomparator.web.dto.outgoing.GetDiscountDTO;
import edu.ubb.pricecomparator.web.dto.outgoing.ProductWithDiscountDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/discounts")
public class DiscountController {
    private final GetNewDiscountsUseCase getNewDiscountsUseCase;
    private final GetAllProductsWithMaxDiscountUseCase getAllProductsWithMaxDiscountUseCase;
    private final DiscountMapper discountMapper;
    private final ProductMapper productMapper;

    @GetMapping("/best")
    public ResponseEntity<List<ProductWithDiscountDTO>> getBestDiscounts() {
        try {
            List<Pair<Product, Discount>> bestDiscounts = getAllProductsWithMaxDiscountUseCase.getAllProductsWithMaxDiscountUseCase();

            var dtoList = bestDiscounts.stream()
                    .map(pair -> {
                        var productDto = productMapper.toDto(pair.getFirst());
                        var discountDto = discountMapper.toDto(pair.getSecond());
                        return new ProductWithDiscountDTO(productDto, discountDto);
                    })
                    .collect(Collectors.toList());

            return ResponseEntity.ok(dtoList);
        } catch (Exception e) {
            log.error("Failed to fetch best discounts", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/new")
    public ResponseEntity<List<GetDiscountDTO>> getNewDiscounts(
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate date
    ) {
        try {
            var newDiscounts = getNewDiscountsUseCase.getNewDiscounts(date);
            var dtoList = newDiscounts.stream()
                    .map(discountMapper::toDto)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(dtoList);
        } catch (Exception e) {
            log.error("Failed to fetch new discounts", e);
            return ResponseEntity.internalServerError().build();
        }
    }
}
