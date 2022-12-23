package com.example.jsoupapp.ui.web

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.net.toUri
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.example.jsoupapp.R
import com.example.jsoupapp.data.remote.Source
import com.example.jsoupapp.databinding.FragmentWebBinding
import com.example.jsoupapp.util.UiState
import com.example.jsoupapp.util.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FragmentWeb : Fragment(R.layout.fragment_web) {

    private var _binding: FragmentWebBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<WebViewModel>()
    private lateinit var menuHost: MenuHost
    private val rvAdapter by lazy {
        WebAdapter() {
            startActivity(Intent(Intent.ACTION_VIEW, it.toUri()))
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentWebBinding.bind(view)
        lifeCycleObserver()
        menuHost = binding.toolbar
        setMenu()

        binding.rvWeb.apply {
            adapter = rvAdapter
            setHasFixedSize(true)
        }

        binding.swipeRefresh.setOnRefreshListener {
            binding.progressBar.isVisible = true
            viewModel.searchOnUnsplash("cat")
            binding.swipeRefresh.isRefreshing = false
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.searchOnUnsplash("cat")
            viewModel.searchResult.collect { state ->
                state?.let {
                    setRv(it)
                }
            }
        }
    }

    private fun setMenu() {
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onMenuItemSelected(menuItem: MenuItem) = false
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.search_bar, menu)

                val menuItem = menu.findItem(R.id.search_unsplash)
                val searchView = menuItem.actionView as SearchView

                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextChange(newText: String?) = false
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main){
                            query?.let {
                                if (it.isNotEmpty()) viewModel.searchOnUnsplash(it)
                            }
                        }
                        return true
                    }
                })
            }
        },viewLifecycleOwner,Lifecycle.State.STARTED)
    }

    private fun setRv(state: UiState<List<Source>>) = with(binding) {
        when (state) {
            is UiState.Loading -> progressBar.isVisible = true
            is UiState.Error -> {
                progressBar.isVisible = false
                context?.showToast(state.message!!)
            }
            is UiState.Success -> {
                progressBar.isVisible = false
                rvAdapter.setData(state.data!!)
            }
        }
    }


    private fun lifeCycleObserver() {
        viewLifecycleOwner.lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onResume(owner: LifecycleOwner) {
                super.onResume(owner)
            }

            override fun onPause(owner: LifecycleOwner) {
                super.onPause(owner)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}