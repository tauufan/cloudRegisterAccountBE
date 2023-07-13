package id.app.register.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }
     
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
         
        return authProvider;
    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }
    
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//            .anyRequest().authenticated()
//            .and()
//            .formLogin().permitAll()
//            .and()
//            .logout().permitAll();
//    }
    
	// Create 2 users for demo
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//
//        auth.inMemoryAuthentication()
//                .withUser("user").password("{noop}user").roles("USER")
//                .and()
//                .withUser("admin").password("{noop}admin").roles("USER", "ADMIN");
//
//    }

    // Secure the endpoins with HTTP Basic authentication
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        	http.csrf().disable()
        		.formLogin().disable()
        		.httpBasic()
                .and()
                .authorizeRequests()
//                .antMatchers(HttpMethod.GET, "/api/**").hasRole("ADMIN")
//                .antMatchers(HttpMethod.POST, "/api").hasRole("ADMIN")
//                .antMatchers(HttpMethod.PUT, "/api/**").hasRole("ADMIN")
//                .antMatchers(HttpMethod.PATCH, "/api/**").hasRole("ADMIN")
//                .antMatchers(HttpMethod.DELETE, "/api/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

//    	http.csrf().disable()
//		.authorizeRequests().anyRequest().authenticated()
//		.and().httpBasic()
//		.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    /*@Bean
    public UserDetailsService userDetailsService() {
        //ok for demo
        User.UserBuilder users = User.withDefaultPasswordEncoder();

        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(users.username("user").password("password").roles("USER").build());
        manager.createUser(users.username("admin").password("password").roles("USER", "ADMIN").build());
        return manager;
    }*/

}