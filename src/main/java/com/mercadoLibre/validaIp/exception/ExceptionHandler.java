package com.mercadoLibre.validaIp.exception;

import java.util.Objects;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.util.WebUtils;

import com.mercadoLibre.validaIp.dto.Envelope;
import com.mercadoLibre.validaIp.dto.RestError;
import com.mercadoLibre.validaIp.exception.message.UnexpectedException;

/**
 * If an error occurred here we handled it
 * 
 * @author fabian.fonseca
 *
 */
@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
			request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
		}
		return new ResponseEntity<>(getErrorResponse(ex, status), headers, status);
	}

	/**
	 * @param ex
	 *            Exception who was thrown
	 * @param request
	 *            information
	 * @return
	 */
	@org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleRestException(Exception ex, WebRequest request) {
		return new ResponseEntity<>(getErrorResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR),
				new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * Builds and standard response for any exception
	 * 
	 * @param ex
	 *            Exception who was thrown
	 * @param status
	 *            {@link org.springframework.http.HttpStatus} of the service
	 * @return {@link com.mercadoLibre.validaIp.dto.Envelope.Envelope} with all the
	 *         error detail no matter which type exception was thrown
	 */
	private Envelope getErrorResponse(Exception ex, HttpStatus status) {
		logger.error("ExceptionHandler", ex);
		Envelope errorResponse = new Envelope(HttpStatus.INTERNAL_SERVER_ERROR);
		RestError restError = new RestError();
		if (ex instanceof RestException) {
			RestException restException = (RestException) ex;
			restError.setCode(restException.getCode());
			restError.setMessage(restException.getMessage());
		} else {
			restError.setCode(UnexpectedException.UNEXPECTED_EXCEPTION.getCode());
			restError.setMessage(UnexpectedException.UNEXPECTED_EXCEPTION.getMessage());
		}
		Throwable throwable = ExceptionUtils.getRootCause(ex);
		restError.setException(Objects.isNull(throwable.getCause()) ? throwable.toString()
				: throwable.getCause().toString());
		restError.setTrace(ExceptionUtils.getStackTrace(throwable));
		errorResponse.setError(restError);
		return errorResponse;
	}
}
