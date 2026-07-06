package com.example.kotkotlin.controller.dto

import com.example.kotkotlin.domain.Conta
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Positive
import java.math.BigDecimal

data class AbrirContaCorrenteRequest(
    @field:NotBlank(message = "Titular é obrigatório")
    val titular: String,

    @field:Positive(message = "Limite deve ser positivo")
    val limite: BigDecimal = BigDecimal("500.00"),
)

data class AbrirContaPoupancaRequest(
    @field:NotBlank(message = "Titular é obrigatório")
    val titular: String,
)

data class ValorRequest(
    @field:Positive(message = "Valor deve ser positivo")
    val valor: BigDecimal,
)

data class TransferenciaRequest(
    @field:NotBlank(message = "Conta destino é obrigatória")
    val numeroDestino: String,

    @field:Positive(message = "Valor deve ser positivo")
    val valor: BigDecimal,
)

data class ContaResponse(
    val numero: String,
    val titular: String,
    val saldo: BigDecimal,
    val tipo: String,
) {
    companion object {
        fun de(conta: Conta): ContaResponse =
            ContaResponse(
                numero = conta.numero,
                titular = conta.titular,
                saldo = conta.saldo,
                tipo = conta.tipo(),
            )
    }
}

data class ErroResponse(
    val erro: String,
)