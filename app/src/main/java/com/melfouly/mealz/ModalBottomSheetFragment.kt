package com.melfouly.mealz

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.melfouly.mealz.databinding.FragmentModalBottomSheetBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ModalBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentModalBottomSheetBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment.
        binding = FragmentModalBottomSheetBinding.inflate(layoutInflater)

        val strCategoryArg = ModalBottomSheetFragmentArgs.fromBundle(requireArguments()).strCategory

        fetchMeals(strCategoryArg)

        // Observing meals stateFlow with bottomSheet.
        lifecycleScope.launch {
            viewModel.meals.collect { state ->

                if (state.isLoading) {
                    binding.progressBar.visibility = View.VISIBLE
                } else if (state.success != null) {
                    binding.categoryTv.text = strCategoryArg
                    binding.progressBar.visibility = View.INVISIBLE
                    binding.mealNameValue.text = state.success.meals[0].strMeal
                    Log.d("Main", "state: ${state.success.meals[0].strMeal}")
                    binding.mealIdValue.text = state.success.meals[0].idMeal
                } else {
                    binding.progressBar.visibility = View.INVISIBLE
                    binding.categoryTv.text = state.error
                }
            }
        }

        return binding.root
    }

    private fun fetchMeals(categoryName: String) {
        lifecycleScope.launch {
            viewModel.userIntent.send(MainIntent.FetchMeals(categoryName))
        }
    }


}