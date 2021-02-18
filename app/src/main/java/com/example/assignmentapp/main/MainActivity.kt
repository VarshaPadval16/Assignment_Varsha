package com.example.assignmentapp.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.assignmentapp.R
import com.example.assignmentapp.databinding.ActivityMainBinding
import com.example.assignmentapp.utils.NetworkUtils.isInternetAvailable
import com.example.assignmentapp.utils.NetworkUtils.showErrorToast

class MainActivity : AppCompatActivity(),onDataSetChangedCalled {

    companion object{
        val TAG = MainActivity::class.java.simpleName
    }

    private lateinit var binding: ActivityMainBinding
    private var movieList = ArrayList<Episode>()
    private lateinit var mainActivityViewModel: MainActivityViewModel
    private var movieAdapter: MovieAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        mainActivityViewModel = ViewModelProvider(
            this,
            MainActivityViewModelFactory(this.applicationContext)
        ).get(MainActivityViewModel::class.java)

        //searchview
        binding.searchView.isIconified = false
        val closeButton: ImageView =
            binding.searchView.findViewById(androidx.appcompat.R.id.search_close_btn) as ImageView
        closeButton.setOnClickListener {
            binding.searchView.isIconified = false
            binding.searchView.setQuery("", false);
        }

        //get movie list
        getMovieList()

        //search
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.d(TAG, "onQueryTextSubmit $query")
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.d(TAG, "onQueryTextChange $newText ")
                movieAdapter!!.filter.filter(newText.toString())
                return false
            }
        })

        //observe data to set to list
        mainActivityViewModel.getMovieListResponse().observe(this, Observer {
            try {
                if (null == it) {
                    Log.d(TAG, "it null")
                    return@Observer
                }
                if (it.response) {
                    setListingData(it.episodes)
                }
            } catch (ex: Exception) {
                Log.d(TAG,"Exception: $ex")
                setEmptyListView()
            }
        })
    }

    private fun setListingData(list: ArrayList<Episode>) {
        if (list.isNotEmpty()) {
            makeRecyclerVisible()
            movieList = list
            setListingAdapter(movieList)
        } else {
            setEmptyListView()
        }
    }

    private fun setEmptyListView() {
        //show no result found text if list is empty
        binding.textNoResult.visibility = View.VISIBLE
        binding.recyclerView.visibility = View.GONE
    }

    private fun setListingAdapter(list: List<Episode>) {
        //set list to adapter
        movieAdapter = MovieAdapter(this, list,this)
        binding.myAdapter = movieAdapter
        movieAdapter!!.notifyDataSetChanged()
    }

    private fun getMovieList() {
        //get movie list
        if (this.isInternetAvailable()) {
            mainActivityViewModel.getMovieList()
        } else {
            this.showErrorToast(this.getString(R.string.internet_not_avl))
        }
    }

    override fun onDataSetChangedCalled(list: ArrayList<Episode>) {
        Log.d(TAG,"onDataSetChangedCalled: ${list.size}")
        if (list.isEmpty()){
            setEmptyListView()
        }else{
            makeRecyclerVisible()
        }
    }

    private fun makeRecyclerVisible() {
        binding.textNoResult.visibility = View.GONE
        binding.recyclerView.visibility = View.VISIBLE
    }
}