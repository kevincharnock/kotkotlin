package com.example.kotkotlin.domain

import com.example.kotkotlin.domain.exception.SaldoInsuficienteException
import java.math.BigDecimal

class ContaCorrente(
    numero: String,
    titular: String,
    val limite: BigDecimal = BigDecimal("500.00"),
) : ContaBase(numero, titular) {

    override fun sacar(valor: BigDecimal) {
        validarValorPositivo(valor, "Saque")
        val saldoDisponivel = saldo + limite
        if (valor > saldoDisponivel)
            throw SaldoInsuficienteException(numero, valor, saldoDisponivel)
        saldo -= valor
        registrar("Saque de R$ $valor com limite. Saldo atual: R$ $saldo")
    }

    override fun tipo() = "CORRENTE"
}