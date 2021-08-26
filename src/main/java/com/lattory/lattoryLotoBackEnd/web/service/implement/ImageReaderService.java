package com.lattory.lattoryLotoBackEnd.web.service.implement;

import com.lattory.lattoryLotoBackEnd.core.dto.JsonObject;
import com.lattory.lattoryLotoBackEnd.web.dao.ImageReaderDao;
import com.lattory.lattoryLotoBackEnd.web.service.ImageReaderInterface;
import org.springframework.stereotype.Service;

@Service
public class ImageReaderService implements ImageReaderInterface {
    final ImageReaderDao imageReaderDao;
    ImageReaderService(ImageReaderDao imageReaderDao) {
        this.imageReaderDao = imageReaderDao;
    }
    @Override
    public JsonObject inquiryResourcesID(JsonObject param) {
        return this.imageReaderDao.inquiryResourcesID(param);
    }
}
