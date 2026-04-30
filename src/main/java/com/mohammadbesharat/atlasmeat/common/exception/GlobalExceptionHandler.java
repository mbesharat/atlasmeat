package com.mohammadbesharat.atlasmeat.common.exception;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.mohammadbesharat.atlasmeat.appointment.exceptions.AppointmentNotEligibleForHangingWeightException;
import com.mohammadbesharat.atlasmeat.appointment.exceptions.AppointmentNotFoundException;
import com.mohammadbesharat.atlasmeat.appointment.exceptions.InvalidAppointmentStatusTransitionException;
import com.mohammadbesharat.atlasmeat.checkout.exceptions.*;
import com.mohammadbesharat.atlasmeat.order.exceptions.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import jakarta.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;


@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    //404 error 

    @ExceptionHandler(CheckoutNotFoundException.class)
    public ResponseEntity<ApiError> handleCheckoutNotFound(
        CheckoutNotFoundException exception,
        HttpServletRequest request){
            return buildResponse(HttpStatus.NOT_FOUND, exception.getMessage(), request, null);
    }

    @ExceptionHandler(AppointmentNotFoundException.class)
    public ResponseEntity<ApiError> handleAppointmentNotFound(
        AppointmentNotFoundException exception,
        HttpServletRequest request){
            return buildResponse(HttpStatus.NOT_FOUND, exception.getMessage(), request, null);
    }


    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ApiError> handleOrderNotfound(
        OrderNotFoundException exception,
        HttpServletRequest request){
            return buildResponse(HttpStatus.NOT_FOUND, exception.getMessage(), request, null);
    }

    @ExceptionHandler(CutNotFoundException.class)
    public ResponseEntity<ApiError> handleCutNotFound(
        CutNotFoundException exception,
        HttpServletRequest request){
            return buildResponse(HttpStatus.NOT_FOUND, exception.getMessage(), request, null);
    }

    @ExceptionHandler(CutAnimalMismatchException.class)
    public ResponseEntity<ApiError> handleCutAnimalMismatch(
        CutAnimalMismatchException exception,
        HttpServletRequest request){
            return buildResponse(HttpStatus.CONFLICT, exception.getMessage(), request, null);
    }

    @ExceptionHandler(InvalidDateRangeException.class)
    public ResponseEntity<ApiError> handleInvalidDateRange(
        InvalidDateRangeException exception,
        HttpServletRequest request){
            return buildResponse(HttpStatus.BAD_REQUEST, exception.getMessage(), request, null);
    }

    @ExceptionHandler(InvalidCheckoutStatusTransitionException.class)
    public ResponseEntity<ApiError> handleInvalidCheckoutStatusTransition(
        InvalidCheckoutStatusTransitionException exception,
        HttpServletRequest request){
            return buildResponse(HttpStatus.CONFLICT, exception.getMessage(), request, null);
    }

    @ExceptionHandler(InvalidAppointmentStatusTransitionException.class)
    public ResponseEntity<ApiError> handleInvalidAppointmentStatusTransition(
        InvalidAppointmentStatusTransitionException exception,
        HttpServletRequest request){
            return buildResponse(HttpStatus.CONFLICT, exception.getMessage(), request, null);
    }

    @ExceptionHandler(AppointmentNotEligibleForHangingWeightException.class)
    public ResponseEntity<ApiError> handleAppointmentNotEligibleForHangingWeight(
            AppointmentNotEligibleForHangingWeightException exception,
            HttpServletRequest request){
        return buildResponse(HttpStatus.CONFLICT, exception.getMessage(), request, null);
    }

    @ExceptionHandler(CheckoutLockedException.class)
    public ResponseEntity<ApiError> handleCheckoutLockedException(
        CheckoutLockedException exception,
        HttpServletRequest request){
            return buildResponse(HttpStatus.CONFLICT, exception.getMessage(), request, null);
    }

    @ExceptionHandler(OrderNotInCheckoutException.class)
    public ResponseEntity<ApiError> handleOrderNotInCheckout(
        OrderNotInCheckoutException exception,
        HttpServletRequest request){
            return buildResponse(HttpStatus.NOT_FOUND, exception.getMessage(), request, null);
    }

    @ExceptionHandler(CutNotInOrderException.class)
    public ResponseEntity<ApiError> handleCutNotInOrder(
        CutNotInOrderException exception,
        HttpServletRequest request){
            return buildResponse(HttpStatus.NOT_FOUND, exception.getMessage(), request, null);
    }

    @ExceptionHandler(OrderItemNotFoundException.class)
    public ResponseEntity<ApiError> handleOrderItemNotFound(
        OrderItemNotFoundException exception,
        HttpServletRequest request){
            return buildResponse(HttpStatus.NOT_FOUND, exception.getMessage(), request, null);
    }

    @ExceptionHandler(InvalidPatchRequestException.class)
    public ResponseEntity<ApiError> handleInvalidPatchRequest(
        InvalidPatchRequestException exception,
        HttpServletRequest request){
            return buildResponse(HttpStatus.BAD_REQUEST, exception.getMessage(), request, null);
    }

    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(
        MethodArgumentNotValidException exception,
        HttpServletRequest request)
        {
            List<ValidationError> errors = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(this::toValidationError)
                .toList();
            return buildResponse(HttpStatus.BAD_REQUEST, "Validation failed", request, errors);
    }

    //400 error
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> handleUnreadableJson(
        HttpMessageNotReadableException exception,
        HttpServletRequest request
    ){
        if(exception.getCause() instanceof InvalidFormatException ife){
            if(ife.getTargetType().isEnum()) {
                return buildResponse(HttpStatus.BAD_REQUEST, "Invalid enum type. Valid values include: " +
                        Arrays.toString(ife.getTargetType().getEnumConstants()), request, null);
            }
            else if(ife.getTargetType() == LocalDate.class) {
                return buildResponse(HttpStatus.BAD_REQUEST, "Invalid date. Must in the format yyyy-MM-dd", request, null);
            }
        }

        return buildResponse(HttpStatus.BAD_REQUEST, "Invalid JSON value", request, null);

    }
    
    //500 error
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleUnexpected(
        Exception exception,
        HttpServletRequest request
        ){
            log.error("Unexpected error", exception);
            return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected server error", request, null);
    }


    private ValidationError toValidationError(FieldError fe){
        String message = fe.getDefaultMessage() != null ? fe.getDefaultMessage() : "Invalid value";
        return new ValidationError(fe.getField(), message);
    }

    private ResponseEntity<ApiError> buildResponse(
        HttpStatus status,
        String message,
        HttpServletRequest request,
        List<ValidationError> validationErrors
    ){
        List<ValidationError> safeErrors = 
            (validationErrors == null) ? List.of() : validationErrors;

        ApiError body = new ApiError(
            Instant.now(),
            status.value(),
            status.getReasonPhrase(),
            message,
            request.getRequestURI(),
            safeErrors
        );
        
        return ResponseEntity.status(status).body(body);
    }


}
