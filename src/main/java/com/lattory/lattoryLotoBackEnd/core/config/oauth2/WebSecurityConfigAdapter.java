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
import org.springframework.security.config.annotation.authentication.configurers.provisioning.JdbcUserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    @Bean("userPasswordEncoder")
    PasswordEncoder userPasswordEncoder() {
        return new BCryptPasswordEncoder(4);
    }


    @Autowired
    public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
        JdbcUserDetailsManagerConfigurer<AuthenticationManagerBuilder> cfg = auth.jdbcAuthentication()
                .passwordEncoder(userPasswordEncoder()).dataSource(ds);
        cfg.getUserDetailsService().setEnableGroups(true);
        cfg.getUserDetailsService().setEnableAuthorities(false);

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
                        "/api/image/**"
                        , "/unsecure/**"
                        , "/401.html"
                        , "/404.html"
                        , "/500.html");
    }
}
