// src/main/java/com/overclock/meucapital/Lancamento.kt
package com.overclock.meucapital

data class Lancamento(
    val descricao: String,
    val valor: Double,
    val data: String,
    val tipo: String
)