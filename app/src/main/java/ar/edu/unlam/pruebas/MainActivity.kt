package ar.edu.unlam.pruebas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ar.edu.unalm.pruebas.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}