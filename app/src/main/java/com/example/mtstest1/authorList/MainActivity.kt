package com.example.mtstest1.authorList

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mtstest1.Adapter.AuthorsAdapter
import com.example.mtstest1.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    lateinit var vm: AuthorListVM
    lateinit var adapter: AuthorsAdapter




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        vm = AuthorListVM(this)
        initRecycler()
        initSearch()
        subscribe()
        if (vm.getLastSearch()!=null){
            search.setQuery(vm.getLastSearch(),true)
            println(vm.getLastSearch())
        }

    }

    private fun initRecycler() {
        val layoutManager = LinearLayoutManager(this)
        recycle_authors.setHasFixedSize(true)
        recycle_authors.layoutManager = layoutManager
        adapter = AuthorsAdapter(this)
        recycle_authors.adapter = adapter
    }

    private fun initSearch() {
        val searchView: SearchView = findViewById(R.id.search)
        searchView.setOnQueryTextListener(vm.searchTextListener)
    }
    private fun subscribe(){
        vm.authorListLive.observe(this, Observer {
            adapter.authorList.clear()
            adapter.authorList.addAll(it)
            adapter.notifyDataSetChanged()
        })
    }
}

