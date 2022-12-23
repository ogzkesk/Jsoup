package com.example.jsoupapp.ui.currency

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import androidx.navigation.fragment.findNavController
import com.example.jsoupapp.R
import com.example.jsoupapp.data.remote.Euro
import com.example.jsoupapp.databinding.FragmentCurrencyBinding
import com.example.jsoupapp.util.UiState
import com.example.jsoupapp.util.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FragmentCurrency : Fragment(R.layout.fragment_currency) {

    private var _binding: FragmentCurrencyBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<CurrencyViewModel>()
    private val handler = Handler(Looper.getMainLooper())
    private var runnable : Runnable ? = null
    private val delay = 10000L

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCurrencyBinding.bind(view)


        viewLifecycleOwner.lifecycle.addObserver(object : DefaultLifecycleObserver{
            override fun onResume(owner: LifecycleOwner) {
                handler.postDelayed(Runnable {
                    handler.postDelayed(runnable!!,delay)
                    viewModel.checkEuro()
                }.also { runnable = it },delay)
                super.onResume(owner)
            }
        })

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.observeEuro.collect {
                    setData(it)
                }
            }
        }

    }

    private fun setData(state: UiState<Euro>?) {
        state?.let {
            when (it) {
                is UiState.Loading -> binding.progressBar.isVisible = true
                is UiState.Error -> {
                    binding.progressBar.isVisible = false
                    context?.showToast(it.message!!)
                }
                is UiState.Success -> {
                    binding.progressBar.isVisible = false
                    binding.tvAlis.text = it.data?.alis
                    binding.tvSatis.text = it.data?.satis
                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        handler.removeCallbacks(runnable!!)

    }
}