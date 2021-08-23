package com.lattory.lattoryLotoBackEnd.web.service.implement;

import com.lattory.lattoryLotoBackEnd.core.dto.JsonObject;
import com.lattory.lattoryLotoBackEnd.core.exception.ValidatorException;
import com.lattory.lattoryLotoBackEnd.web.dao.Base64WriteImageDao;
import com.lattory.lattoryLotoBackEnd.web.service.Base64WriteImageInterface;
import org.springframework.stereotype.Service;

@Service
public class Base64WriteImageService implements Base64WriteImageInterface {

    final Base64WriteImageDao base64WriteImageDao;

    /**
     * @param base64WriteImageDao
     */
    public Base64WriteImageService(Base64WriteImageDao base64WriteImageDao) {
        this.base64WriteImageDao = base64WriteImageDao;
    }

    @Override
    public int save(JsonObject param) throws ValidatorException {
        return 0;
    }

    @Override
    public int delete(JsonObject param) throws ValidatorException {
        return 0;
    }

    @Override
    public int update(JsonObject param) throws ValidatorException {
        return 0;
    }

    @Override
    public String getResourcesImageById(JsonObject param) throws ValidatorException {
        return null;
    }
}
