package edu.ubb.pricecomparator.service;

import edu.ubb.pricecomparator.dto.incoming.UploadResourceDTO;

public interface UploadDiscountsUseCase {
    void uploadDiscounts(UploadResourceDTO request);
}
