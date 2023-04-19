package ar.edu.unalm.pruebaspeechtotext.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import ar.edu.unalm.pruebaspeechtotext.databinding.FragmentHomeBinding
import ar.edu.unalm.pruebaspeechtotext.room.viewmodel.LanguageViewModel

class HomeFragment: Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    //view models
    private val languageViewModel by activityViewModels<LanguageViewModel>()

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

        languageViewModel.allLanguage.observe(viewLifecycleOwner){
            createRecyclerView()
        }

    }

    private fun createRecyclerView() {
        val adapter = LanguageAdapter(languageViewModel.allLanguage)
    }
}