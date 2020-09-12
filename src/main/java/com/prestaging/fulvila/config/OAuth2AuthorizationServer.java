package com.prestaging.fulvila.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;


@Configuration
@EnableAuthorizationServer
public class OAuth2AuthorizationServer extends AuthorizationServerConfigurerAdapter 
{	
	@Autowired
	private TokenStore tokenStore;
	
	private AuthenticationManager authenticationManagerBean;
		
  	@Autowired
  	public void setAuthenticationManagerBean(AuthenticationManager authenticationManagerBean) {
  		this.authenticationManagerBean = authenticationManagerBean;
  	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security
			.tokenKeyAccess("permitAll()")
			.checkTokenAccess("isAuthenticated()")
			.allowFormAuthenticationForClients();
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients
			.inMemory()
			.withClient("clientapp")
	        .authorizedGrantTypes ("password", "authorization_code", "refresh_token", "implicit")
	        .authorities ("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT", "USER")
			.scopes ("read", "write")
	        .autoApprove (true)
	        .secret (passwordEncoder().encode("123456"))
			.accessTokenValiditySeconds(5000)
			.refreshTokenValiditySeconds(50000);
	}
  
	@Override
	public void configure (AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
      endpoints
              .authenticationManager (authenticationManagerBean)        
              .tokenStore (tokenStore);
	}
	
    @Bean
    public TokenStore tokenStore () {
        return new InMemoryTokenStore ();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder () {
        return new BCryptPasswordEncoder ();
    }

	@Bean
	public FilterRegistrationBean corsFilter_() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.addAllowedOrigin("*");
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");
		source.registerCorsConfiguration("/**", config);
		FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
		bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
		return bean;
	}
}
