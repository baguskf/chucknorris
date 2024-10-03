package com.example.chucknorris


import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chucknorris.api.response.Joke


import com.example.chucknorris.databinding.ActivityMainBinding



class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private lateinit var adapter: RvAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.RvListJokes.layoutManager = LinearLayoutManager(this)
        adapter = RvAdapter(emptyList())
        binding.RvListJokes.adapter = adapter

        viewModel.jokes.observe(this) { jokes ->
            val jokesList = jokes.map { jokesItem -> Joke.Jokes(jokesItem) }
            adapter.queryJokes(jokesList)
        }

        viewModel.isLoading.observe(this){
            setLoading(it)
        }

        binding.searchButton.setOnClickListener {
            val searchText = binding.searchEditText.text.toString()
            if (searchText.isNotEmpty()) {
                viewModel.searchQuery(searchText)
            } else {
                    Toast.makeText( this,"Masukkan teks pencarian", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.query.observe(this) { results ->
            if (results.isNotEmpty()) {
                val jokesList = results.map { resultItem -> Joke.Result(resultItem) }
                adapter.queryJokes(jokesList)
            } else {
                Toast.makeText(this, "Tidak ada hasil ditemukan", Toast.LENGTH_SHORT).show()
            }
        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.myFav -> {
                refreshJokes()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setLoading(isLoading : Boolean){
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun refreshJokes() {
        viewModel.getJokes(15)
    }


}

