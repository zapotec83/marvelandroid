package com.jorider.example.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.jorider.data.common.Constants
import com.jorider.data.common.error.Error
import com.jorider.example.databinding.FragmentHeroesListBinding
import com.jorider.example.ui.adapter.HeroesAdapter
import com.jorider.example.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HeroesListFragment : Fragment() {

    private var viewBinding: FragmentHeroesListBinding? = null

    private val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var heroesAdapter: HeroesAdapter

    private val binding get() = viewBinding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        viewBinding = FragmentHeroesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        heroesAdapter = HeroesAdapter(requireContext())

        binding.fragmentContentList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = heroesAdapter

            binding.fragmentContentList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    detectNeedToReload()
                }
            })
        }

        mainViewModel.marvelComics.observe(viewLifecycleOwner) { resultWrapper ->
            resultWrapper.doSuccess {
                heroesAdapter.setList(it)
            }
            resultWrapper.doFail {
                showError(it)
            }
        }

        mainViewModel.refreshHeroesList()
    }

    private fun detectNeedToReload() {
        val layoutManager = binding.fragmentContentList.layoutManager as LinearLayoutManager
        val totalItemCount = layoutManager.itemCount
        val lastVisible = layoutManager.findLastVisibleItemPosition()
        val endHasBeenReached = lastVisible + (Constants.PAGE_SIZE / 2) >= totalItemCount
        if (totalItemCount > 0 && endHasBeenReached) {
            mainViewModel.refreshHeroesList()
        }
    }

    // TODO Diversify error messages according to error (not the same internet connection as API_KEY...)
    private fun showError(it: Error) {
        Snackbar.make(requireContext(), requireView(), it.toString(), Snackbar.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }
}