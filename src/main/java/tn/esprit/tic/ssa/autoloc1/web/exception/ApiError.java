package tn.esprit.tic.ssa.autoloc1.web.exception;

import java.util.List;

public record ApiError(
        String timestamp,
        int status,
        String error,
        String message,
        String path,
        List<FieldViolation> violations) {

    public record FieldViolation(String field, String message) {
    }
}