package com.luist.todo.ui;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

/**
 * SecurityUtils takes care of all such static operations that have to do with
 * security and querying rights from different beans of the UI.
 *
 */
public class SecurityUtils {

	private SecurityUtils() {
		// Util methods only
	}

	/**
	 * Gets the user name of the currently signed in user.
	 *
	 * @return the user name of the current user or <code>null</code> if the
	 *         user has not signed in
	 */
	public static String getUsername() {
		SecurityContext context = SecurityContextHolder.getContext();
		if (context == null) {
			return null;
		}
		Authentication authentication = context.getAuthentication();
		if (authentication == null) {
			return null;
		}
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		return userDetails.getUsername();
	}

	/**
	 * Check if currently signed-in user is in the role with the given role
	 * name.
	 *
	 * @param role
	 *            the role to check for
	 * @return <code>true</code> if user is in the role, <code>false</code>
	 *         otherwise
	 */
	public static boolean isCurrentUserInRole(String role) {
		return getUserRoles()
				.stream()
				.anyMatch(roleName -> roleName.equals(requireNonNull(role)));
	}

	/**
	 * Gets the roles the currently signed-in user belongs to.
	 *
	 * @return a set of all roles the currently signed-in user belongs to.
	 */
	public static Set<String> getUserRoles() {
		SecurityContext context = SecurityContextHolder.getContext();
		return context.getAuthentication()
				.getAuthorities()
				.stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.toSet());
	}

	/**
	 * Gets the user object for the current user.
	 *
	 * @return the user object
	 */
	public static UserDetails getCurrentUser(UserDetailsService userService) {
		return userService.loadUserByUsername(SecurityUtils.getUsername());
	}
}
