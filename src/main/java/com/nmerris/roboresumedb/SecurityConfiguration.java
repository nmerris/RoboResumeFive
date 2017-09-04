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

    // uncommment this to disable security for testing
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
                // always allow access to all our static folders
                .antMatchers("/css/**", "/js/**", "/fonts/**", "/img/**")
                    .permitAll()
                .antMatchers("/add*", "/startover", "/editdetails", "/delete/*", "/update/*", "/finalresume",
                        "/course*", "/student*", "/summary")
                    .access("hasRole('ROLE_USER')")
                .anyRequest()
                    .authenticated()
                .and().formLogin().loginPage("/login")
                    .permitAll()
                .and().httpBasic() // allows authentication in the URL itself
                // go back to the login page after user logs out
                .and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().
                withUser("user").password("pass").roles("USER");
    }

}