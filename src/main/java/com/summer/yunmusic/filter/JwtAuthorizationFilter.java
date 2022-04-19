package com.summer.yunmusic.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.summer.yunmusic.config.SecurityConfig;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * doFilterInternal方法拦截请求然后检查 Authorization 标头。如果标头不存在或不以“BEARER”开头，则进入过滤器链。
 * <p>
 * 如果标头存在，则调用getAuthentication方法。getAuthentication验证 JWT，如果令牌有效，则返回 Spring 将在内部使用的访问令牌。
 * <p>
 * 然后将此新令牌保存到 SecurityContext。如果您需要基于角色的授权，您还可以将 Authorities 传递给此令牌。
 * <p>
 * 我们的过滤器已经准备就绪，现在我们需要在配置类的帮助下将它们付诸行动。
 *
 * @author Summer
 * @since 2022/4/18 2:22
 */
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(SecurityConfig.HEADER_STRING);

        if (header == null || !header.startsWith(SecurityConfig.TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authenticationToken = getAuthentication(header);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String header) {
        if (header != null) {
            String username = JWT.require(Algorithm.HMAC512(SecurityConfig.SECRET.getBytes()))
                    .build()
                    .verify(header.replace(SecurityConfig.TOKEN_PREFIX, ""))
                    .getSubject();
            if (username != null) {
                return new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());
            }
        }
        return null;
    }


}