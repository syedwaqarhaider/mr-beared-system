package com.beared.shopservice.exception;


import com.beared.shopservice.response.ApiResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ServiceExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse<?>> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        String msg = ex.getRootCause() != null ? ex.getRootCause().getMessage() : ex.getMessage();

        if (msg.contains("cnic_format_check")) {
            return new ResponseEntity<>(
                    new ApiResponse<>(false, "Invalid CNIC format: must match #####-#######-#.", null),
                    HttpStatus.BAD_REQUEST
            );
        } else  if (msg.contains("unique_email")) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, "Email already exists.", null));

        } else if (msg.contains("unique_cnic")) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, "CNIC already exists.", null));
        } else if (msg.contains("unique_phone_number")) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, "Phone number already exists.", null));
        }
        else if(msg.contains("phone_number_format_check"))
        {
            return new ResponseEntity<>(
                    new ApiResponse<>(false, "Invalid Phone number", null),
                    HttpStatus.BAD_REQUEST
            );
        }
        return new ResponseEntity<>(
                new ApiResponse<>(false, "constraint violation.", null),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleValidation(MethodArgumentNotValidException ex) {
        String errorMsg = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
        return new ResponseEntity<>(
                new ApiResponse<>(false, errorMsg, null),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<?>> handleIllegalArgument(IllegalArgumentException ex) {
        return new ResponseEntity<>(
                new ApiResponse<>(false, ex.getMessage(), null),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleGenericException(Exception ex) {
        ex.printStackTrace();
        return new ResponseEntity<>(
                new ApiResponse<>(false, "Internal server error. Please contact support.", null),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<?>> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity.badRequest().body(new ApiResponse<>(false, ex.getMessage(), null));
    }
}
