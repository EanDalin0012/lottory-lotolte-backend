package com.lattory.lattoryLotoBackEnd.web.rest;

import com.lattory.lattoryLotoBackEnd.core.constant.MessageCode;
import com.lattory.lattoryLotoBackEnd.core.constant.Status;
import com.lattory.lattoryLotoBackEnd.core.constant.StatusCode;
import com.lattory.lattoryLotoBackEnd.core.dto.Header;
import com.lattory.lattoryLotoBackEnd.core.dto.JsonObject;
import com.lattory.lattoryLotoBackEnd.core.dto.ResponseData;
import com.lattory.lattoryLotoBackEnd.core.exception.ValidatorException;
import com.lattory.lattoryLotoBackEnd.web.service.implement.Base64WriteImageService;
import com.lattory.lattoryLotoBackEnd.web.util.Base64ImageUtil;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping(value = "/api/base64/image/v0")
public class Base64WriteImageRest {
    private static final Logger log = LoggerFactory.getLogger(Base64WriteImageRest.class);
    final Base64WriteImageService base64WriteImageService;

    public Base64WriteImageRest(Base64WriteImageService base64WriteImageService) {
        this.base64WriteImageService = base64WriteImageService;
    }

    @PostMapping(value = "/write")
    public ResponseData<JsonObject> index(@RequestBody JsonObject jsonObject, @RequestParam("userID") int userID, @RequestParam("lang") String lang) {
        ResponseData responseData = new ResponseData();
        Header header = new Header(StatusCode.success, MessageCode.success);
        try {
            String path = "/uploads/products";
            String base64 = jsonObject.getString("base64");
            String id = jsonObject.getString("id");
            String fileExtension = jsonObject.getString("file_extension");

            log.info("path:" + path + ",id" + id + ",name:" + id + ",extension:" + fileExtension);

            String basePath = Base64ImageUtil.decodeToImage(path, base64, id, fileExtension);
            JsonObject input = new JsonObject();

            if (!basePath.equals("")) {
                log.info("data:" + jsonObject);
                input.setString("id", id);
                input.setString("file_name", id);
                input.setInt("file_size", jsonObject.getInt("file_size"));
                input.setString("file_type", jsonObject.getString("file_type"));
                input.setString("file_extension", jsonObject.getString("file_extension"));
                input.setString("original_name", jsonObject.getString("file_name"));
                input.setString("file_source", basePath);
                input.setString("status", Status.active);
                input.setInt("user_id", userID);
                int save = base64WriteImageService.save(input);
                if (save > 0) {
                    // output.setString("status", "Y");
                }
            } else {
                log.info("can not write image");
//                ErrorMessage message = message(ErrorCode.EXCEPTION_ERR, lang);
//                responseData.setError(message);
            }

            log.info("base64:" + base64);


            log.info("jsonObject"+responseData);
        }catch (Exception | ValidatorException e) {
            e.printStackTrace();
        }
        return responseData;
    }

}
