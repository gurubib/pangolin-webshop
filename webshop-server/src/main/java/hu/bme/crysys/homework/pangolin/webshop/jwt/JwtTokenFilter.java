package hu.bme.crysys.homework.pangolin.webshop.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import hu.bme.crysys.homework.pangolin.webshop.model.BlackListedJwt;
import hu.bme.crysys.homework.pangolin.webshop.repository.BlackListedJwtRepository;

@RequiredArgsConstructor
public class JwtTokenFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;
    private final BlackListedJwtRepository blackListedJwtRepository;

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain)
            throws IOException, ServletException {
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) req);

        blackListedJwtRepository.deleteByExpireDateBefore(LocalDateTime.now());
        List<BlackListedJwt> blackListedTokens = blackListedJwtRepository.findByTokenEquals(token);

        if (token != null && jwtTokenProvider.validateToken(token) && blackListedTokens.isEmpty()) {
            Authentication auth = jwtTokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        filterChain.doFilter(req, res);
    }

}
