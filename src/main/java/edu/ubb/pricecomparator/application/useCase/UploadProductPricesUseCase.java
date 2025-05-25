package edu.ubb.pricecomparator.application.useCase;

import edu.ubb.pricecomparator.web.dto.incoming.UploadResourceDTO;

public interface UploadProductPricesUseCase {
    void uploadProductPrices(UploadResourceDTO request);
}