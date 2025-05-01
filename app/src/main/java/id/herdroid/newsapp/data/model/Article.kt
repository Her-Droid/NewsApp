package id.herdroid.newsapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Article(
    val title: String,
    val url: String,
    val author: String?,
    val publishedAt: String,
    val urlToImage: String?,
    val description: String?,
    val source: Source?
) : Parcelable

