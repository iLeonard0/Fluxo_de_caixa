package unipar.edu.utfpr.usandocomponentesvisuais

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.CheckBox
import android.widget.DatePicker
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity() {

    private lateinit var barra: Spinner
    private lateinit var main : LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        main = findViewById(R.id.main)
        barra = findViewById(R.id.barra)


    }
    fun btTestarComponenteOnClick(view: View) {

        Thread {
                for (i in 0 .. 100) {
                    barra
                }
        }
    }
}

