package com.perrygarg.khanapeena.common.validation;

/**
 * Created by perry.garg on 07/02/17.
 */

public class ValidationUtil {

    public static boolean isValidEmail(String email)
    {
        return email.matches(ValidationConstants.REGEX_EMAIL_VALID);
    }

}
