package ar.edu.unalm.pruebas.presentation.diffutil

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import ar.edu.unalm.pruebas.R
import ar.edu.unalm.pruebas.databinding.ItemLanguageBinding
import ar.edu.unalm.pruebas.entities.Language

class LanguageAdapter(var list: MutableLiveData<MutableList<Language>>) :
    Adapter<LanguageHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LanguageHolder {
        val view =
            (LayoutInflater.from(parent.context).inflate(R.layout.item_language, parent, false))
        return LanguageHolder(view)
    }

    override fun onBindViewHolder(holder: LanguageHolder, position: Int) {
        holder.render(list.value!!)
    }

    override fun getItemCount(): Int = list.value?.size ?: 0

}

class LanguageHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemLanguageBinding.bind(view)

    fun render(listLanguage: MutableList<Language>){
        binding.codigoText.text = listLanguage[0].language_id.toString()
        binding.nameText.text = listLanguage[0].language_name
        binding.voiceText.text = listLanguage[0].language_voice
    }
}
