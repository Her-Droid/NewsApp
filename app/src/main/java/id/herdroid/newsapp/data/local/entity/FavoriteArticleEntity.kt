package id.herdroid.newsapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_articles")
data class FavoriteArticleEntity(
    @PrimaryKey val url: String,
    val title: String,
    val author: String?,
    val publishedAt: String,
    val urlToImage: String?,
    val description: String?,
    val sourceId: String,
    val sourceName: String,
    val sourceDescription: String,
    val sourceUrl: String,
    val sourceCategory: String,
    val sourceLanguage: String,
    val sourceCountry: String
)


