package com.example.kotkotlin.domain

import java.math.BigDecimal
import java.math.RoundingMode

class ContaPoupanca(
    numero: String,
    titular: String,
) : ContaBase(numero, titular) {

    override fun tipo() = "POUPANCA"

    fun renderJuros(taxaMensal: BigDecimal) {
        if (taxaMensal <= BigDecimal.ZERO)
            throw com.example.kotkotlin.domain.exception.ValorInvalidoException(
                "Taxa de juros deve ser positiva. Recebida: $taxaMensal"
            )
        val juros = (saldo * taxaMensal).setScale(2, RoundingMode.HALF_UP)
        saldo += juros
        registrar("Rendimento de R$ $juros (taxa ${taxaMensal * BigDecimal("100")}%). Saldo: R$ $saldo")
    }
}