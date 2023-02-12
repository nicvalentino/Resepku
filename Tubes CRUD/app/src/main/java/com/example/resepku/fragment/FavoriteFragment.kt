package com.example.resepku.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.resepku.Constants
import com.example.resepku.R
import com.example.resepku.SpoonacularApi
import com.example.resepku.adapter.RecipesAdapter
import com.example.resepku.database.FavoriteDBHelper
import com.example.resepku.databinding.FragmentFavoriteBinding
import com.example.resepku.databinding.FragmentRecipeBinding
import com.example.resepku.model.FoodRecipe
import com.example.resepku.model.Result
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FavoriteFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecipesAdapter
    private lateinit var binding : FragmentFavoriteBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        val favoriteDBHelper = FavoriteDBHelper(requireContext())
        val results = ArrayList<Result>()

        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val spoonacularApi = retrofit.create(SpoonacularApi::class.java)

        val favoriteIds = favoriteDBHelper.retrieveFavorites()
        favoriteIds.forEach { favoriteId ->
            val call = spoonacularApi.getRecipeDetails(
                id = favoriteId,
                apiKey = Constants.API_KEY
            )

            println("tes $favoriteId")

            call.enqueue(object : Callback<Result> {
                override fun onResponse(call: Call<Result>, response: Response<Result>) {
                    if (!response.isSuccessful) {
                        return
                    }
                    val result = response.body()
                    println("response dari $favoriteId : $result")
                    if (result != null) {
                        results.add(result)
                    } else {
                        println("Lewat tapi tidak add")
                    }
                }

                override fun onFailure(call: Call<Result>, t: Throwable) {
                    // Handle failure
                    return
                }
            })
        }

        val listResults: List<Result> = results

        println("Binding ke adapter : $listResults")
        recyclerView = binding.recipesRecyclerView2
        recyclerView.layoutManager = LinearLayoutManager(activity)
        adapter = RecipesAdapter(listResults, requireActivity())
        recyclerView.adapter = adapter
        println("RECYCLER VIEW BERHASIL DIBUATTTTTTTTTTTTT")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imageViewLoad.setOnClickListener() {
            adapter.notifyDataSetChanged()
            Toast.makeText(activity, "Reloading ...", Toast.LENGTH_SHORT).show()
            binding.imageViewLoad.isInvisible = true
            binding.textViewLoad.isInvisible = true
        }
        binding.textViewLoad.setOnClickListener() {
            adapter.notifyDataSetChanged()
            Toast.makeText(activity, "Reloading ...", Toast.LENGTH_SHORT).show()
            binding.imageViewLoad.isInvisible = true
            binding.textViewLoad.isInvisible = true
        }
    }
}