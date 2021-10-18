package com.example.mtstest1.authorList

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.mtstest1.Models.Author
import com.example.mtstest1.network.NetClient

class AuthorListVM(private val context: Context){

    private val LAST_SEARCH = "lastSearch"
    private val TAG = "AuthorListVM"

    private val preferences: SharedPreferences = context.getSharedPreferences(LAST_SEARCH,Context.MODE_PRIVATE)
    val authorListLive = MutableLiveData<List<Author>>()
    val searchTextListener: SearchView.OnQueryTextListener = object : SearchView.OnQueryTextListener {

        override fun onQueryTextChange(newText: String): Boolean {
            Log.d(TAG, "search text = $newText")
            //TODO: добавить поиск при вводе с задержкой
            return false
        }

        override fun onQueryTextSubmit(query: String): Boolean {
            getAuthorsByName(query)
            setLastSearch(query)
            return false
        }

    }

    fun getAuthorsByName(searchString: String) {
        NetClient.getAuthorList(searchString) { data, error ->
            if (error != null) {
                Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
            } else {
                if (data?.author != null) {
                    authorListLive.value = data.author!!
                } else {
                    Toast.makeText(context, "Ничего не найдено", Toast.LENGTH_SHORT).show()
                    authorListLive.value = ArrayList()
                }
            }
        }
    }

    fun getLastSearch() = preferences.getString(LAST_SEARCH, null)
    fun setLastSearch(value:String) = preferences.edit().putString(LAST_SEARCH, value).apply()
}