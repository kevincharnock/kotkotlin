package com.example.kotkotlin.repository

import com.example.kotkotlin.domain.Conta
import com.example.kotkotlin.domain.exception.ContaNaoEncontradaException
import org.springframework.stereotype.Repository

@Repository
class InMemoryContaRepository : ContaRepository {

    private val contas: MutableMap<String, Conta> = mutableMapOf()

    override fun salvar(conta: Conta): Conta {
        contas[conta.numero] = conta
        return conta
    }

    override fun buscarPorNumero(numero: String): Conta? {
        return contas[numero]
    }

    override fun listar(): List<Conta> {
        return contas.values.toList()
    }

    override fun excluir(numero: String) {
        if (!contas.containsKey(numero)) throw ContaNaoEncontradaException(numero)
        contas.remove(numero)
    }
}