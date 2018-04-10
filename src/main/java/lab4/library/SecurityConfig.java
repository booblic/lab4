package lab4.library;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Class for security config
 * @author Кирилл
 * @version 1.0
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * Method configures the access restriction, setting loging page
     * @throws Exception
     * @param http - this object allows configuring web based security for specific http requests
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /*http.authorizeRequests().antMatchers("/resource/**", "/webapp/**",
                "/user/getregistrationform", "/user/registeruser",
                "/searchbookoptions",
                "/book/show", "/book/getsearchingbybooknameform", "/book/searchingbybookname",
                "/book/getformviewbook",
                "/book/genreandyearsearchingform", "/book/searchingbygenreandyear",
                "/book/authorandgenresearchingform", "/book/searhcingbyauthorandgenre",
                "/publisher/getsearchingbypublisherform", "/publisher/searchingbypublisher",
                "/genre/getsearchingbygenreform", "/genre/searchingbygenre",
                "/author/getsearchingbyauthorform", "/author/searchingbyauthor",
                "/console/**", "/").permitAll().anyRequest().authenticated();*/

        http
                .authorizeRequests().antMatchers("/resource/**", "/webapp/**",
                "/user/getregistrationform", "/user/registeruser",
                "/searchbookoptions",
                "/book/show", "/book/getsearchingbybooknameform", "/book/searchingbybookname",
                "/book/getformviewbook",
                "/book/genreandyearsearchingform", "/book/searchingbygenreandyear",
                "/book/authorandgenresearchingform", "/book/searhcingbyauthorandgenre",
                "/publisher/getsearchingbypublisherform", "/publisher/searchingbypublisher",
                "/genre/getsearchingbygenreform", "/genre/searchingbygenre",
                "/author/getsearchingbyauthorform", "/author/searchingbyauthor",
                "/console/**", "/").permitAll()
                .and()
                .authorizeRequests().antMatchers("/book/getaddform", "/book/addbook",
                "/book/getformedit", "/book/editbook",
                "/book/getsearchbookinternetform", "/book/searchbookinternet", "/book/addsearchingbook")
                .hasAnyRole("MODER", "ADMIN")
                .and().authorizeRequests().antMatchers("/user/getshowalluserform", "/user/getformedituserbyadmin", "/user/edituserbyadmin")
                .hasRole("ADMIN")
                .anyRequest()
                .authenticated();

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

    /**
     * Method configures authentication, setting userDetailsService and password encoder
     * @param auth - object for configures authentication
     */
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(bCryptPasswordEncoder());
    }
}
