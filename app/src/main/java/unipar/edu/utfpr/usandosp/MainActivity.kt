package unipar.edu.utfpr.usandosp

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity


private const val PREFERENCE = "PREFERENCE_STAR"

private const val FIELD = "estrela"

class MainActivity : AppCompatActivity() {

    private lateinit var imageView : ImageView
    private var ligado = false
    private lateinit var sharedPreference : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageView = findViewById(R.id.imageView)

        sharedPreference = getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE)

        ligado = sharedPreference.getBoolean(FIELD, false)

        

        if(ligado){
            imageView.setImageResource(android.R.drawable.star_big_on)
        } else {
            imageView.setImageResource(android.R.drawable.star_big_off)
        }

    }

    fun btOnOffOnClick (view : View) {
        if(ligado){
            imageView.setImageResource(android.R.drawable.star_big_off)
        } else {
            imageView.setImageResource(android.R.drawable.star_big_on)
        }
        ligado = !ligado

        var editor = sharedPreference.edit()
        editor.putBoolean(FIELD, ligado)
        editor.commit()

    }
}