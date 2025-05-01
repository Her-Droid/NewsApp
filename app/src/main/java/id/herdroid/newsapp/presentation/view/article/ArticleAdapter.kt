package id.herdroid.newsapp.presentation.view.article

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.herdroid.newsapp.R
import id.herdroid.newsapp.data.model.Article
import id.herdroid.newsapp.databinding.ItemArticleBinding
import id.herdroid.newsapp.utils.getTimeAgo

class ArticleAdapter(private val onClick: (Article) -> Unit) : ListAdapter<Article, ArticleAdapter.ArticleViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding = ItemArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ArticleViewHolder(private val binding: ItemArticleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(article: Article) {
            binding.tvTitle.text = article.title
            binding.tvSource.text = "${getTimeAgo(article.publishedAt)} • ${article.source?.name.orEmpty()}"


            Glide.with(binding.imgThumbnail.context)
                .load(article.urlToImage)
                .placeholder(R.drawable.placeholder)
                .into(binding.imgThumbnail)

            binding.root.setOnClickListener {
                onClick(article)
            }
        }

    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem.url == newItem.url
            }

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem == newItem
            }
        }
    }
}
