package com.overclock.meucapital

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class NovoLancamentoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_novo_lancamento)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val etDescricao: EditText = findViewById(R.id.etDescricao)
        val etValor: EditText = findViewById(R.id.etValor)
        val etData: EditText = findViewById(R.id.etData)
        val spTipoLancamento: Spinner = findViewById(R.id.spTipoLancamento)
        val btnSalvar: Button = findViewById(R.id.btnSalvar)

        etData.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
                etData.setText(String.format("%02d/%02d/%04d", selectedDay, selectedMonth + 1, selectedYear))
            }, year, month, day)

            datePickerDialog.show()
        }

        btnSalvar.setOnClickListener {
            val descricao = etDescricao.text.toString()
            val valor = etValor.text.toString().toDoubleOrNull()
            val data = etData.text.toString()
            val tipoLancamento = spTipoLancamento.selectedItem.toString()

            if (descricao.isNotEmpty() && valor != null && data.isNotEmpty() && tipoLancamento.isNotEmpty()) {
                val resultIntent = Intent()
                resultIntent.putExtra(MainActivity.EXTRA_DESCRICAO, descricao)
                resultIntent.putExtra(MainActivity.EXTRA_VALOR, valor)
                resultIntent.putExtra(MainActivity.EXTRA_DATA, data)
                resultIntent.putExtra(MainActivity.EXTRA_TIPO, tipoLancamento)
                setResult(RESULT_OK, resultIntent)
                finish()
            } else {
                Toast.makeText(this, "Preencha todos os campos corretamente.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}