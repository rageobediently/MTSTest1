package com.example.mtstest1.oneAuthor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.mtstest1.R
import com.example.mtstest1.network.NetClient
import kotlinx.android.synthetic.main.activity_one_author.*

class OneAuthorActivity : AppCompatActivity() {
    private val TAG = "OneAuthorActivity"
    lateinit var vm: OneAuthorVM

    companion object {
        const val ONE_ID = "author_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_one_author)

        vm = OneAuthorVM(this,intent)
        vm.getAuthorData()
        subscribe()
    }
    private fun subscribe(){
        vm.authorLive.observe(this, Observer {
            nameText.text = it.authordisplay
            idText.text = it.authorid
            lastInitialText.text = it.lastinitial
        })

    }


}