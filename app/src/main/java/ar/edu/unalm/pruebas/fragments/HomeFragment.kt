package ar.edu.unalm.pruebas.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import ar.edu.unalm.pruebas.databinding.FragmentHomeBinding

import dagger.hilt.android.AndroidEntryPoint
import ar.edu.unalm.pruebas.diffutil.LanguageAdapter
import ar.edu.unalm.pruebas.room.viewmodel.LanguageViewModel

@AndroidEntryPoint
class HomeFragment : Fragment() {

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

        languageViewModel.getAllLanguage()
        languageViewModel.allLanguage.observe(viewLifecycleOwner) {
            if (it != null)
                createRecyclerView()
        }

    }

    private fun createRecyclerView() {
        val adapter = LanguageAdapter(languageViewModel.allLanguage)
        binding.languageRv.layoutManager = LinearLayoutManager(context)
        binding.languageRv.adapter = adapter
    }
}