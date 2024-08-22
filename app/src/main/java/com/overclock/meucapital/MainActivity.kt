// app/src/main/java/com/overclock/meucapital/MainActivity.kt
package com.overclock.meucapital

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.NumberFormat
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecursoAdapter
    private lateinit var saldoTextView: TextView
    private lateinit var totalReceitasTextView: TextView
    private lateinit var totalDespesasTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        saldoTextView = findViewById(R.id.saldo)
        totalReceitasTextView = findViewById(R.id.totalReceitas)
        totalDespesasTextView = findViewById(R.id.totalDespesas)

        adapter = RecursoAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        calcularOperacoes()
    }

    private fun calcularOperacoes() {
        val (totalReceitas, totalDespesas, saldo) = adapter.calcularTotais()
        Log.d("MainActivity", "Total Receitas: $totalReceitas, Total Despesas: $totalDespesas, Saldo: $saldo")
        saldoTextView.text = "Saldo: ${formatCurrency(saldo)}"
        totalReceitasTextView.text = "Receitas: ${formatCurrency(totalReceitas)}"
        totalDespesasTextView.text = "Despesas: ${formatCurrency(totalDespesas)}"

    }

    private fun formatCurrency(value: Double): String {
        val format = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
        return format.format(value)
    }
}