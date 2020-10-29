package hu.bme.crysys.homework.pangolin.webshop.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import hu.bme.crysys.homework.pangolin.webshop.repository.BlackListedJwtRepository;

@RequiredArgsConstructor
public class JwtConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final JwtTokenProvider jwtTokenProvider;
    private final BlackListedJwtRepository blackListedJwtRepository;

    @Override
    public void configure(HttpSecurity http) {
        JwtTokenFilter customFilter = new JwtTokenFilter(jwtTokenProvider, blackListedJwtRepository);
        http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
    }

}
