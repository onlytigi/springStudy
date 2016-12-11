package com.onlytigi.common.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


/**
 * Security Config
 * @author tIgI
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	/*
	 * user authentication manager 
	 *  <security:authentication-manager>
     *   <security:authentication-provider>
     *       <security:user-service>
     *           <security:user name="user" password="password" authorities="ROLE_USER" />
     *       </security:user-service>
     *   </security:authentication-provider>
     *	</security:authentication-manager>
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication() .withUser("user").password("password").roles("USER")
		.and().withUser("admin").password("admin").roles("ADMIN");
	}	

	/**
	 * Security 권한체크에 해당하지 않도록 설정을 합니다.
	 * 해당 항목은 Security 4 에서 부터 적용이 됩니다.
	 * <security:http pattern="/resources/**" security="none" />
  	*/	
    @Override
    public void configure(WebSecurity web) throws Exception {
    	web.ignoring()
           .antMatchers("/resources/**");
    }

 /**
  * <security:http auto-config="true">
  *
  *	  <security:intercept-url pattern="/favicon.ico" access="permitAll" />
  *   <security:intercept-url pattern="/" access="permitAll" />
  *   <security:intercept-url pattern="/admin/**" access="hasRole('ADMIN')" />
  *   <security:intercept-url pattern="/**" access="IS_AUTHENTICATED_ANONYMOUSLY />
  *
  *   <security:csrf disabled="false"/>
  *
  *   <security:form-login
  *    login-page="/security/loginPage"
  *    login-processing-url="/login"
  *    username-parameter="username"
  *    password-parameter="password"
  *    default-target-url="/"/>
  *
  *   <security:logout logout-url="/logout" logout-success-url="/"/>
  *   <security:access-denied-handler error-page="/security/error"/>
  * </security:http>
  */    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
      http
        //.csrf().disable() // csrf 관련 설정 (보안관련 설정)
          .authorizeRequests()
          .antMatchers("/favicon.ico", "/").permitAll() // 모든 권한 허용
          .antMatchers("/admin/**").hasAnyRole("ADMIN") // "ADMIN"권한 허용
          .anyRequest().authenticated() // 여타 다른주소들은 "USER"권한 체크 
          .and()
          .formLogin()  // 로그인 form
        //.usernameParameter("username") // form내 권한명 파라미터 name (default : username)
        //.passwordParameter("password") // form내 비밀번호 파라미터 name (default : password)
          .loginPage("/security/loginPage") // 권한이 필요한 페이지 접근시 로그인 페이지로 리다이렉
          .loginProcessingUrl("/login") // 로그인 url
          .permitAll()  // 로그인 페이지는 모든 권한 허용 
          .and()
          .logout() // 로그아웃 설정 
          .logoutUrl("/logout") // 로그아웃 url
          .logoutSuccessUrl("/") // 로그아웃 성공후 이동 url
          .permitAll() // 로그아웃 모든 권한 허용 
          .and()
          .exceptionHandling().accessDeniedPage("/security/error"); // 권한 획득 관련 에러시 이동 url
    }
}
