package unipar.edu.utfpr.verificador_de_idade_kotlin

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val resultado = findViewById<TextView>(R.id.txtResultado)
        val inputIdade = findViewById<TextInputEditText>(R.id.inputIdade)
        val botaoValidar = findViewById<Button>(R.id.btValidarIdade)

        botaoValidar.setOnClickListener {
            val idadeInformada = inputIdade.text.toString()

            if (idadeInformada.isNotEmpty()) {
                val idadeInt = idadeInformada.toInt()
                when {
                    idadeInt < 18 -> resultado.text = "Você é menor de idade!"
                    idadeInt in 18..60 -> resultado.text = "Você está na meia idade!"
                    idadeInt > 60 -> resultado.text = "Você é idoso (a)!"
                }
            }
        }
    }
    fun limparValores(view: View) {
        val input = findViewById<TextInputEditText>(R.id.inputIdade)
        input.setText("")  // Limpar o campo de entrada
        val resultado = findViewById<TextView>(R.id.txtResultado)
        resultado.text = ""  // Limpar o texto do resultado
    }
}
