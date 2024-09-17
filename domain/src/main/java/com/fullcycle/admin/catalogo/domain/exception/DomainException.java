package com.fullcycle.admin.catalogo.domain.exception;

import java.util.List;
import com.fullcycle.admin.catalogo.domain.validation.Error;

public class DomainException extends NoStacktraceException {

    private final List<Error> errors;

    private DomainException(final String aMessage, final List<Error> anErrors) {
        super(aMessage);;
        this.errors = anErrors;
    }

    public static DomainException with(final Error anError) {
        return DomainException.with(List.of(anError));
    }

    public static DomainException with(final List<Error> errors) {
        return new DomainException("", errors);
    }

    public List<Error> getErrors() {
        return errors;
    }
}
