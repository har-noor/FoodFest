package ca.sheridancollege.kau11900.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@EnableWebSecurity
public class SecurityConfig {

		//Authorization
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http)
	       throws Exception {
		
		
		///////////////////////////////////////
		//Remove this code before production
		http.csrf().disable();
		http.headers().frameOptions().disable();
		///////////////////////////////////////
		
		
		http
		    .authorizeHttpRequests((authz) -> authz
		    		
		    		//Identify the mapping and the roles that can access it 
		    		//.antMatchers (Http Method, URL ). hasRole (specific role)
		    		//.antMatchers (Http Method, URL ). hasAnyRole (any role)
		    		
		    		.antMatchers(HttpMethod.GET, "/add").hasRole("VENDOR")
		    		.antMatchers(HttpMethod.POST, "/add").hasRole("VENDOR")
		    		.antMatchers(HttpMethod.GET, "/register").permitAll()
		    		.antMatchers(HttpMethod.POST, "/register").permitAll()
		    		.antMatchers(HttpMethod.GET, "/edit/{id}").hasRole("VENDOR")
		    		.antMatchers(HttpMethod.GET, "/delete/{id}").hasRole("VENDOR")
		    		.antMatchers(HttpMethod.GET, "/view").hasAnyRole("VENDOR", "GUEST")
		    		
		    		//Pages that we don't require a login 
		    		//.antMatchers(HttpMethod, URL).permitAll()
		    		
		    		.antMatchers(HttpMethod.GET, "/").permitAll()
		    		.antMatchers("/h2-console/**").permitAll()
		    		.antMatchers("/css/**", "/images/**").permitAll()
		    		.anyRequest().authenticated()
		    		)
		    
		            .formLogin()
		            	.loginPage("/Login")//Mapping in the controller
		            	.permitAll()
		            .and()
		            	.logout()
		            		.invalidateHttpSession(true)
		            		.clearAuthentication(true)
		            		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		            		.logoutSuccessUrl("/?logout")
		
		            .and()
		            	.exceptionHandling()
		            		.accessDeniedHandler(accessDeniedHandler);
		     return http.build();
	}
	
	@Autowired
	LoginAccessDeniedHandler accessDeniedHandler;
	
		//Authentication
	
	
	
	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Bean
	public AuthenticationManager authManager(HttpSecurity http)
	throws Exception {
	return http.getSharedObject(AuthenticationManagerBuilder.class)
	.userDetailsService(userDetailsService)
	.passwordEncoder(passwordEncoder)
	.and()
	.build();
	}
	
	
//	@Autowired
//	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception
//	{
//		auth.inMemoryAuthentication()
//		.passwordEncoder(NoOpPasswordEncoder.getInstance())
//		.withUser("Noor").password("123").roles("USER")
//		.and()
//		.withUser("Test").password("123").roles("USER", "ADMIN");	
//	}
	
	
}