package com.perrygarg.khanapeena.common.validation;

/**
 * Created by perry.gar  on 07/02/17.
 */

public interface ValidationConstants {

    public int VALIDATION_FIELD_EMPTY = 1;
    public int VALIDATION_FIELD_INVALID = 2;

    /*** Regex ***/
    String REGEX_EMAIL_VALID = "^[A-Z0-9a-z\\._%+-]+@([A-Za-z0-9-]+\\.)+[A-Za-z]{2,4}$";

}
