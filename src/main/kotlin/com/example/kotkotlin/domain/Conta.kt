package com.example.kotkotlin.domain

import java.math.BigDecimal

interface Conta {
    val numero: String
    val titular: String
    val saldo: BigDecimal

    fun tipo(): String

    fun depositar(valor: BigDecimal)

    fun sacar(valor: BigDecimal)

    fun extrato(): List<String>

}