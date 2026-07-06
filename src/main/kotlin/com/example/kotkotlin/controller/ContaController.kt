package com.example.kotkotlin.controller

import com.example.kotkotlin.controller.dto.*
import com.example.kotkotlin.service.ContaService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/contas")
class ContaController(private val service: ContaService) {

    @PostMapping("/corrente")
    fun abrirContaCorrente(@Valid @RequestBody request: AbrirContaCorrenteRequest): ResponseEntity<ContaResponse> {
        val conta = service.abrirContaCorrente(request.titular, request.limite)
        return ResponseEntity.status(HttpStatus.CREATED).body(ContaResponse.de(conta))
    }

    @PostMapping("/poupanca")
    fun abrirContaPoupanca(@Valid @RequestBody request: AbrirContaPoupancaRequest): ResponseEntity<ContaResponse> {
        val conta = service.abrirContaPoupanca(request.titular)
        return ResponseEntity.status(HttpStatus.CREATED).body(ContaResponse.de(conta))
    }

    @GetMapping("/{numero}")
    fun buscarConta(@PathVariable numero: String): ResponseEntity<ContaResponse> {
        val conta = service.buscarConta(numero)
        return ResponseEntity.ok(ContaResponse.de(conta))
    }

    @GetMapping
    fun listarContas(): ResponseEntity<List<ContaResponse>> {
        val contas = service.listarContas().map { ContaResponse.de(it) }
        return ResponseEntity.ok(contas)
    }

    @PostMapping("/{numero}/deposito")
    fun depositar(@PathVariable numero: String, @Valid @RequestBody request: ValorRequest): ResponseEntity<ContaResponse> {
        service.depositar(numero, request.valor)
        val conta = service.buscarConta(numero)
        return ResponseEntity.ok(ContaResponse.de(conta))
    }

    @PostMapping("/{numero}/saque")
    fun sacar(@PathVariable numero: String, @Valid @RequestBody request: ValorRequest): ResponseEntity<ContaResponse> {
        service.sacar(numero, request.valor)
        val conta = service.buscarConta(numero)
        return ResponseEntity.ok(ContaResponse.de(conta))
    }

    @PostMapping("/{numero}/transferencia")
    fun transferir(@PathVariable numero: String, @Valid @RequestBody request: TransferenciaRequest): ResponseEntity<ContaResponse> {
        service.transferir(numero, request.numeroDestino, request.valor)
        val conta = service.buscarConta(numero)
        return ResponseEntity.ok(ContaResponse.de(conta))
    }
}