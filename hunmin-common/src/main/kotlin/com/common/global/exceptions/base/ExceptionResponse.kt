package com.common.global.exceptions.base

data class ExceptionResponse(
    val name: String,
    val errorCode: String,
    val message: String,
)
