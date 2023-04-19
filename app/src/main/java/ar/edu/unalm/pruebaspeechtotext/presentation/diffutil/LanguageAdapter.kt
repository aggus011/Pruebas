package ar.edu.unalm.pruebaspeechtotext.presentation.diffutil

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import ar.edu.unalm.pruebaspeechtotext.entities.Language

class LanguageAdapter (var list: MutableLiveData<List<Language>>):
 Adapter<LanguageHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LanguageHolder {
        val view =
            (LayoutInflater.from(parent.context).inflate())
    }

    override fun onBindViewHolder(holder: LanguageHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

}

class LanguageHolder(view: View): RecyclerView.ViewHolder(view){

}
