package com.laboschqpa.server.enums.apierrordescriptor;

import com.laboschqpa.server.annotation.ApiErrorCategory;
import com.laboschqpa.server.api.errorhandling.ApiErrorDescriptor;

@ApiErrorCategory("invalidAttachment")
public enum InvalidAttachmentApiError implements ApiErrorDescriptor {
    SOME_FILES_ARE_NOT_AVAILABLE(1),
    SOME_FILES_ARE_NOT_OWNED_BY_YOU(2);

    private Integer apiErrorCode;

    InvalidAttachmentApiError(Integer apiErrorCode) {
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
