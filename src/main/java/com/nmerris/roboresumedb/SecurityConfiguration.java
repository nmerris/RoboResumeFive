package com.nmerris.roboresumedb;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .antMatchers("/**")
//                .permitAll()
//                .antMatchers("/add*", "/startover", "/editdetails", "/delete/*", "/update/*", "/finalresume")
//                .access("hasRole('ROLE_USER')")
//                .anyRequest()
//                .authenticated()
//                .and().formLogin().loginPage("/login")
//                .permitAll()
//                .and().httpBasic() // allows authentication in the URL itself
//                .and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login");
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/css/**", "/js/**", "/fonts/**", "/img/**")
                    .permitAll()
                .antMatchers("/add*", "/startover", "/editdetails", "/delete/*", "/update/*", "/finalresume")
                    .access("hasRole('ROLE_USER')")
                .anyRequest()
                    .authenticated()
                .and().formLogin().loginPage("/login")
                    .permitAll()
                .and().httpBasic() // allows authentication in the URL itself
                .and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().
//                withUser("newuser").password("newuserpa$$").roles("USER");
                withUser("user").password("pass").roles("USER");
    }

}