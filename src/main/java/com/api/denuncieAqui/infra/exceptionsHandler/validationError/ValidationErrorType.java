package com.api.denuncieAqui.infra.exceptionsHandler.validationError;

import com.api.denuncieAqui.infra.exceptionsHandler.ErrorTypes;

import java.time.Instant;
import java.util.Map;

public record ValidationErrorType(
        Instant timestamp,
        int httpStatus,
        ErrorTypes errorType,
        String message,
        Map<String, String> fieldErrors
) {
}
