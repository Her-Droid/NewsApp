package id.herdroid.newsapp.data.repository

import id.herdroid.newsapp.data.local.dao.FavoriteArticleDao
import id.herdroid.newsapp.data.model.Article
import id.herdroid.newsapp.domain.repository.FavoriteRepository
import javax.inject.Inject
import id.herdroid.newsapp.utils.toDomain
import id.herdroid.newsapp.utils.toEntity

class FavoriteRepositoryImpl @Inject constructor(
    private val dao: FavoriteArticleDao
) : FavoriteRepository {

    override suspend fun addToFavorite(article: Article) {
        dao.insertFavorite(article.toEntity())
    }

    override suspend fun removeFromFavorite(article: Article) {
        dao.deleteFavorite(article.toEntity())
    }

    override suspend fun getFavorites(): List<Article> {
        return dao.getAllFavorites().map { it.toDomain() }
    }

    override suspend fun isFavorite(url: String): Boolean {
        return dao.isFavorite(url)
    }
}

