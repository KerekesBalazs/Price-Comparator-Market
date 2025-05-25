package edu.ubb.pricecomparator.web.dto.incoming;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class UploadResourceDTO {
    private List<Map<String, String>> data;
    private String fileName;
}
