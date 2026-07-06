package com.example.kotkotlin.service

import com.example.kotkotlin.domain.Conta
import com.example.kotkotlin.domain.ContaCorrente
import com.example.kotkotlin.domain.ContaPoupanca
import com.example.kotkotlin.domain.exception.ContaNaoEncontradaException
import com.example.kotkotlin.repository.ContaRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.util.*

@Service
class ContaService(private val repository: ContaRepository) {

    private val logger = LoggerFactory.getLogger(javaClass)

    fun abrirContaCorrente(titular: String, limite: BigDecimal = BigDecimal("500.00")): Conta {
        logger.info("Abrindo conta corrente para titular={}", titular)

        val numero = gerarNumero()
        val conta = ContaCorrente(numero, titular, limite)

        repository.salvar(conta)
        logger.info("Conta corrente aberta: numero={}, titular={}", numero, titular)

        return conta
    }

    fun abrirContaPoupanca(titular: String): Conta {
        logger.info("Abrindo conta poupança para titular={}", titular)

        val numero = gerarNumero()
        val conta = ContaPoupanca(numero, titular)

        repository.salvar(conta)
        logger.info("Conta poupança aberta: numero={}, titular={}", numero, titular)

        return conta
    }

    fun buscarConta(numero: String): Conta {
        logger.debug("Buscando conta numero={}", numero)

        val conta = repository.buscarPorNumero(numero)
            ?: throw ContaNaoEncontradaException(numero)

        logger.debug("Conta encontrada: {}", conta)
        return conta
    }

    fun depositar(numero: String, valor: BigDecimal) {
        logger.info("Depósito solicitado: numero={}, valor={}", numero, valor)

        val conta = buscarConta(numero)
        conta.depositar(valor)
        repository.salvar(conta)

        logger.info("Depósito realizado: numero={}, valor={}, novo_saldo={}", numero, valor, conta.saldo)
    }

    fun sacar(numero: String, valor: BigDecimal) {
        logger.info("Saque solicitado: numero={}, valor={}", numero, valor)

        val conta = buscarConta(numero)
        conta.sacar(valor)
        repository.salvar(conta)

        logger.info("Saque realizado: numero={}, valor={}, novo_saldo={}", numero, valor, conta.saldo)
    }

    fun transferir(numeroOrigem: String, numeroDestino: String, valor: BigDecimal) {
        logger.info("Transferência solicitada: de={}, para={}, valor={}", numeroOrigem, numeroDestino, valor)

        if (numeroOrigem == numeroDestino) {
            logger.warn("Tentativa de transferência para a mesma conta: {}", numeroOrigem)
            throw IllegalArgumentException("Não é possível transferir para a mesma conta")
        }

        val contaOrigem = buscarConta(numeroOrigem)
        val contaDestino = buscarConta(numeroDestino)

        // Tenta sacar da origem
        contaOrigem.sacar(valor)

        // Se chegou aqui, o saque funcionou. Deposita no destino.
        contaDestino.depositar(valor)

        // Ambas as operações funcionaram, salva as duas
        repository.salvar(contaOrigem)
        repository.salvar(contaDestino)

        logger.info(
            "Transferência concluída: de={} (novo_saldo={}), para={} (novo_saldo={}), valor={}",
            numeroOrigem, contaOrigem.saldo,
            numeroDestino, contaDestino.saldo,
            valor
        )
    }

    fun listarContas(): List<Conta> {
        logger.debug("Listando todas as contas")
        return repository.listar()
    }

    private fun gerarNumero(): String {
        logger.debug("Gerando novo número de conta")
        return UUID.randomUUID().toString().take(8)
    }
}