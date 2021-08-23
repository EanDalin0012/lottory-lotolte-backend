package com.lattory.lattoryLotoBackEnd.core.config.oauth2;

import com.lattory.lattoryLotoBackEnd.core.encryption.Encoders;
import com.lattory.lattoryLotoBackEnd.core.provider.DefaultAuthenticationProvider;
import com.lattory.lattoryLotoBackEnd.core.service.implement.DefaultAuthenticationProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@Order(SecurityProperties.BASIC_AUTH_ORDER)
@Import(Encoders.class)
public class WebSecurityConfigAdapter extends WebSecurityConfigurerAdapter {
    @Autowired
    private DefaultAuthenticationProviderService appUserRepository;

    @Autowired
    DataSource ds;

    @Autowired
    public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
        // BCryptPasswordEncoder(4) is used for users.password column
//        JdbcUserDetailsManagerConfigurer<AuthenticationManagerBuilder> cfg = auth.jdbcAuthentication()
//                .passwordEncoder(new BCryptPasswordEncoder(4)).dataSource(ds);
//
//        cfg.getUserDetailsService().setEnableGroups(true);
//        cfg.getUserDetailsService().setEnableAuthorities(true);

        auth.authenticationProvider(new DefaultAuthenticationProvider(appUserRepository));
    }

    @Override
    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(WebSecurity webSecurity) throws Exception {
        webSecurity
                .ignoring()
                .antMatchers(
//                        "/api/web/**"
                        "/unsecure/**"
                        , "/401.html"
                        , "/404.html"
                        , "/500.html");
    }
}
