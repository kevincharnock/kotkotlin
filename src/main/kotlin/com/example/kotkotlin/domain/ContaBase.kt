package com.example.kotkotlin.domain


import com.example.kotkotlin.domain.exception.SaldoInsuficienteException
import com.example.kotkotlin.domain.exception.ValorInvalidoException
import java.math.BigDecimal

abstract class ContaBase(
    override val numero: String,
    override val titular: String,
) : Conta {

    override var saldo: BigDecimal = BigDecimal.ZERO
        protected set

    private val historico: MutableList<String> = mutableListOf()

    override fun depositar(valor: BigDecimal) {
        validarValorPositivo(valor, "Depósito")
        saldo += valor
        registrar("Depósito de R$ $valor. Saldo atual: R$ $saldo")
    }

    override fun sacar(valor: BigDecimal) {
        validarValorPositivo(valor, "Saque")
        if (valor > saldo) throw SaldoInsuficienteException(numero, valor, saldo)
        saldo -= valor
        registrar("Saque de R$ $valor. Saldo atual: R$ $saldo")
    }

    override fun extrato(): List<String> = historico.toList()

    protected fun registrar(evento: String) {
        historico.add(evento)
    }

    protected fun validarValorPositivo(valor: BigDecimal, operacao: String) {
        if (valor <= BigDecimal.ZERO)
            throw ValorInvalidoException("$operacao deve ser maior que zero. Recebido: $valor")
    }

    override fun toString(): String =
        "Conta[${tipo()}] numero=$numero, titular=$titular, saldo=R$ $saldo"
}