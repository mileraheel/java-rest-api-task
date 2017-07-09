package de.egym.recruiting.codingtask.rest;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import de.egym.recruiting.codingtask.dto.ErrorDto;

public class MalformedRestException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private Set<ErrorDto> errors;
	
	public MalformedRestException(Set<ErrorDto> errors) {
        super();
        this.setErrors(errors);
    }

    public MalformedRestException(String message, Set<ErrorDto> errors) {
        super(message);
        this.setErrors(errors);
    }
    
    public MalformedRestException(ErrorDto errorDTO) {
        super();
        this.setErrors(Collections.singleton(errorDTO));
    }

	public Set<ErrorDto> getErrors() {
		return errors;
	}

	public void setErrors(Set<ErrorDto> errors) {
		this.errors = errors;
	}

}
