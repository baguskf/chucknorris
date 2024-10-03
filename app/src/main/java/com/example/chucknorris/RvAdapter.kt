package com.example.chucknorris

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.chucknorris.api.response.Joke


class RvAdapter(private var jokes: List<Joke>) : RecyclerView.Adapter<RvAdapter.JokeViewHolder>() {

    class JokeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvJoke: TextView = itemView.findViewById(R.id.tv_item_title)
        val ivImage: ImageView = itemView.findViewById(R.id.iv_item_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JokeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return JokeViewHolder(view)
    }

    override fun onBindViewHolder(holder: JokeViewHolder, position: Int) {
        when (val joke = jokes[position]) {
            is Joke.Jokes -> {
                holder.tvJoke.text = joke.jokes.value
                Glide.with(holder.itemView)
                    .load(joke.jokes.iconUrl)
                    .into(holder.ivImage)
            }
            is Joke.Result -> {
                holder.tvJoke.text = joke.result.value
                Glide.with(holder.itemView)
                    .load(joke.result.iconUrl)
                    .into(holder.ivImage)
            }
        }
    }

    override fun getItemCount() = jokes.size

    fun queryJokes(newJokes: List<Joke>) {
        jokes = newJokes
        notifyDataSetChanged()
    }
}

