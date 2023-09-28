package ar.edu.unalm.pruebaspeechtotext.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import ar.edu.unalm.pruebaspeechtotext.databinding.FragmentHomeBinding
import com.google.zxing.integration.android.IntentIntegrator

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        binding.btnScanner.setOnClickListener { initScanner() }
    }

    private fun initScanner() {
        IntentIntegrator(requireActivity()).initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)

        if (result != null) {

            if (result.contents == null) {
                Toast.makeText(requireContext(), "sin contenido", Toast.LENGTH_SHORT).show()
                binding.txt.text = "Sin contenido"
                binding.txt.visibility = View.VISIBLE
            } else {
                Toast.makeText(requireContext(), "Escaneo exitoso", Toast.LENGTH_SHORT).show()
                binding.txt.visibility = View.VISIBLE

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}