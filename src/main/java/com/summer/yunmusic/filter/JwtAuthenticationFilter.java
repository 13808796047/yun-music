package com.summer.yunmusic.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.summer.yunmusic.config.SecurityConfig;
import com.summer.yunmusic.entity.User;
import com.summer.yunmusic.exception.BizException;
import com.summer.yunmusic.exception.ExceptionEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

/**
 * 验证过滤器
 * 这个类扩展了UsernamePasswordAuthenticationFilter，它是 Spring Security 中密码认证的默认类。我们对其进行扩展以定义我们的自定义身份验证逻辑。
 * 我们覆盖了UsernameAuthenticationFilter类的尝试认证和成功认证方法。
 * <p>
 * 当用户尝试登录我们的应用程序时，就会运行attemptAuthentication函数。它读取凭据，从中创建用户 POJO，然后检查凭据以进行身份​​验证。
 * <p>
 * 我们传递用户名、密码和一个空列表。空列表表示权限（角色），我们将其保留原样，因为我们的应用程序中还没有任何角色。
 * <p>
 * 如果身份验证成功，则运行successfulAuthentication方法。该方法的参数由 Spring Security 在后台传递。
 * <p>
 * attemptAuthentication方法返回一个Authentication对象，其中包含我们在尝试时传递的权限。
 * <p>
 * 我们希望在身份验证成功后向用户返回一个令牌，因此我们使用用户名、密码和到期日期创建令牌。我们现在需要定义SECRET和EXPIRATION_DATE
 *
 * @author Summer
 * @since 2022/4/18 2:05
 */
@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            User user = new ObjectMapper()
                    .readValue(request.getInputStream(), User.class);
//            log.info("用户是{}", user);
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getUsername(),
                            user.getPassword(),
                            new ArrayList<>()
                    )
            );
        } catch (IOException e) {
            throw new BizException(ExceptionEnum.FORBIDDEN);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        log.info("create token");
        String token = JWT.create()
                .withSubject(((User) authResult.getPrincipal()).getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConfig.EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(SecurityConfig.SECRET.getBytes()));
        response.addHeader(SecurityConfig.HEADER_STRING, SecurityConfig.TOKEN_PREFIX + token);
    }
}