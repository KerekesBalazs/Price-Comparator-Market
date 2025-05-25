package edu.ubb.pricecomparator.service;

import edu.ubb.pricecomparator.dto.incoming.UploadResourceDTO;

public interface UploadProductPricesUseCase {
    void uploadProductPrices(UploadResourceDTO request);
}