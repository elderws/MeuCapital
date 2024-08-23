package com.overclock.meucapital

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.NumberFormat
import java.util.Locale

class MainActivity : AppCompatActivity(), LancamentoListener {

    private lateinit var lancamentoAdapter: LancamentoAdapter
    private lateinit var tvTotalReceita: TextView
    private lateinit var tvTotalDespesa: TextView
    private lateinit var tvSaldo: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnNovoLancamento: Button = findViewById(R.id.btnNovoLancamento)
        btnNovoLancamento.setOnClickListener {
            val intent = Intent(this, NovoLancamentoActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_NOVO_LANCAMENTO)
        }

        val btnLimparLancamentos: Button = findViewById(R.id.btnLimparLancamentos)
        btnLimparLancamentos.setOnClickListener {
            lancamentoAdapter.clearLancamentos()
            atualizarTotais()
        }

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        lancamentoAdapter = LancamentoAdapter()
        recyclerView.adapter = lancamentoAdapter

        tvTotalReceita = findViewById(R.id.tvTotalReceita)
        tvTotalDespesa = findViewById(R.id.tvTotalDespesa)
        tvSaldo = findViewById(R.id.tvSaldo)

        adicionarDadosPadrao()
        atualizarTotais()
    }

    private fun adicionarDadosPadrao() {
        val dadosPadrao = listOf(
            Lancamento("Salario", 5000.0, "20/08/2024", "Receita"),
            Lancamento("Agua", 120.0, "22/08/2024", "Despesa"),
            Lancamento("Faculdade", 400.0, "24/08/2024", "Despesa")
        )
        dadosPadrao.forEach { lancamentoAdapter.addLancamento(it) }
    }

    private fun atualizarTotais() {
        val totalReceita = lancamentoAdapter.getTotalReceita()
        val totalDespesa = lancamentoAdapter.getTotalDespesa()
        val saldo = totalReceita - totalDespesa

        val formatador = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))

        tvTotalReceita.text = "Total Receita: ${formatador.format(totalReceita)}"
        tvTotalDespesa.text = "Total Despesa: ${formatador.format(totalDespesa)}"
        tvSaldo.text = "Saldo: ${formatador.format(saldo)}"
    }

    fun showEditDialog(position: Int) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_edit, null)
        val etDescricao = dialogView.findViewById<EditText>(R.id.etDescricao)
        val etTipo = dialogView.findViewById<EditText>(R.id.etTipo)
        val etValor = dialogView.findViewById<EditText>(R.id.etValor)
        val etData = dialogView.findViewById<EditText>(R.id.etData)

        val lancamento = lancamentoAdapter.getLancamento(position)
        etDescricao.setText(lancamento.descricao)
        etTipo.setText(lancamento.tipo)
        etValor.setText(lancamento.valor.toString())
        etData.setText(lancamento.data)

        val dialog = AlertDialog.Builder(this)
            .setTitle("Editar Lançamento")
            .setView(dialogView)
            .setPositiveButton("Salvar") { _, _ ->
                val descricao = etDescricao.text.toString()
                val tipo = etTipo.text.toString()
                val valor = etValor.text.toString().toDouble()
                val data = etData.text.toString()
                updateLancamento(position, Lancamento(descricao, valor, data, tipo))
            }
            .setNegativeButton("Cancelar", null)
            .create()

        dialog.show()
    }

    private fun updateLancamento(position: Int, newLancamento: Lancamento) {
        lancamentoAdapter.updateLancamento(position, newLancamento)
        atualizarTotais()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_NOVO_LANCAMENTO && resultCode == RESULT_OK) {
            val descricao = data?.getStringExtra(EXTRA_DESCRICAO) ?: return
            val valor = data.getDoubleExtra(EXTRA_VALOR, 0.0)
            val dataLancamento = data.getStringExtra(EXTRA_DATA) ?: return
            val tipo = data.getStringExtra(EXTRA_TIPO) ?: return
            onLancamentoAdicionado(descricao, valor, dataLancamento, tipo)
            atualizarTotais()
        }
    }

    override fun onLancamentoAdicionado(descricao: String, valor: Double, data: String, tipo: String) {
        val novoLancamento = Lancamento(descricao, valor, data, tipo)
        lancamentoAdapter.addLancamento(novoLancamento)
        atualizarTotais()
    }

    companion object {
        const val REQUEST_CODE_NOVO_LANCAMENTO = 1
        const val EXTRA_DESCRICAO = "descricao"
        const val EXTRA_VALOR = "valor"
        const val EXTRA_DATA = "data"
        const val EXTRA_TIPO = "tipo"
    }
}