package com.example.kotkotlin.domain.exception


import java.math.BigDecimal

class SaldoInsuficienteException(
    numero: String,
    valorSolicitado: BigDecimal,
    saldoDisponivel: BigDecimal,
) : RuntimeException(
    "Conta $numero: saldo insuficiente. " +
            "Solicitado: R$ $valorSolicitado, disponível: R$ $saldoDisponivel"
)