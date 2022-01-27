package com.awais.mvvmnavdaggerunit.animaldetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.awais.mvvmnavdaggerunit.animalslist.AnimalListModel
import com.awais.mvvmnavdaggerunit.base.BaseFragment
import com.example.mvvmdagger2navigationunit.databinding.FragmentAnimalDetailBinding

class AnimalDetailFragment : BaseFragment<FragmentAnimalDetailBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentAnimalDetailBinding
        get() = FragmentAnimalDetailBinding::inflate

    private lateinit var animalListModel: AnimalListModel
    private val arg: AnimalDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        animalListModel = arg.animalModel
        binding.animalModel = animalListModel
    }
}