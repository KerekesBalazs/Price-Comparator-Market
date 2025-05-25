package edu.ubb.pricecomparator.service;

import edu.ubb.pricecomparator.dto.incoming.UploadResourceDTO;
import edu.ubb.pricecomparator.model.Store;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ResourceService implements UploadProductPricesUseCase, UploadDiscountsUseCase {

    @Override
    public void uploadProductPrices(UploadResourceDTO request) {
        log.info(request.getFileName());

    }

    @Override
    public void uploadDiscounts(UploadResourceDTO request) {

    }

    private Store getStoreFromTheFileName(String fileName) {
        String storeName = fileName.split("_")[0];
        
    }
}
