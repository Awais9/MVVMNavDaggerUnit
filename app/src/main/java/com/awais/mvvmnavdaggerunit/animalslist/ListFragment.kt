package com.awais.mvvmnavdaggerunit.animalslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.awais.mvvmnavdaggerunit.base.BaseFragment
import com.example.mvvmdagger2navigationunit.databinding.FragmentListBinding
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class ListFragment : BaseFragment<FragmentListBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentListBinding
        get() = FragmentListBinding::inflate

    private lateinit var viewModel: ListViewModel
    private lateinit var listAdapter: AnimalListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(ListViewModel::class.java)
        setObservers()
        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.swipeRefreshLayout.isRefreshing = false
            callRefresh(true)
        }
    }

    private fun callRefresh(hardRefresh: Boolean) {
        lifecycleScope.launch {
            viewModel.refresh(hardRefresh)
        }
    }

    private fun setObservers() {
        listAdapter = AnimalListAdapter(arrayListOf()).apply {
            selectedAnimal.observe(viewLifecycleOwner) {
                findNavController().navigate(
                    ListFragmentDirections.actionListFragmentToAnimalDetailFragment(
                        it
                    )
                )
            }
        }
        viewModel.animals.observe(viewLifecycleOwner) {
            it?.let {
                binding.animalListRV.visibility = View.VISIBLE
                listAdapter.updateData(it as ArrayList<AnimalListModel>)
            }
        }
        viewModel.loading.observe(viewLifecycleOwner) {
            binding.loadingPB.visibility = if (it) View.VISIBLE else View.GONE
            if (it) {
                binding.errorTV.visibility = View.GONE
                binding.animalListRV.visibility = View.GONE
            }
        }
        viewModel.loadError.observe(viewLifecycleOwner) {
            binding.errorTV.visibility = if (it) View.VISIBLE else View.GONE
            if (it) {
                binding.loadingPB.visibility = View.GONE
                binding.animalListRV.visibility = View.GONE
            }
        }
        callRefresh(false)
        binding.animalListRV.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            adapter = listAdapter
        }
    }
}












