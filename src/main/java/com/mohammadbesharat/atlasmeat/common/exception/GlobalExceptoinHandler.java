package com.mohammadbesharat.atlasmeat.common.exception;
import com.mohammadbesharat.atlasmeat.checkout.exceptions.CheckoutLockedException;
import com.mohammadbesharat.atlasmeat.checkout.exceptions.CheckoutNotFound;
import com.mohammadbesharat.atlasmeat.checkout.exceptions.CutAnimalMismatch;
import com.mohammadbesharat.atlasmeat.checkout.exceptions.CutNotFound;
import com.mohammadbesharat.atlasmeat.checkout.exceptions.CutNotInOrder;
import com.mohammadbesharat.atlasmeat.checkout.exceptions.InvalidDateRange;
import com.mohammadbesharat.atlasmeat.checkout.exceptions.InvalidPatchRequest;
import com.mohammadbesharat.atlasmeat.checkout.exceptions.InvalidStatusTransition;
import com.mohammadbesharat.atlasmeat.checkout.exceptions.OrderItemNotFound;
import com.mohammadbesharat.atlasmeat.checkout.exceptions.OrderNotInCheckout;
import com.mohammadbesharat.atlasmeat.order.exceptions.OrderNotFoundException;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import jakarta.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.List;


@RestControllerAdvice
public class GlobalExceptoinHandler {



    //404 error 

    @ExceptionHandler(CheckoutNotFound.class)
    public ResponseEntity<ApiError> handleCheckoutNotFound(
        CheckoutNotFound exception,
        HttpServletRequest request){
            return buildResponse(HttpStatus.NOT_FOUND, exception.getMessage(), request, null);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ApiError> handleOrderNotfonud(
        OrderNotFoundException exception,
        HttpServletRequest request){
            return buildResponse(HttpStatus.NOT_FOUND, exception.getMessage(), request, null);
    }

    @ExceptionHandler(CutNotFound.class)
    public ResponseEntity<ApiError> handleCutNotFound(
        CutNotFound exception,
        HttpServletRequest request){
            return buildResponse(HttpStatus.NOT_FOUND, exception.getMessage(), request, null);
    }

     @ExceptionHandler(CutAnimalMismatch.class)
    public ResponseEntity<ApiError> handleCutAnimalMismatch(
        CutAnimalMismatch exception,
        HttpServletRequest request){
            return buildResponse(HttpStatus.CONFLICT, exception.getMessage(), request, null);
    }

    @ExceptionHandler(InvalidDateRange.class)
    public ResponseEntity<ApiError> handleInvalidDateRange(
        InvalidDateRange exception,
        HttpServletRequest request){
            return buildResponse(HttpStatus.BAD_REQUEST, exception.getMessage(), request, null);
    }

    @ExceptionHandler(InvalidStatusTransition.class)
    public ResponseEntity<ApiError> handleInvalidStatusTransition(
        InvalidStatusTransition exception,
        HttpServletRequest request){
            return buildResponse(HttpStatus.BAD_REQUEST, exception.getMessage(), request, null);
    }

    @ExceptionHandler(CheckoutLockedException.class)
    public ResponseEntity<ApiError> handleCheckoutLockedException(
        CheckoutLockedException exception,
        HttpServletRequest request){
            return buildResponse(HttpStatus.CONFLICT, exception.getMessage(), request, null);
    }

    @ExceptionHandler(OrderNotInCheckout.class)
    public ResponseEntity<ApiError> handleOrderNotInCheckout(
        OrderNotInCheckout exception,
        HttpServletRequest request){
            System.out.println("HANDLER HIT: " + exception.getClass().getName());
            return buildResponse(HttpStatus.NOT_FOUND, exception.getMessage(), request, null);
    }

    @ExceptionHandler(CutNotInOrder.class)
    public ResponseEntity<ApiError> handleCutNotInOrder(
        CutNotInOrder exception,
        HttpServletRequest request){
            return buildResponse(HttpStatus.NOT_FOUND, exception.getMessage(), request, null);
    }

    @ExceptionHandler(OrderItemNotFound.class)
    public ResponseEntity<ApiError> handleCutNotInOrder(
        OrderItemNotFound exception,
        HttpServletRequest request){
            return buildResponse(HttpStatus.NOT_FOUND, exception.getMessage(), request, null);
    }

    @ExceptionHandler(InvalidPatchRequest.class)
    public ResponseEntity<ApiError> handleInvalidPatchRequest(
        InvalidPatchRequest exception,
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
        return buildResponse(HttpStatus.BAD_REQUEST, "Malformed JSON request", request, null);
    }
    
    //500 error
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleUnexpected(
        Exception exception,
        HttpServletRequest request
    ){
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
        ApiError body = new ApiError(
            Instant.now(),
            status.value(),
            status.getReasonPhrase(),
            message,
            request.getRequestURI(),
            validationErrors
        );
        
        return ResponseEntity.status(status).body(body);
    }


}
