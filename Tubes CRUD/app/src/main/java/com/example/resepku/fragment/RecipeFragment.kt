package com.example.resepku.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.resepku.Constants.Companion.API_KEY
import com.example.resepku.Constants.Companion.BASE_URL
import com.example.resepku.DashboardActivity
import com.example.resepku.R
import com.example.resepku.RecipeDetailActivity
import com.example.resepku.SpoonacularApi
import com.example.resepku.adapter.RecipesAdapter
import com.example.resepku.databinding.FragmentRecipeBinding
import com.example.resepku.databinding.ListRecipeBinding
import com.example.resepku.model.FoodRecipe
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RecipeFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecipesAdapter
    private lateinit var binding : FragmentRecipeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentRecipeBinding.inflate(inflater, container, false)
//        val view = inflater.inflate(R.layout.fragment_recipe, container, false)

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val spoonacularApi = retrofit.create(SpoonacularApi::class.java)

        val call = spoonacularApi.getRecipes(
            apiKey = API_KEY,
            addRecipeInformation = true,
            number = 10,
            offset = 0
        )

        call.enqueue(object : Callback<FoodRecipe> {
            override fun onResponse(call: Call<FoodRecipe>, response: Response<FoodRecipe>) {
                if (!response.isSuccessful) {
                    return
                }
                val recipes = response.body()
                val results = recipes?.results

//                recyclerView = view.findViewById(R.id.recipesRecyclerView)
                recyclerView = binding.recipesRecyclerView
                recyclerView.layoutManager = LinearLayoutManager(activity)
                adapter = RecipesAdapter(results, activity!!)
                recyclerView.adapter = adapter
            }

            override fun onFailure(call: Call<FoodRecipe>, t: Throwable) {
                // Handle failure
                return
            }
        })
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}