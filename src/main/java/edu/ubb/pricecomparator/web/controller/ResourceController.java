package edu.ubb.pricecomparator.web.controller;

import edu.ubb.pricecomparator.web.dto.incoming.UploadResourceDTO;
import edu.ubb.pricecomparator.infrastructure.csv.CsvParsingService;
import edu.ubb.pricecomparator.application.useCase.UploadDiscountsUseCase;
import edu.ubb.pricecomparator.application.useCase.UploadProductPricesUseCase;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/resources")
public class ResourceController {
    private UploadProductPricesUseCase uploadProductPricesUseCase;
    private UploadDiscountsUseCase uploadDiscountsUseCase;
    private final CsvParsingService csvParsingService;

    @PostMapping("/product-prices/upload")
    public ResponseEntity<?> uploadCsv(@RequestParam("file") MultipartFile file) {
        if (csvParsingService.isInvalidCsv(file)) {
            return ResponseEntity.badRequest().body("Invalid file. Please upload a CSV.");
        }

        try {
            UploadResourceDTO uploadResourceDTO = csvParsingService.parse(file);

            uploadProductPricesUseCase.uploadProductPrices(uploadResourceDTO);

            return ResponseEntity.ok().build();
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Failed to parse CSV file.");
        }
    }

    @PostMapping("/discounts/upload")
    public ResponseEntity<?> uploadDiscounts(@RequestParam("file") MultipartFile file) {
        if (csvParsingService.isInvalidCsv(file)) {
            return ResponseEntity.badRequest().body("Invalid file. Please upload a CSV.");
        }

        try {
            UploadResourceDTO uploadResourceDTO = csvParsingService.parse(file);

            uploadDiscountsUseCase.uploadDiscounts(uploadResourceDTO);

            return ResponseEntity.ok().build();
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Failed to parse CSV file.");
        }
    }
}
