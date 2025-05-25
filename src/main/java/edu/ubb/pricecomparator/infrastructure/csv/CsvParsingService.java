package edu.ubb.pricecomparator.infrastructure.csv;

import edu.ubb.pricecomparator.web.dto.incoming.UploadResourceDTO;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

@Component
public class CsvParsingService {

    public UploadResourceDTO parse(MultipartFile file) throws IOException {
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
        }

        return new UploadResourceDTO(parsedData, file.getOriginalFilename());
    }

    public boolean isInvalidCsv(MultipartFile file) {
        return file.isEmpty() || !Objects.requireNonNull(file.getOriginalFilename()).endsWith(".csv");
    }
}