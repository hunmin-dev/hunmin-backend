package com.api.global.exceptions

import com.common.global.exceptions.base.CustomException
import com.common.global.exceptions.base.ExceptionResponse
import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
import jakarta.servlet.http.HttpServletRequest
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionAdvice {
    private val log = LoggerFactory.getLogger(this::class.java)

    @ExceptionHandler(Exception::class)
    fun handleUnexpectedException(
        request: HttpServletRequest,
        exception: Exception,
    ): ResponseEntity<ExceptionResponse> {
        log.error(
            "Unexpected error occurred. URI: {} {}, message: {}",
            request.method,
            request.requestURI,
            exception.message,
            exception,
        )

        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(
                ExceptionResponse(
                    name = "INTERNAL_SERVER_ERROR",
                    customCode = HttpStatus.INTERNAL_SERVER_ERROR,
                    message = exception.message ?: "An unexpected error occurred.",
                ),
            )
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationException(
        request: HttpServletRequest,
        exception: MethodArgumentNotValidException,
    ): ResponseEntity<ExceptionResponse> {
        log.warn(
            "Invalid request parameters. URI: {} {}",
            request.method,
            request.requestURI,
        )

        val errorMessage =
            exception.bindingResult.fieldErrors
                .joinToString("; ") { "${it.field}: ${it.defaultMessage ?: "Invalid value"}" }

        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(
                ExceptionResponse(
                    name = "VALIDATION_ERROR",
                    customCode = HttpStatus.BAD_REQUEST,
                    message = errorMessage.takeIf { it.isNotBlank() } ?: "Invalid request parameters",
                ),
            )
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleDeserializationException(exception: HttpMessageNotReadableException): ResponseEntity<ExceptionResponse> {
        val message =
            when (val cause = exception.cause) {
                is MissingKotlinParameterException -> "Field '${cause.parameter.name}' cannot be null."
                else -> "Failed to deserialize request body."
            }

        log.warn("Deserialization error: {}", message, exception)

        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(
                ExceptionResponse(
                    name = "DESERIALIZATION_ERROR",
                    customCode = HttpStatus.BAD_REQUEST,
                    message = message,
                ),
            )
    }

    @ExceptionHandler(CustomException::class)
    fun handleCustomException(
        request: HttpServletRequest,
        exception: CustomException,
    ): ResponseEntity<ExceptionResponse> {
        val type = exception.getExceptionType()

        log.warn(
            "Custom exception occurred. URI: {} {}, subject: {}, message: {}",
            request.method,
            request.requestURI,
            type.subject,
            type.message,
            exception,
        )

        return ResponseEntity
            .status(type.httpStatusCode)
            .body(
                ExceptionResponse(
                    name = type.subject,
                    customCode = type.httpStatusCode,
                    message = type.message,
                ),
            )
    }
}
