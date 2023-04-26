package com.example.webservice_for_online_testing.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Override class for configuring behaviour after successful user authentication
 * @author Kondrashov Dmitry
 * @version 1.0
 */
public class MySimpleUrlAuthenticationSuccessHandler
        implements AuthenticationSuccessHandler {

    /** Named logger for this class */
    protected Log logger = LogFactory.getLog(this.getClass());

    /** Field for new redirect strategy */
    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    /**
     * Overrided method called after successful authentication.
     * @see MySimpleUrlAuthenticationSuccessHandler#handle(HttpServletRequest, HttpServletResponse, Authentication)
     * @see MySimpleUrlAuthenticationSuccessHandler#clearAuthenticationAttributes(HttpServletRequest)
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response, Authentication authentication) throws IOException
    {
        handle(request, response, authentication);
        clearAuthenticationAttributes(request);
    }

    /**
     * Method performs redirect for one of two main pages based user`s role
     * @param request request that gets after successful authentication
     * @param response response that would be sent after successful authentication
     * @param authentication the token for an authentication request
     * @throws IOException general class of exceptions produced by failed or interrupted I/O operations
     */
    protected void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException {

        String targetUrl = determineTargetUrl(authentication);

        if (response.isCommitted()) {
            logger.debug(
                    "Response has already been committed. Unable to redirect to "
                            + targetUrl);
            return;
        }

        redirectStrategy.sendRedirect(request, response, targetUrl);
    }

    /**
     * Method contains roles and pages to which redirect user based on his role.
     * If the role is unknown {@link IllegalStateException} may occur.
     * @param authentication name of role of the user that logged in webservice
     * @return name of the html page to redirect
     * See src/main/resources/templates/index_teacher.html in templates.
     * See src/main/resources/templates/index_student.html in templates.
     */
    protected String determineTargetUrl(final Authentication authentication) {

        Map<String, String> roleTargetUrlMap = new HashMap<>();
        roleTargetUrlMap.put("ROLE_STUDENT", "/index_student");
        roleTargetUrlMap.put("ROLE_TEACHER", "/index_teacher");

        final Collection<? extends GrantedAuthority> authorities;
        authorities = authentication.getAuthorities();
        for (final GrantedAuthority grantedAuthority : authorities) {
            String authorityName = grantedAuthority.getAuthority();
            if(roleTargetUrlMap.containsKey(authorityName)) {
                return roleTargetUrlMap.get(authorityName);
            }
        }

        throw new IllegalStateException();
    }

    /**
     * Method will return the mapped URL for the first role the user has
     * @param request http request from which method will collect user`s role
     */
    protected void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return;
        }
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }
}