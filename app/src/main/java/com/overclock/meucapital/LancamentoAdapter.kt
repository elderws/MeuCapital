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
        holder.bind(lancamento)
    }

    override fun getItemCount(): Int = lancamentos.size

    fun addLancamento(lancamento: Lancamento) {
        lancamentos.add(lancamento)
        notifyItemInserted(lancamentos.size - 1)
    }

    class LancamentoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val descricaoTextView: TextView = itemView.findViewById(R.id.tvDescricao)
        private val valorTextView: TextView = itemView.findViewById(R.id.tvValor)
        private val dataTextView: TextView = itemView.findViewById(R.id.tvData)
        private val tipoTextView: TextView = itemView.findViewById(R.id.tvTipo)

        fun bind(lancamento: Lancamento) {
            descricaoTextView.text = lancamento.descricao
            valorTextView.text = lancamento.valor.toString()
            dataTextView.text = lancamento.data
            tipoTextView.text = lancamento.tipo
        }
    }
}