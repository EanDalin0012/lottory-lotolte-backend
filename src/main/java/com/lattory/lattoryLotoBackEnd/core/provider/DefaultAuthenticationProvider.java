package com.lattory.lattoryLotoBackEnd.core.provider;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lattory.lattoryLotoBackEnd.core.constant.DemoProperties;
import com.lattory.lattoryLotoBackEnd.core.dto.JsonObject;
import com.lattory.lattoryLotoBackEnd.core.dto.JsonObjectArray;
import com.lattory.lattoryLotoBackEnd.core.encryption.EncryptionUtil;
import com.lattory.lattoryLotoBackEnd.core.service.implement.DefaultAuthenticationProviderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.common.exceptions.ClientAuthenticationException;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class DefaultAuthenticationProvider implements AuthenticationProvider {
    private static final Logger log = LoggerFactory.getLogger(DefaultAuthenticationProvider.class);



    final DefaultAuthenticationProviderService userService;

    public DefaultAuthenticationProvider(DefaultAuthenticationProviderService userService) {
        this.userService = userService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.info("============== Start Authorization ===============");
        log.info("============== Authentication :"+authentication.getName());
        log.info("============== Authentication :"+authentication.getAuthorities());
        ObjectMapper objectMapper = new ObjectMapper();
        String encodedBase64Key = EncryptionUtil.encodeKey(DemoProperties.secretKey);
        String userName = EncryptionUtil.decrypt(authentication.getName(), encodedBase64Key);
        String password = EncryptionUtil.decrypt((String) authentication.getCredentials(), encodedBase64Key);
        Map<String, String> data = (Map<String, String>) authentication.getDetails();
        String deviceInfo = data.get("deviceInfo");
        String deviceInfoDecrypt = EncryptionUtil.decrypt(deviceInfo, encodedBase64Key).toString();

        try {
            JsonObject deviceInfoObj = new JsonObject();
            JsonObject input = new JsonObject();
            input.setString("userName", userName);

            if (deviceInfoDecrypt != null || deviceInfoDecrypt != "") {
                deviceInfoObj = objectMapper.readValue(deviceInfoDecrypt, JsonObject.class);
            }
            input.setJsonObject("deviceInfo", deviceInfoObj);

            JsonObject userInfo = userService.getUserObjectByName(input);

            if (userInfo == null) {
                log.info("============== Authorization User Not Found ===============");
                throw new UsernameNotFoundException("UserNameNotFound");
            }

            if (userInfo.getBoolean("accountLocked")) {
                log.info("============== User Account Locked ===============");
                throw new UsernameNotFoundException("UserLocked");
            }

            if (!userInfo.getBoolean("enabled")) {
                log.info("============== User Enabled False ===============");
                throw new UsernameNotFoundException("UserDisabled");
            }

            if (userInfo.getBoolean("accountExpired")) {
                log.info("============== User Account Expired ===============");
                throw new UsernameNotFoundException("UserExpired");
            }

            if (userInfo.getBoolean("credentialsExpired")) {
                log.info("============== User Account Credentials Expired ===============>>>>>>>>>>>>");
                throw new UsernameNotFoundException("UserExpired");
            }

            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String _password = userInfo.getString("password");

            boolean isPasswordMatch = passwordEncoder.matches(password, _password);
            System.out.println(isPasswordMatch);
            if (!isPasswordMatch) {
                throw new UsernameNotFoundException("InvalidPassword");
            }

            List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
            JsonObjectArray authorities = userInfo.getJsonObjectArray("authorities");
            for (JsonObject authority : authorities.toListData()) {
                grantedAuthorities.add(new SimpleGrantedAuthority(authority.getString("name")));
            }

            JsonObject param = new JsonObject();
            param.setString("user_name", authentication.getName());
//            userService.updateLoginSuccess(param);
            log.info("Apply keep login success");
//            isLoginSuccess(param);
            log.info("============== End Authorization ===============>>>>>>>>>>>>\n");
            return new UsernamePasswordAuthenticationToken(
                    userInfo.getString("username"),
                    userInfo.getString("password"),
                    grantedAuthorities);

        } catch (UsernameNotFoundException ex) {
            log.info("============== Get error user name not found exception ===============" + ex);
            throw new ClientAuthenticationException(ex.getMessage(), ex.getCause()) {
                @Override
                public String getOAuth2ErrorCode() {
                    return "400";
                }
            };
        } catch (Exception e) {
            log.error("*** get error class default authentication exception", e);
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
