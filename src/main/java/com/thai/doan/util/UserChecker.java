package com.thai.doan.util;

import com.thai.doan.dao.model.User;
import com.thai.doan.security.CustomUserDetails;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserChecker {
  public static boolean doesUserIsAdmin() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (
        authentication != null
            && !(authentication instanceof AnonymousAuthenticationToken)
            && authentication.isAuthenticated()) {
      CustomUserDetails userDetails =
          (CustomUserDetails) authentication.getPrincipal();
      return userDetails.getUser().getAdmin();
    }
    return false;
  }

  public static User getUserFromCtx() {
    CustomUserDetails userDetails;
    userDetails =
        (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return userDetails.getUser();
  }
}
