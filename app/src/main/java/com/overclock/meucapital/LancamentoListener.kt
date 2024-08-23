package com.overclock.meucapital

interface LancamentoListener {
    fun onLancamentoAdicionado(descricao: String, valor: Double, data: String, tipo: String)
}