package com.summer.yunmusic.config;

import com.summer.yunmusic.exception.RestAuthenticationEntryPoint;
import com.summer.yunmusic.filter.JwtAuthorizationFilter;
import com.summer.yunmusic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

/**
 * 安全配置类
 * 我们用@EnableWebSecurity注释这个类并扩展WebSecurityConfigureAdapter来实现我们的自定义安全逻辑。
 * <p>
 * 我们自动装配我们之前定义的 BCrypt bean。我们还自动装配以UserService查找用户的帐户。
 * <p>
 * 最重要的方法是接受HttpSecurity对象的方法。在这里，我们指定要应用的安全端点和过滤器。我们配置 CORS，然后我们允许所有发布请求到我们在常量类中定义的注册 URL。
 * <p>
 * 您可以添加其他 ant 匹配器以根据 URL 模式和角色进行过滤，您可以查看此 StackOverflow 问题以获取有关示例。另一种方法将AuthenticationManager配置为在检查凭据时使用我们的编码器对象作为其密码编码器。
 *
 * @author Summer
 * @since 2022/4/18 1:59
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    public static final String SECRET = "YuanLiMusic";
    public static final long EXPIRATION_TIME = 864_000_000; // 10 days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";

    public static final String SIGN_UP_URL = "/users";
    public static final String AUTHORIZATION_URL = "/api/authorizations";

    UserService userService;

    RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers(AUTHORIZATION_URL).permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilter(new JwtAuthorizationFilter(authenticationManager()))
                .exceptionHandling()
                .authenticationEntryPoint(restAuthenticationEntryPoint)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/swagger**/**")//
                .antMatchers("/webjars/**")//
                .antMatchers("/v3/**")//
                .antMatchers("/doc.html")
                .antMatchers("/weixin/**");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setRestAuthenticationEntryPoint(RestAuthenticationEntryPoint restAuthenticationEntryPoint) {
        this.restAuthenticationEntryPoint = restAuthenticationEntryPoint;
    }
}