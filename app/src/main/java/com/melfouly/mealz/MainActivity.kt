package com.melfouly.mealz

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.melfouly.mealz.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private lateinit var bottomSheet: BottomSheetDialog
    private lateinit var progressBar: ProgressBar
    private lateinit var mealTitle: TextView
    private lateinit var mealNameValue: TextView
    private lateinit var mealIdValue: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bottomSheet = BottomSheetDialog(this)
        val sheetView = layoutInflater.inflate(R.layout.bottom_sheet_dialog, null)
        sheetView.apply {
            progressBar = findViewById(R.id.progress_bar)
            mealTitle = findViewById(R.id.category_tv)
            mealNameValue = findViewById(R.id.meal_name_value)
            mealIdValue = findViewById(R.id.meal_id_value)
        }

        fetchCategories()

        val adapter = MealsAdapter(MealsAdapter.CategoryClickListener {
            mealTitle.text = it.strCategory
            bottomSheet.setContentView(sheetView)
            bottomSheet.show()
            bottomSheet.behavior.state = BottomSheetBehavior.STATE_EXPANDED
            Log.d("Main", "Adapter: onClick for ${it.strCategory} ")
            fetchMeals(it.strCategory)
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

        // Observing meals stateFlow with bottomSheet.
        lifecycleScope.launch {
            viewModel.meals.collect { state ->

                if (state.isLoading) {
                    progressBar.visibility = View.VISIBLE
                } else if (state.success != null) {
                    progressBar.visibility = View.INVISIBLE
                    mealNameValue.text = state.success.meals[0].strMeal
                    Log.d("Main", "state: ${state.success.meals[0].strMeal}")
                    mealIdValue.text = state.success.meals[0].idMeal
                } else {
                    progressBar.visibility = View.INVISIBLE
                }
            }
        }

    }

    private fun fetchCategories() {
        lifecycleScope.launch {
            viewModel.userIntent.send(MainIntent.FetchCategories)
        }
    }

    private fun fetchMeals(categoryName: String) {
        lifecycleScope.launch {
            viewModel.userIntent.send(MainIntent.FetchMeals(categoryName))
        }
    }
}