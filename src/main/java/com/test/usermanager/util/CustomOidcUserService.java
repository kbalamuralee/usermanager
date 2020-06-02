package com.test.usermanager.util;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.test.usermanager.model.UserDetails;
import com.test.usermanager.repository.UserDetailsRepository;

/* 
 * Overrides the default user service and stores user data in table
 * if unregistered.
 */
@Service
public class CustomOidcUserService extends DefaultOAuth2UserService {

	@Autowired
	private UserDetailsRepository repo;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
		OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);
		try {
			return processOAuth2User(oAuth2UserRequest, oAuth2User);
		} catch (AuthenticationException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
		}
	}

	private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
		String name = "";
		String link = "";
		if ("github".equals(oAuth2UserRequest.getClientRegistration().getRegistrationId())) {
			name = oAuth2User.getAttribute("login");
			link = oAuth2User.getAttribute("url");
		}
		Optional<UserDetails> optUser = repo.findByName(name);
		if (!optUser.isPresent()) {
			UserDetails user = new UserDetails();
			user.setName(name);
			user.setMeta(link);
			repo.save(user);
			return oAuth2User;
		}
		throw new RuntimeException("User already registered. Please login.");
	}

}
