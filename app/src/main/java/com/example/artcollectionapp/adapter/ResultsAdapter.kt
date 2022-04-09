package com.example.artcollectionapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.artcollectionapp.R
import com.example.artcollectionapp.model.`object`.Art

class ResultsAdapter(
    private val resultsClickAdapter: ResultsClickAdapter,
    private val resultsList: MutableList<Art> = mutableListOf()
): RecyclerView.Adapter<ResultsViewHolder>() {

    fun clearList(){
        val size = resultsList.size
        resultsList.clear()
        notifyItemRangeRemoved(0, size)
    }


    fun addList(art: List<Art>){
        resultsList.clear()
        resultsList.addAll(art)
        notifyDataSetChanged()
    }

    fun addMore(art: List<Art>){
        val currentSize = resultsList.size
        resultsList.addAll(art)
        notifyItemRangeInserted(currentSize - 1, art.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultsViewHolder {
        val resultsView = LayoutInflater.from(parent.context)
            .inflate(R.layout.results_items, parent, false)
        return ResultsViewHolder(resultsView)
    }

    override fun onBindViewHolder(holder: ResultsViewHolder, position: Int) {
        val result = resultsList[position]

        holder.itemView.setOnClickListener(){
            resultsClickAdapter.onResultsClicked(result)
        }

        holder.bind(result)
    }

    override fun getItemCount(): Int = resultsList.size


}

class ResultsViewHolder(
    private val resultsView: View
): RecyclerView.ViewHolder(resultsView){

    private val artistName: TextView = resultsView.findViewById(R.id.resultsArtist)
    private val artName: TextView = resultsView.findViewById(R.id.resultsArtName)
    private val culture: TextView = resultsView.findViewById(R.id.resultsCulture)
    private val artWork: ImageView = resultsView.findViewById(R.id.resultItemImage)

    fun bind(art: Art){

        artistName.text = art.artistDisplayName
        artName.text = art.title
        culture.text = art.culture

        val image = if(!art.primaryImageSmall.isNullOrEmpty()){
            art.primaryImageSmall
        }else if(!art.primaryImage.isNullOrEmpty()){
            art.primaryImage
        }else {
            null
        }

        Glide.with(resultsView)
            .load(image)
            .placeholder(R.drawable.ic_baseline_camera_alt_24)
            .error(R.drawable.broken_image)
            .fallback(R.drawable.no_image_plain)
            .into(artWork)
    }
}