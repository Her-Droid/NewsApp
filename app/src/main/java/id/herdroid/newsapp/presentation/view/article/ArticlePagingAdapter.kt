package id.herdroid.newsapp.presentation.view.article

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.herdroid.newsapp.R
import id.herdroid.newsapp.data.model.Article
import id.herdroid.newsapp.utils.getTimeAgo

class ArticlePagingAdapter(
    private val onClick: (Article) -> Unit
) : PagingDataAdapter<Article, ArticlePagingAdapter.ArticleViewHolder>(DIFF_CALLBACK) {

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article) = oldItem.url == newItem.url
            override fun areContentsTheSame(oldItem: Article, newItem: Article) = oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_article, parent, false)
        return ArticleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(article: Article) {
            itemView.findViewById<TextView>(R.id.tvTitle).text = article.title
            itemView.findViewById<TextView>(R.id.tvSource).text = "${getTimeAgo(article.publishedAt)} • ${article.source?.name.orEmpty()}"
            val imageView = itemView.findViewById<ImageView>(R.id.imgThumbnail)
            Glide.with(itemView.context)
                .load(article.urlToImage)
                .placeholder(R.drawable.placeholder)
                .into(imageView)

            itemView.setOnClickListener { onClick(article) }
        }

    }
}
