package com.example.mtstest1.oneAuthor

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.mtstest1.Models.Author
import com.example.mtstest1.network.NetClient
import kotlinx.android.synthetic.main.activity_one_author.*

class OneAuthorVM(private val context: Context,private val intent: Intent){
    val authorLive = MutableLiveData<Author>()
    private var TAG = "OneAuthorVM"

    fun getAuthorData() {
        Log.d(TAG, "Ищем автора по Id = ${intent.getStringExtra(OneAuthorActivity.ONE_ID)}")
        NetClient.getAuthorById(intent.getStringExtra(OneAuthorActivity.ONE_ID)!!) { data, error ->
            if (error != null) {
                Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
            } else {
                if (data != null) {
                    authorLive.value = data
                } else {
                    Log.d(TAG, "Пустой элемент")
                }
            }
        }

    }

}