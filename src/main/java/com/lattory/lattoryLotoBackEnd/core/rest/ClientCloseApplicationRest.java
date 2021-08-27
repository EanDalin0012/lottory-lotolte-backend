package com.lattory.lattoryLotoBackEnd.core.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lattory.lattoryLotoBackEnd.core.dto.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/client/v0")
public class ClientCloseApplicationRest {
    private static final Logger log = LoggerFactory.getLogger(ClientCloseApplicationRest.class);
    @PostMapping(value = "/close")
    public void clientCloseApplication(@RequestBody JsonObject jsonObject, @RequestParam("lang") String lang) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            log.info("clientCloseApplication:"+objectMapper.writeValueAsString(jsonObject));
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
