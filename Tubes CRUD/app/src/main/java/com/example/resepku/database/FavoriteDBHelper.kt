package com.example.resepku.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import com.example.resepku.model.Favorite

class FavoriteDBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){
    companion object {
        private const val DATABASE_NAME = "resepku.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "favorites"
        private const val COLUMN_RECIPE_ID = "recipeId"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE $TABLE_NAME ($COLUMN_RECIPE_ID INTEGER)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertFavorite(favorite: Favorite) {
        val db = writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_RECIPE_ID, favorite.recipeId)
        }
        db.insert(TABLE_NAME, null, contentValues)

        db.close()
    }

    fun retrieveFavorites(): ArrayList<Int> {
        val favorites = ArrayList<Int>()

        // Select all query
        val selectQuery = "SELECT * FROM $TABLE_NAME"

        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery(selectQuery, null)

        // Looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                val favoriteName = cursor.getInt(cursor.getColumnIndex(COLUMN_RECIPE_ID))
                favorites.add(favoriteName)
            } while (cursor.moveToNext())
        }

        // Close cursor
        cursor.close()

        // Return the list of favorites
        return favorites
    }

    fun isFavorite(id: Int): Boolean {
        val db = readableDatabase
        val cursor = db.query(TABLE_NAME, null, "$COLUMN_RECIPE_ID = ?", arrayOf(id.toString()), null, null, null)
        return if (cursor.moveToFirst()) {
            cursor.close()
            db.close()
            true
        } else {
            cursor.close()
            db.close()
            false
        }
    }

    fun deleteFavorite(id: Int): Int {
        val db = this.writableDatabase
        return db.delete(TABLE_NAME, "$COLUMN_RECIPE_ID = ?", arrayOf(id.toString()))
    }
}