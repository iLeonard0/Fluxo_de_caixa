package unipar.edu.utfpr.fluxo_de_caixa

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var transacaoRepo: RepositoryDB
    private var selectedDate: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        transacaoRepo = RepositoryDB(this)

        val spinnerTipo: Spinner = findViewById(R.id.spDebCred)
        val spinnerCategoria: Spinner = findViewById(R.id.spDetalhes)
        val editTextValor: EditText = findViewById(R.id.inputValor)
        val editTextData: TextInputEditText = findViewById(R.id.inputData)
        val buttonAdd: Button = findViewById(R.id.btLancar)
        val buttonVerLancamentos: Button = findViewById(R.id.btLancamentos)
        val buttonSaldo: Button = findViewById(R.id.btSaldo)

        val tipos = resources.getStringArray(R.array.tipos)
        val categoriasCredito = resources.getStringArray(R.array.categorias_credito)
        val categoriasDebito = resources.getStringArray(R.array.categorias_debito)

        val adapterTipo = ArrayAdapter(this, android.R.layout.simple_spinner_item, tipos)
        adapterTipo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerTipo.adapter = adapterTipo

        spinnerTipo.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedTipo = parent?.getItemAtPosition(position) as String
                val categorias =
                    if (selectedTipo == "Crédito") categoriasCredito else categoriasDebito
                val adapterCategoria = ArrayAdapter(
                    this@MainActivity,
                    android.R.layout.simple_spinner_item,
                    categorias
                )
                adapterCategoria.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerCategoria.adapter = adapterCategoria
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        editTextData.setOnClickListener {
            showDatePickerDialog()
        }

        buttonAdd.setOnClickListener {
            val tipo = spinnerTipo.selectedItem.toString()
            val categoria = spinnerCategoria.selectedItem.toString()
            val valor = editTextValor.text.toString().toDoubleOrNull()

            if (valor != null && valor > 0 && selectedDate.isNotEmpty()) {
                val transacao = Transacao(tipo, categoria, valor, selectedDate)
                transacaoRepo.addTransacao(transacao)
                Toast.makeText(this, "Transação adicionada!", Toast.LENGTH_SHORT).show()
                editTextValor.text?.clear()
                editTextData.text?.clear()
            } else {
                Toast.makeText(this, "Preencha todos os campos corretamente!", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        buttonVerLancamentos.setOnClickListener {
            val intent = Intent(this, LancamentoActivity::class.java)
            startActivity(intent)
        }

        buttonSaldo.setOnClickListener {
            showSaldo()
        }
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                selectedDate =
                    String.format("%02d/%02d/%d", selectedDay, selectedMonth + 1, selectedYear)
                findViewById<TextInputEditText>(R.id.inputData).setText(selectedDate)
            },
            year, month, day
        )
        datePickerDialog.show()
    }

    private fun showSaldo() {
        try {
            val saldoCredito = transacaoRepo.getTotalPorTipo("Crédito")
            val saldoDebito = transacaoRepo.getTotalPorTipo("Débito")
            val saldo = saldoCredito - saldoDebito
            val mensagem = "Saldo Atual: R$ ${String.format("%.2f", saldo)}"
            AlertDialog.Builder(this)
                .setTitle("Saldo Atual")
                .setMessage(mensagem)
                .setPositiveButton("OK", null)
                .show()
        } catch (e: Exception) {
            Toast.makeText(this, "Erro ao calcular o saldo: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }
}
