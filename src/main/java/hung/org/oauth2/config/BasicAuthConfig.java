package hung.org.oauth2.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@EnableWebSecurity
//@Profile("BasicAuth")
public class BasicAuthConfig extends WebSecurityConfigurerAdapter {
	          
	//@Value("${auth.basic.active-directory.url}")
	private String activeDirectoryUrl;

	//@Autowired
	//private UserDetailsContextMapper userdetailsContextMapper;
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.debug(true);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// @formatter:off
		http
		  .authorizeRequests()
		    .antMatchers("/error")
		      .permitAll()
			.antMatchers("/actuator/**")
			  .permitAll()			      
		    .anyRequest()
		      .authenticated()
		  .and()
		    //.httpBasic();
		    .formLogin();
		// @formatter:on
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth
		  .ldapAuthentication()
		    .contextSource()
		      .url("ldap://dockerce01:389/dc=example,dc=org")
		      .managerDn("cn=admin,dc=example,dc=org")
		      .managerPassword("passw0rd")
		    .and()
		      .userDnPatterns("cn={0},ou=users")
		      .groupSearchBase("ou=groups");
		//auth.ldapAuthentication().userDetailsContextMapper(userdetailsMapper());
		
		//ActiveDirectoryLdapAuthenticationProvider activedirectoryLdapAuthProvider =
		//	new ActiveDirectoryLdapAuthenticationProvider(activeDirectoryDomain,activeDirectoryUrl);
		
		//activedirectoryLdapAuthProvider.setUserDetailsContextMapper(userdetailsContextMapper);

		//auth.authenticationProvider(activedirectoryLdapAuthProvider);
	}
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return this.authenticationManager();
	}

	@Bean
	@Override
	public UserDetailsService userDetailsServiceBean() throws Exception {
		return this.userDetailsService();
	}
	
	//@Bean
	//public UserDetailsContextMapper userdetailsMapper() {
	//	ADUserDetailsMapper mapper = new ADUserDetailsMapper();
	//	return mapper;
	//}
}
