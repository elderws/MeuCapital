// app/src/main/java/com/overclock/meucapital/RecursoAdapter.kt
package com.overclock.meucapital

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.NumberFormat
import java.util.Locale

class RecursoAdapter : RecyclerView.Adapter<RecursoAdapter.RecursoViewHolder>() {

    val recursos = listOf(
        Recurso("Salário", "Receita", 5001.00, "01/08/2024"),
        Recurso("Aluguel", "Despesa", 1501.00, "05/08/2024"),
        Recurso("Luz", "Despesa", 201.00, "10/08/2024"),
        Recurso("Água", "Despesa", 101.42, "12/08/2024"),
        Recurso("Internet", "Despesa", 151.17, "15/08/2024"),
        Recurso("Mercado", "Despesa", 601.54, "18/08/2024"),
        Recurso("Transporte", "Despesa", 301.00, "20/08/2024"),
        Recurso("Lazer", "Despesa", 201.00, "22/08/2024"),
        Recurso("Freelance", "Receita", 1201.00, "25/08/2024"),
        Recurso("Investimento", "Receita", 801.00, "28/08/2024"),
        Recurso("Academia", "Despesa", 101.00, "01/09/2024"),
        Recurso("Cinema", "Despesa", 51.00, "03/09/2024"),
        Recurso("Restaurante", "Despesa", 201.00, "05/09/2024"),
        Recurso("Curso", "Despesa", 301.00, "07/09/2024"),
        Recurso("Consultoria", "Receita", 1501.00, "10/09/2024"),
        Recurso("Venda", "Receita", 701.00, "12/09/2024"),
        Recurso("Manutenção", "Despesa", 101.00, "15/09/2024"),
        Recurso("Seguro", "Despesa", 501.00, "18/09/2024"),
        Recurso("Viagem", "Despesa", 1001.00, "20/09/2024"),
        Recurso("Presente", "Despesa", 201.00, "22/09/2024")
    )

    class RecursoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val descricao: TextView = itemView.findViewById(R.id.descricao)
        val lancamento: TextView = itemView.findViewById(R.id.lancamento)
        val valor: TextView = itemView.findViewById(R.id.valor)
        val data: TextView = itemView.findViewById(R.id.data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecursoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recurso, parent, false)
        return RecursoViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecursoViewHolder, position: Int) {
        val recurso = recursos[position]
        holder.descricao.text = recurso.descricao
        holder.lancamento.text = recurso.tipoLancamento
        holder.valor.text = formatCurrency(recurso.valor)
        holder.data.text = recurso.data
    }

    override fun getItemCount() = recursos.size

    private fun formatCurrency(value: Double): String {
        val format = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
        return format.format(value)
    }

    fun calcularTotais(): Triple<Double, Double, Double> {
        var totalReceitas = 0.0
        var totalDespesas = 0.0

        for (recurso in recursos) {
            if (recurso.tipoLancamento == "Receita") {
                totalReceitas += recurso.valor
            } else if (recurso.tipoLancamento == "Despesa") {
                totalDespesas += recurso.valor
            }
        }

        val saldo = totalReceitas - totalDespesas
        return Triple(totalReceitas, totalDespesas, saldo)
    }
}