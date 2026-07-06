package com.example.kotkotlin.domain.exception


class ContaNaoEncontradaException(numero: String) :
    RuntimeException("Conta $numero não encontrada")