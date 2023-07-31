package com.melfouly.mealz

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.melfouly.mealz.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment.
        binding = FragmentMainBinding.inflate(layoutInflater)

        fetchCategories()

        val adapter = MealsAdapter(MealsAdapter.CategoryClickListener {
            Log.d("Main", "Adapter: onClick for ${it.strCategory} ")
            this.findNavController()
                .navigate(MainFragmentDirections.actionMainFragmentToModalBottomSheetFragment(it.strCategory))
        })

        // Observing categories stateFlow.
        lifecycleScope.launch {
            viewModel.categories.collect { state ->
                if (state.isLoading) {
                    binding.progressBar.visibility = View.VISIBLE
                } else if (state.success != null) {
                    adapter.submitList(state.success.categories)
                    binding.progressBar.visibility = View.INVISIBLE
                    binding.categoryRv.adapter = adapter
                } else {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.errorTv.text = state.error
                }
            }
        }

        return binding.root


    }

    private fun fetchCategories() {
        lifecycleScope.launch {
            viewModel.userIntent.send(MainIntent.FetchCategories)
        }
    }


}