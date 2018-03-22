package lab4.library;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //http.authorizeRequests().antMatchers("/resource/**", "/webapp/**", "/user/registrationform", "/user/registeruser", "/").permitAll().antMatchers("/console/").hasRole("ADMIN").anyRequest().authenticated();
        //http.formLogin().loginPage("/user/login").successForwardUrl("/success").permitAll();
        //http.authorizeRequests().antMatchers("/resource/**", "/webapp/**", "/user/registrationform", "/user/registeruser", "/").permitAll().antMatchers("/console/").hasRole("ADMIN").anyRequest().authenticated();
        http.authorizeRequests().antMatchers("/resource/**", "/webapp/**", "/user/registrationform", "/user/registeruser", "/", "/console/**").permitAll().anyRequest().authenticated();
        http.formLogin().loginPage("/user/login").permitAll();
        http.logout().permitAll();
        http.csrf().disable();
        http.headers().frameOptions().disable();
    }


    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private UserDetailsService userDetailsService;

    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(bCryptPasswordEncoder());
    }
}
