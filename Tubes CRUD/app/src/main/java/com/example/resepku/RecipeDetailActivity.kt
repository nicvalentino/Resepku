package com.example.resepku

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.resepku.Constants.Companion.API_KEY
import com.example.resepku.Constants.Companion.BASE_URL
import com.example.resepku.database.FavoriteDBHelper
import com.example.resepku.databinding.ActivityRecipeDetailBinding
import com.example.resepku.model.Favorite
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.resepku.model.Result

class RecipeDetailActivity : AppCompatActivity() {
    private lateinit var binding : ActivityRecipeDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        binding = DataBindingUtil.setContentView<ActivityRecipeDetailBinding>(this, R.layout.activity_recipe_detail)
        val favoriteDBHelper = FavoriteDBHelper(this)
        binding = ActivityRecipeDetailBinding.inflate(layoutInflater)

        setSupportActionBar(binding.toolbar)
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val spoonacularApi = retrofit.create(SpoonacularApi::class.java)

        val call = spoonacularApi.getRecipeDetails(
            id = intent.getIntExtra("result_id", 716429),
            apiKey = API_KEY
        )

        call.enqueue(object : Callback<Result> {
            override fun onResponse(call: Call<Result>, response: Response<Result>) {
                if (!response.isSuccessful) {
                    return
                }
                val result = response.body()
                binding.result = result
            }

            override fun onFailure(call: Call<Result>, t: Throwable) {
                // Handle failure
                return
            }
        })
        if(favoriteDBHelper.isFavorite(intent.getIntExtra("result_id", 716429))) {
            binding.imageView2.setColorFilter(Color.parseColor("#FF8F00"))
        }


        setContentView(binding.root)

        binding.imageView2.setOnClickListener() {
            val favoriteDBHelper = FavoriteDBHelper(this)
            if (favoriteDBHelper.isFavorite(intent.getIntExtra("result_id", 716429))) {
                favoriteDBHelper.deleteFavorite(intent.getIntExtra("result_id", 716429))
                Toast.makeText(this, "Berhasil dihapus dari favorit", Toast.LENGTH_SHORT).show()
                binding.imageView2.setColorFilter(Color.DKGRAY)
            } else {
                favoriteDBHelper.insertFavorite(Favorite(intent.getIntExtra("result_id", 716429)))
                Toast.makeText(this, "Berhasil ditambahkan ke favorit", Toast.LENGTH_SHORT).show()
                binding.imageView2.setColorFilter(Color.parseColor("#FF8F00"))
            }
        }
    }
}

