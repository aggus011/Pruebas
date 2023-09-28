package ar.edu.unalm.pruebaspeechtotext

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import ar.edu.unalm.pruebaspeechtotext.R
import ar.edu.unalm.pruebaspeechtotext.databinding.ActivityMainBinding
import ar.edu.unalm.pruebaspeechtotext.databinding.FragmentHomeBinding
import com.google.zxing.integration.android.IntentIntegrator

class MainActivity : AppCompatActivity() {


    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnScanner.setOnClickListener { initScanner() }
    }

    private fun initScanner() {
        IntentIntegrator(this).initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)

        if (result != null) {

            if (result.contents == null) {
                Toast.makeText(this, "sin contenido", Toast.LENGTH_SHORT).show()
                binding.txt.text = "Sin contenido"
                binding.txt.visibility = View.VISIBLE
            } else {
                Toast.makeText(this, "Escaneo exitoso", Toast.LENGTH_SHORT).show()
                binding.txt.visibility = View.VISIBLE

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}