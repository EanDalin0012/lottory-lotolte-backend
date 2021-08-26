package com.lattory.lattoryLotoBackEnd.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lattory.lattoryLotoBackEnd.core.dto.JsonObject;
import com.lattory.lattoryLotoBackEnd.core.util.SystemUtil;
import com.lattory.lattoryLotoBackEnd.web.service.implement.ImageReaderService;
import com.lattory.lattoryLotoBackEnd.web.util.ExistsFileUtil;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping(value = "/api/image/reader/v0")
public class ImageReaderRest {
    private static final Logger log = LoggerFactory.getLogger(ImageReaderRest.class);

    final ImageReaderService imageReaderService;
    ImageReaderRest(ImageReaderService imageReaderService) {
        this.imageReaderService = imageReaderService;
    }

    @GetMapping("/read/{resourceId}")
    public ResponseEntity<byte[]> resourcesImage(@PathVariable("resourceId") int resourceId) throws IOException {
        byte bytes[] = null;
        HttpHeaders headers = new HttpHeaders();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonObject input = new JsonObject();
            input.setInt("id", resourceId);
            JsonObject imgInfo = this.imageReaderService.inquiryResourcesID(input);

            log.info("file info values: " + objectMapper.writeValueAsString(imgInfo));

            if (imgInfo != null) {
                String path = imgInfo.getString("fileSource");
                String filepath = SystemUtil.projectPath() + path;
                String fileExt = imgInfo.getString("fileExtension");

                if (ExistsFileUtil.exists(filepath)) {
                    File file = new File(filepath);
                    InputStream targetStream = new FileInputStream(file);
                    bytes = IOUtils.toByteArray(targetStream);

                    if (fileExt.equalsIgnoreCase("JPG")) {
                        headers.setContentType(MediaType.IMAGE_JPEG);
                    } else if (fileExt.equalsIgnoreCase("PNG")) {
                        headers.setContentType(MediaType.IMAGE_PNG);
                    } else {
                        headers.setContentType(MediaType.IMAGE_PNG);
                    }
                    headers.setContentLength(bytes.length);
                    return new ResponseEntity<byte[]>(bytes, headers, HttpStatus.OK);
                } else {
                    return null;
                }
            }

        } catch (Exception e) {
            log.error("error read image", e);
        }
        return null;
    }

}
