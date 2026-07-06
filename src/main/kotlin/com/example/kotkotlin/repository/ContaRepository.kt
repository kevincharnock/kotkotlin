package com.example.kotkotlin.repository

import com.example.kotkotlin.domain.Conta

interface ContaRepository {
    fun salvar(conta: Conta): Conta
    fun buscarPorNumero(numero: String): Conta?
    fun listar(): List<Conta>
    fun excluir(numero: String)
}