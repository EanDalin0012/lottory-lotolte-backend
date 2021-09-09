package com.lattory.lattoryLotoBackEnd.core.service.implement;

import com.lattory.lattoryLotoBackEnd.core.dao.DefaultAuthenticationProviderDao;
import com.lattory.lattoryLotoBackEnd.core.dao.OauthAccessTokenDao;
import com.lattory.lattoryLotoBackEnd.core.dto.JsonObject;
import com.lattory.lattoryLotoBackEnd.core.service.DefaultAuthenticationProviderInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;

import java.util.Collection;
@Service
public class DefaultAuthenticationProviderService implements DefaultAuthenticationProviderInterface, UserDetailsService {
    private static final Logger log = LoggerFactory.getLogger(DefaultAuthenticationProviderService.class);

//    @Autowired
//    OauthAccessTokenDao oauthAccessTokenDao;

    @Autowired
    private TokenStore tokenStore;

    final DefaultAuthenticationProviderDao defaultAuthenticationProviderDao;
    DefaultAuthenticationProviderService(DefaultAuthenticationProviderDao defaultAuthenticationProviderDao) {
        this.defaultAuthenticationProviderDao = defaultAuthenticationProviderDao;

    }

    @Override
    public JsonObject getUserObjectByName(JsonObject param) throws Exception {
//        JsonObject tokenObj = oauthAccessTokenDao.getTokenByUserName(param);
//        byte d= tokenObj.get("token")
//        String s = new String(bytes, StandardCharsets.UTF_8);
        String clientId = "spring-security-oauth2-read-write-client";
        String userName = "admin@gmail.com";
//
        Collection<OAuth2AccessToken> data =  tokenStore.findTokensByClientIdAndUserName(clientId, userName);

        OAuth2AccessToken oAuth2AccessToken = data.iterator().next();
        tokenStore.removeAccessToken(oAuth2AccessToken);

        return defaultAuthenticationProviderDao.getUserByName(param);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            JsonObject input = new JsonObject();
            input.put("userName", username);
            JsonObject userInfo = defaultAuthenticationProviderDao.getUserByName(input);
            log.info("user info:"+ userInfo.toString());
            String userName = (String) userInfo.get("userName");
            String password = (String) userInfo.get("password");
            Collection<? extends GrantedAuthority> authorities = (Collection<? extends GrantedAuthority>) userInfo.get("authorities");
            if (userInfo != null) {
                UserDetails userDetails = User.builder()
                        .username(userName)
                        .password(password)
                        .authorities(authorities)
                        .build();
                return userDetails;
            }
            throw new UsernameNotFoundException("User not found_0");

        } catch (Exception e) {
            log.error("get error exception service loadUserByUsername:", e);
            throw e;
        }
    }
}
