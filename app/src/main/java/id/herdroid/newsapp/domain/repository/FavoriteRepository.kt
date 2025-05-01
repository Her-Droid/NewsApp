package id.herdroid.newsapp.domain.repository

import id.herdroid.newsapp.data.model.Article

interface FavoriteRepository {
    suspend fun addToFavorite(article: Article)
    suspend fun removeFromFavorite(article: Article)
    suspend fun getFavorites(): List<Article>
    suspend fun isFavorite(url: String): Boolean
}

