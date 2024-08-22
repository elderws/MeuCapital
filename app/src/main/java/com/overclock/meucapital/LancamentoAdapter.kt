// src/main/java/com/overclock/meucapital/LancamentoAdapter.kt
package com.overclock.meucapital

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class LancamentoAdapter : RecyclerView.Adapter<LancamentoAdapter.LancamentoViewHolder>() {

    private val lancamentos = mutableListOf<Lancamento>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LancamentoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_lancamento, parent, false)
        return LancamentoViewHolder(view)
    }

    override fun onBindViewHolder(holder: LancamentoViewHolder, position: Int) {
        val lancamento = lancamentos[position]
        holder.tvDescricao.text = lancamento.descricao
        holder.tvTipo.text = lancamento.tipo
        holder.tvValor.text = lancamento.valor.toString()
        holder.tvData.text = lancamento.data
    }

    override fun getItemCount(): Int = lancamentos.size

    fun addLancamento(lancamento: Lancamento) {
        lancamentos.add(lancamento)
        notifyItemInserted(lancamentos.size - 1)
    }

    fun clearLancamentos() {
        lancamentos.clear()
        notifyDataSetChanged()
    }

    fun getTotalReceita(): Double {
        return lancamentos.filter { it.tipo == "Receita" }.sumOf { it.valor }
    }

    fun getTotalDespesa(): Double {
        return lancamentos.filter { it.tipo == "Despesa" }.sumOf { it.valor }
    }

    class LancamentoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvDescricao: TextView = itemView.findViewById(R.id.tvDescricao)
        val tvTipo: TextView = itemView.findViewById(R.id.tvTipo)
        val tvValor: TextView = itemView.findViewById(R.id.tvValor)
        val tvData: TextView = itemView.findViewById(R.id.tvData)
    }
}