package de.egym.recruiting.codingtask.dto;

/**
 * This Data Transfer Object (DTO) is used to pass error codes and error messages via the REST interface from the server to the client.
 * This DTO is sent to the client using a JSON format.
 *
 * @see de.egym.recruiting.codingtask.rest.RestExceptionMapper
 */
public class ErrorDto {

	/**
	 * The error status code.
	 */
	private int errorCode;

	/**
	 * (Optional) Error text value.
	 */
	private String errorValue;
	
	/**
	 * This is used to store field name;
	 */
	private String field;

	/**
	 * Used for serialization only.
	 */
	ErrorDto() {
		super();
	}
	
	public ErrorDto(int errorCode, String errorValue) {
		this.errorCode = errorCode;
		this.errorValue = errorValue;
	}

	public ErrorDto(int errorCode, String errorValue, String field) {
		this.errorCode = errorCode;
		this.errorValue = errorValue;
		this.field = field;
	}

	public int getErrorCode() {
		return errorCode;
	}

	void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorValue() {
		return errorValue;
	}

	void setErrorValue(String errorValue) {
		this.errorValue = errorValue;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}
}
