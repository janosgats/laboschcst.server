package com.laboschqpa.server.enums.apierrordescriptor;

import com.laboschqpa.server.annotation.ApiErrorCategory;
import com.laboschqpa.server.api.errorhandling.ApiErrorDescriptor;

@ApiErrorCategory("auth")
public enum AuthApiError implements ApiErrorDescriptor {
    OAUTH2_AUTHORIZATION_REQUEST_FROM_ALREADY_LOGGED_IN_USER(1),
    AUTH_CANNOT_FIND_EXISTING_ACCOUNT_AND_NO_REGISTRATION_SESSION_DATA_IS_SET(2),
    AUTH_CORRUPTED_CONTEXT(3),
    AUTH_DEFECTIVE_AUTH_PROVIDER_RESPONSE(4),
    AUTH_EMAIL_GOT_FROM_OAUTH2_RESPONSE_BELONGS_TO_ANOTHER_ACCOUNT(5),
    AUTH_INVALID_LOGIN_METHOD(6),
    AUTH_REGISTRATION_REQUEST_REFERRED_BY_SESSION_DATA_IS_INVALID(7),
    AUTH_USER_ACCOUNT_IS_DISABLED(8),
    AUTH_GENERIC_FAILURE(9);

    private Integer apiErrorCode;

    AuthApiError(Integer apiErrorCode) {
        this.apiErrorCode = apiErrorCode;
    }

    @Override
    public Integer getApiErrorCode() {
        return apiErrorCode;
    }

    @Override
    public String getApiErrorName() {
        return toString();
    }
}