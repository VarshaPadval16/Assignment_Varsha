package com.example.assignmentapp.main

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.assignmentapp.BR
import com.example.assignmentapp.R
import com.example.assignmentapp.databinding.RowMovieItemBinding
import kotlin.collections.ArrayList

class MovieAdapter(
    val context: Context,
    var episodeList: List<Episode>,
    var listner: onDataSetChangedCalled
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>(), Filterable {

    companion object{
        val TAG = MovieAdapter::class.java.simpleName
    }

    private lateinit var layoutInflater: LayoutInflater
    private var originalEpisodeList: List<Episode> = episodeList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        layoutInflater = LayoutInflater.from(parent.context)
        val binding: RowMovieItemBinding = DataBindingUtil.inflate(
            layoutInflater, R.layout.row_movie_item,
            parent,
            false
        )
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val imgUrl =
            "https://m.media-amazon.com/images/M/MV5BNjM0NTc0NzItM2FlYS00YzEwLWE0YmUtNTA2ZWIzODc2OTgxXkEyXkFqcGdeQXVyNTgwNzIyNzg@._V1_SX300.jpg"
        Glide.with(context)
            .load(imgUrl)
            .placeholder(android.R.drawable.ic_menu_gallery)
            .into(holder.binding.imgPoster)

        holder.bind(episodeList[position])
    }

    override fun getItemCount(): Int {
        return episodeList.size
    }

    class MovieViewHolder(val binding: RowMovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(obj: Episode) {
            binding.setVariable(BR.movie, obj)
            binding.executePendingBindings()
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence): FilterResults {
                val results = FilterResults()
                if (constraint != null) {
                    var filterData = ArrayList<Episode>()

                    if (originalEpisodeList == null) {
                        originalEpisodeList = ArrayList<Episode>(episodeList) // saves the original data in mOriginalValues
                    }

                    if (constraint == null || constraint.length === 0) {
                        // set the Original result to return
                        results.count  = originalEpisodeList.size
                        results.values = originalEpisodeList
                    } else {
                        var searchString = constraint.toString().toLowerCase()
                        for (i in 0 until originalEpisodeList.size) {
                            val data: String = originalEpisodeList[i].title
                            if (data.toLowerCase().startsWith(searchString)) {
                                filterData.add(originalEpisodeList[i])
                            }
                            // set the Filtered result to return
                            results.count = filterData.size
                            results.values = filterData
                        }

                    }
                    Log.d(TAG, "FilterResults: " + filterData + "...count: " + filterData!!.size)

                }
                return results
            }

            override fun publishResults(constraint: CharSequence, results: FilterResults) {
                Log.d(TAG, "publishResults: " + results + "...count: " + results.count)

                episodeList = ArrayList<Episode>()

                if (results != null ) {
                    episodeList = results.values as ArrayList<Episode>
                    notifyDataSetChanged()
                } else {
                    notifyDataSetChanged()
                }
                listner.onDataSetChangedCalled(episodeList as ArrayList<Episode>)
            }
        }
    }
}

interface onDataSetChangedCalled{
    fun onDataSetChangedCalled(list : ArrayList<Episode>)
}