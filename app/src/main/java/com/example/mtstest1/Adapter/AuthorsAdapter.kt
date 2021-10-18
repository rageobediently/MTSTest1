package com.example.mtstest1.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.*
import com.example.mtstest1.Models.*
import com.example.mtstest1.R
import com.example.mtstest1.oneAuthor.OneAuthorActivity
import kotlinx.android.synthetic.main.recycle_item.view.*

class AuthorsAdapter(
    private val context: Context): RecyclerView.Adapter<AuthorsAdapter.AuthorViewHolder>() {

    var authorList: ArrayList<Author> = ArrayList()

    class AuthorViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val authorName: TextView = itemView.authorName
        private val authorId: TextView = itemView.authorId

        fun bind(listItem: Author) {
            authorName.text = listItem.authordisplay
            authorId.text = listItem.authorid
            itemView.setOnClickListener {
                Toast.makeText(it.context, "нажал на ${itemView.authorName.text}", Toast.LENGTH_SHORT).show()
                val oneAuthorIntent = Intent(it.context, OneAuthorActivity::class.java)
                oneAuthorIntent.putExtra(OneAuthorActivity.ONE_ID,listItem.authorid)
                it.context.startActivity(oneAuthorIntent)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AuthorViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recycle_item, parent, false)
        return AuthorViewHolder(itemView)
    }

    override fun getItemCount() = authorList.size

    override fun onBindViewHolder(holder: AuthorViewHolder, position: Int) {
        val listItem = authorList[position]
        holder.bind(listItem)


    }

}
