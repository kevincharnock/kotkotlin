package com.example.kotkotlin.controller.GlobalExceptionHandler

import com.example.kotkotlin.controller.dto.ErroResponse
import com.example.kotkotlin.domain.exception.ContaNaoEncontradaException
import com.example.kotkotlin.domain.exception.SaldoInsuficienteException
import com.example.kotkotlin.domain.exception.ValorInvalidoException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    private val logger = LoggerFactory.getLogger(javaClass)

    @ExceptionHandler(ContaNaoEncontradaException::class)
    fun contaNaoEncontrada(ex: ContaNaoEncontradaException): ResponseEntity<ErroResponse> {
        logger.warn("Conta não encontrada: {}", ex.message)
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErroResponse(ex.message ?: "Conta não encontrada"))
    }

    @ExceptionHandler(SaldoInsuficienteException::class)
    fun saldoInsuficiente(ex: SaldoInsuficienteException): ResponseEntity<ErroResponse> {
        logger.warn("Saldo insuficiente: {}", ex.message)
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ErroResponse(ex.message ?: "Saldo insuficiente"))
    }

    @ExceptionHandler(ValorInvalidoException::class)
    fun valorInvalido(ex: ValorInvalidoException): ResponseEntity<ErroResponse> {
        logger.warn("Valor inválido: {}", ex.message)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErroResponse(ex.message ?: "Valor inválido"))
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun argumentoInvalido(ex: IllegalArgumentException): ResponseEntity<ErroResponse> {
        logger.warn("Argumento inválido: {}", ex.message)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErroResponse(ex.message ?: "Requisição inválida"))
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun validacaoFalhou(ex: MethodArgumentNotValidException): ResponseEntity<ErroResponse> {
        val mensagem = ex.bindingResult.fieldErrors
            .joinToString(separator = "; ") { "${it.field}: ${it.defaultMessage}" }
        logger.warn("Validação falhou: {}", mensagem)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErroResponse(mensagem))
    }

    @ExceptionHandler(Exception::class)
    fun erroInesperado(ex: Exception): ResponseEntity<ErroResponse> {
        logger.error("Erro inesperado", ex)
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErroResponse("Erro interno no servidor"))
    }
}