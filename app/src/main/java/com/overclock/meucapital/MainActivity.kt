// src/main/java/com/overclock/meucapital/MainActivity.kt
package com.overclock.meucapital

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(), LancamentoListener {

    private lateinit var lancamentoAdapter: LancamentoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnNovoLancamento: Button = findViewById(R.id.btnNovoLancamento)
        btnNovoLancamento.setOnClickListener {
            val intent = Intent(this, NovoLancamentoActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_NOVO_LANCAMENTO)
        }

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        lancamentoAdapter = LancamentoAdapter()
        recyclerView.adapter = lancamentoAdapter

        // Adicionar dados padrão
        adicionarDadosPadrao()
    }

    private fun adicionarDadosPadrao() {
        val dadosPadrao = listOf(
            Lancamento("Salario", 5000.0, "20/08/2024", "Receita"),
            Lancamento("Agua", 120.0, "22/08/2024", "Despesa"),
            Lancamento("Faculdade", 400.0, "24/08/2024", "Despesa")
        )
        dadosPadrao.forEach { lancamentoAdapter.addLancamento(it) }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_NOVO_LANCAMENTO && resultCode == RESULT_OK) {
            val descricao = data?.getStringExtra(EXTRA_DESCRICAO) ?: return
            val valor = data.getDoubleExtra(EXTRA_VALOR, 0.0)
            val dataLancamento = data.getStringExtra(EXTRA_DATA) ?: return
            val tipo = data.getStringExtra(EXTRA_TIPO) ?: return
            onLancamentoAdicionado(descricao, valor, dataLancamento, tipo)
        }
    }

    override fun onLancamentoAdicionado(descricao: String, valor: Double, data: String, tipo: String) {
        val novoLancamento = Lancamento(descricao, valor, data, tipo)
        lancamentoAdapter.addLancamento(novoLancamento)
    }

    companion object {
        const val REQUEST_CODE_NOVO_LANCAMENTO = 1
        const val EXTRA_DESCRICAO = "descricao"
        const val EXTRA_VALOR = "valor"
        const val EXTRA_DATA = "data"
        const val EXTRA_TIPO = "tipo"
    }
}