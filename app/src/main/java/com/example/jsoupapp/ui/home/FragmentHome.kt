package com.example.jsoupapp.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import com.example.jsoupapp.R
import com.example.jsoupapp.databinding.FragmentHomeBinding
import com.example.jsoupapp.util.UiState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentHome : Fragment(R.layout.fragment_home) {

    private var _binding : FragmentHomeBinding ? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<HomeViewModel>()
    private lateinit var adapter : HomeRvAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        adapter = HomeRvAdapter()
        viewModel.listDummy.asLiveData().observe(viewLifecycleOwner){
            it?.let { state ->
                when(state){
                    is UiState.Success -> it.data?.let { it1 -> adapter.setData(it1) }
                    else -> {}
                }
            }
        }
        binding.rvHome.adapter = adapter
        binding.rvHome.setHasFixedSize(true)

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}