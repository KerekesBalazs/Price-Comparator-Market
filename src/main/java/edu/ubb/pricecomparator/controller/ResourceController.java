package edu.ubb.pricecomparator.controller;

import edu.ubb.pricecomparator.dto.incoming.UploadResourceDTO;
import edu.ubb.pricecomparator.service.UploadDiscountsUseCase;
import edu.ubb.pricecomparator.service.UploadProductPricesUseCase;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/resources")
public class ResourceController {
    private UploadProductPricesUseCase uploadProductPricesUseCase;
    private UploadDiscountsUseCase uploadDiscountsUseCase;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadCsv(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty() || !Objects.requireNonNull(file.getOriginalFilename()).endsWith(".csv")) {
            return ResponseEntity.badRequest().body("Invalid file. Please upload a CSV.");
        }

        List<Map<String, String>> parsedData = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
             CSVParser parser = CSVFormat.DEFAULT.builder()
                     .setHeader()
                     .setSkipHeaderRecord(true)
                     .setDelimiter(';')
                     .build()
                     .parse(reader)) {

            for (CSVRecord record : parser) {
                Map<String, String> row = new HashMap<>();
                for (String header : parser.getHeaderNames()) {
                    row.put(header, record.get(header));
                }
                parsedData.add(row);
            }

            UploadResourceDTO uploadResourceDTO = new UploadResourceDTO(parsedData, file.getOriginalFilename());

            uploadProductPricesUseCase.uploadProductPrices(uploadResourceDTO);

            return ResponseEntity.ok("");
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Failed to parse CSV file.");
        }
    }
}
