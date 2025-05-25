package edu.ubb.pricecomparator.web.controller;

import edu.ubb.pricecomparator.application.mapper.ProductMapper;
import edu.ubb.pricecomparator.application.useCase.BasketOptimizationUseCase;
import edu.ubb.pricecomparator.domain.model.Product;
import edu.ubb.pricecomparator.web.dto.outgoing.GetProductDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/baskets")
public class BasketController {
    private BasketOptimizationUseCase basketOptimizationUseCase;
    private ProductMapper productMapper;

    @PostMapping("/optimize")
    public ResponseEntity<?> getOptimizedBasket(@RequestBody List<Long> productIds) {
        try {
            Map<String, List<Product>> list = basketOptimizationUseCase.getOptimizedBasket(productIds);

            log.info(list.toString());
            Map<String, List<GetProductDTO>> dtoMap = list.entrySet().stream()
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            entry -> entry.getValue().stream()
                                    .map(productMapper::toDto)
                                    .collect(Collectors.toList())
                    ));

            return ResponseEntity.ok(dtoMap);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Server Error");
        }
    }
}
