package com.practice.simplepaging

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.liveData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.practice.simplepaging.adapter.MovieListAdapter
import com.practice.simplepaging.api.RetrofitApiFactory
import com.practice.simplepaging.data.MovieDataSource
import com.practice.simplepaging.viewmodel.MovieViewModel
import com.practice.simplepaging.viewmodel.MovieViewModelFactory
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val TAG = "panda"

    lateinit var viewModel: MovieViewModel
    lateinit var movieListAdapter: MovieListAdapter
    lateinit var recyclerView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.recyclerView)

        setupViewModel()
        setupMovieList()
        setupViewWithPagingData()
    }

    private fun setupViewWithPagingData() {
        lifecycleScope.launch {
            viewModel.movieData.collect {
                    movieListAdapter.submitData(it)
            }
        }
    }

    //using livedata
    private fun pagingWithoutViewModel() {
        lifecycleScope.launch {
          val moviePageData = Pager(PagingConfig(pageSize = 6)) {
                MovieDataSource(RetrofitApiFactory.retrofitApi)
            }.liveData.cachedIn(lifecycleScope)

            moviePageData.observe(this@MainActivity, {
               movieListAdapter.submitData(lifecycle, it)
            })
        }
    }

    private fun setupMovieList() {
        movieListAdapter = MovieListAdapter()
        recyclerView.apply {
            layoutManager = GridLayoutManager(this@MainActivity, 2)
            adapter = movieListAdapter
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
                this,
                MovieViewModelFactory()
            )[MovieViewModel::class.java]
    }

}