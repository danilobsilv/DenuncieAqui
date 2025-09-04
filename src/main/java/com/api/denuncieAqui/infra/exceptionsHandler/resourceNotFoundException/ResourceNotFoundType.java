package com.api.denuncieAqui.infra.exceptionsHandler.resourceNotFoundException;

import com.api.denuncieAqui.infra.exceptionsHandler.ErrorTypes;

import java.time.Instant;

public record ResourceNotFoundType(
        Instant timestamp,
        int HttpStatus,
        ErrorTypes errorType,
        String message
) {
}
