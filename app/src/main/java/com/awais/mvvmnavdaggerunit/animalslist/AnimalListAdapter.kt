package com.awais.mvvmnavdaggerunit.animalslist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmdagger2navigationunit.R
import com.example.mvvmdagger2navigationunit.databinding.AnimalItemRowBinding

class AnimalListAdapter(private val animalAnimalList: ArrayList<AnimalListModel>) :
    RecyclerView.Adapter<AnimalListAdapter.AnimalViewHolder>() {

    val selectedAnimal by lazy { MutableLiveData<AnimalListModel>() }

    fun updateData(newAnimalAnimalList: ArrayList<AnimalListModel>) {
        animalAnimalList.clear()
        animalAnimalList.addAll(newAnimalAnimalList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalViewHolder {
        return AnimalViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context), R.layout.animal_item_row, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: AnimalViewHolder, position: Int) {
        holder.view.animalModel = animalAnimalList[holder.layoutPosition]
        holder.itemView.apply {
            setOnClickListener {
                selectedAnimal.value = animalAnimalList[holder.layoutPosition]
            }
        }
    }

    override fun getItemCount(): Int = animalAnimalList.size

    class AnimalViewHolder(var view: AnimalItemRowBinding) : RecyclerView.ViewHolder(view.root)

}