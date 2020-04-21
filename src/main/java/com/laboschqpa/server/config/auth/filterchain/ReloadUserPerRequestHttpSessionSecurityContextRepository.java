package com.laboschqpa.server.config.auth.filterchain;

import com.laboschqpa.server.config.auth.user.CustomOauth2User;
import com.laboschqpa.server.entity.account.UserAcc;
import com.laboschqpa.server.repo.UserAccRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

import javax.servlet.http.HttpSession;
import java.util.Optional;

public class ReloadUserPerRequestHttpSessionSecurityContextRepository extends HttpSessionSecurityContextRepository {
    private static final Logger loggerOfChildClass = LoggerFactory.getLogger(ReloadUserPerRequestHttpSessionSecurityContextRepository.class);

    private UserAccRepository userAccRepository;

    public ReloadUserPerRequestHttpSessionSecurityContextRepository(UserAccRepository userAccRepository) {
        this.userAccRepository = userAccRepository;
    }

    @Override
    public SecurityContext loadContext(HttpRequestResponseHolder requestResponseHolder) {
        loggerOfChildClass.trace("Entering custom SecurityContext loader.");
        SecurityContext loadedSecurityContext = super.loadContext(requestResponseHolder);

        if (loadedSecurityContext.getAuthentication() == null || loadedSecurityContext.getAuthentication().getPrincipal() == null) {
            loggerOfChildClass.debug("No user in session authentication. Returning 'loadedSecurityContext'.");
            return loadedSecurityContext;//No user in session auth so we cannot load it from DB.
        }

        Authentication originalAuthentication = loadedSecurityContext.getAuthentication();

        Object originalPrincipal = originalAuthentication.getPrincipal();

        boolean shouldBeUnauthenticated = true;

        if (originalPrincipal instanceof CustomOauth2User) {
            CustomOauth2User originalCustomOauth2User = (CustomOauth2User) originalPrincipal;
            Long userIdFromSession = originalCustomOauth2User.getUserId();

            if (userIdFromSession != null) {
                loggerOfChildClass.debug("Loading user from DB with ID: " + userIdFromSession);
                Optional<UserAcc> userFromDBOptional = userAccRepository.findById(userIdFromSession);

                if (userFromDBOptional.isPresent()) {
                    UserAcc userAccFromDB = userFromDBOptional.get();

                    if (userAccFromDB.getEnabled()) {
                        originalCustomOauth2User.setUserAccEntity(userAccFromDB);

                        Authentication newAuthentication;
                        if (originalAuthentication instanceof UsernamePasswordAuthenticationToken) {
                            newAuthentication = new UsernamePasswordAuthenticationToken(originalCustomOauth2User, originalAuthentication.getCredentials(), originalCustomOauth2User.getAuthorities());
                        } else if (originalAuthentication instanceof OAuth2AuthenticationToken) {
                            newAuthentication = new OAuth2AuthenticationToken(originalCustomOauth2User, originalCustomOauth2User.getAuthorities(), ((OAuth2AuthenticationToken) originalAuthentication).getAuthorizedClientRegistrationId());
                        } else {
                            throw new RuntimeException("Unexpected authentication type of originalAuthentication: " + originalAuthentication.getClass().getName());
                        }

                        loadedSecurityContext.setAuthentication(newAuthentication);
                        shouldBeUnauthenticated = false;
                    }
                }
            }
        } else {
            throw new RuntimeException("Authentication Principal is NOT instance of 'CustomOauth2User'!");
//            shouldBeUnauthenticated = false;//User isn't logged in
        }

        if (shouldBeUnauthenticated) {
            loggerOfChildClass.info("UnAuthenticating user.");
            loadedSecurityContext.setAuthentication(null);

            HttpSession session = requestResponseHolder.getRequest().getSession();
            if (session != null)
                session.invalidate();
        }

        loggerOfChildClass.trace("End of custom SecurityContext loader.");
        return loadedSecurityContext;
    }
}
