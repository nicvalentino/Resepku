package com.example.resepku.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.resepku.R
import com.example.resepku.RecipeDetailActivity
import com.example.resepku.databinding.ListRecipeBinding
import com.example.resepku.model.Result
import com.example.resepku.viewholder.RecipeViewHolder
import kotlinx.coroutines.withContext

class RecipesAdapter(private val results: List<Result>?, private val context: Context) : RecyclerView.Adapter<RecipeViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ListRecipeBinding>(layoutInflater, R.layout.list_recipe, parent, false)

        return RecipeViewHolder(binding)
    }

    override fun getItemCount(): Int = results?.size!!

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.binding.result = results!![position]
        holder.binding.executePendingBindings()

        holder.binding.cardViewRecipe.setOnClickListener() {
            val intent = Intent(context, RecipeDetailActivity::class.java)
            intent.putExtra("result_id", results[position].id)
            context.startActivity(intent)
        }

        holder.binding.imageViewRecipe.setOnClickListener() {
            val intent = Intent(context, RecipeDetailActivity::class.java)
            intent.putExtra("result_id", results[position].id)
            context.startActivity(intent)
        }

        holder.binding.textViewRecipeName.setOnClickListener() {
            val intent = Intent(context, RecipeDetailActivity::class.java)
            intent.putExtra("result_id", results[position].id)
            context.startActivity(intent)
        }

        holder.binding.textViewRecipeDescription.setOnClickListener() {
            val intent = Intent(context, RecipeDetailActivity::class.java)
            intent.putExtra("result_id", results[position].id)
            context.startActivity(intent)
        }
    }
}