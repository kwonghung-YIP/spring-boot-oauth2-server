package hung.org.oauth2.config;

import java.util.Arrays;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.approval.JdbcApprovalStore;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class OAuth2ServerConfig {
	
	@Configuration
	@EnableResourceServer
	protected static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

		@Autowired
		private RedisConnectionFactory redisConnectionFactory;
		
		@Override
		public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
			RedisTokenStore redisTokenStore = new RedisTokenStore(redisConnectionFactory);
			
			resources.tokenStore(redisTokenStore);
		}
		
		@Override
		public void configure(HttpSecurity http) throws Exception {
			// @formatter:off
			http
			  .antMatcher("/oauth/userinfo")
			    .authorizeRequests()
			      .anyRequest()
			        .authenticated();
			// @formatter:on
		}
		
	}
	
	@Configuration
	@EnableAuthorizationServer
	protected static class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {
		
		@Autowired
		private DataSource dataSource;
		
		@Autowired
		private RedisConnectionFactory redisConnectionFactory;

		@Autowired
		private AuthenticationManager authenticationManager;
		
		@Autowired
		private UserDetailsService userDetailsService;

		@Autowired
		private CorsConfigurationSource corsConfigurationSource;
		
		@Override
		public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
			CorsFilter corsFilter = new CorsFilter(this.corsConfigurationSource);
			
			// @formatter:off
			security
			  //Allow client_id & client_secret passed in form post data
			  .allowFormAuthenticationForClients();
			// @formatter:on
			
			//Add CORS to filter to allow cross-origin ajax preflight call to /oauth/token  
			security.addTokenEndpointAuthenticationFilter(corsFilter);
		}

		@Override
		public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
			JdbcAuthorizationCodeServices jdbcAuthCodeSvc = new JdbcAuthorizationCodeServices(dataSource);
			JdbcApprovalStore jdbcApplStore = new JdbcApprovalStore(dataSource);
			RedisTokenStore redisTokenStore = new RedisTokenStore(redisConnectionFactory);
			
			// @formatter:off
			//Reference: (Grant Types) https://projects.spring.io/spring-security-oauth/docs/oauth2.html			  
			endpoints
			  //Inject the authenticationManager to switch on the password grant type
			  .authenticationManager(authenticationManager)
			  //if you inject a UserDetailsService or if one is configured globally anyway (e.g. in a GlobalAuthenticationManagerConfigurer) 
			  //then a refresh token grant will contain a check on the user details, to ensure that the account is still active
			  .userDetailsService(userDetailsService)
			  .authorizationCodeServices(jdbcAuthCodeSvc)
			  .approvalStore(jdbcApplStore)
			  .tokenStore(redisTokenStore);
			// @formatter:on
		}
		
		@Override
		public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
			clients.jdbc(dataSource);
		}
		
	}
	
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("*"));
		configuration.setAllowedMethods(Arrays.asList("GET","POST"));
		configuration.setAllowedHeaders(Arrays.asList("content-type","authorization"));
		configuration.setAllowCredentials(true);
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		//source.registerCorsConfiguration("/login", configuration);
		source.registerCorsConfiguration("/oauth/token", configuration);
		return source;		
	}
	
}
