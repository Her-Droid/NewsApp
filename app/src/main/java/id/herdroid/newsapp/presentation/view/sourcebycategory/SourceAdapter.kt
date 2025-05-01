package id.herdroid.newsapp.presentation.view.sourcebycategory

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import id.herdroid.newsapp.R
import id.herdroid.newsapp.data.model.Source

class SourceAdapter(
    private val onClick: (Source) -> Unit
) : RecyclerView.Adapter<SourceAdapter.SourceViewHolder>() {

    private val sources = mutableListOf<Source>()

    fun submitList(newSources: List<Source>) {
        sources.clear()
        sources.addAll(newSources)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SourceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_source, parent, false)
        return SourceViewHolder(view)
    }

    override fun onBindViewHolder(holder: SourceViewHolder, position: Int) {
        holder.bind(sources[position])
    }

    override fun getItemCount(): Int = sources.size

    inner class SourceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(source: Source) {
            itemView.findViewById<TextView>(R.id.tvSourceName).text = source.name
            itemView.findViewById<TextView>(R.id.tvSourceDescription).text = source.description
            itemView.setOnClickListener { onClick(source) }
        }
    }
}
