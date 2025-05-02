package id.herdroid.newsapp.domain.repository

import id.herdroid.newsapp.data.model.Article
import id.herdroid.newsapp.data.model.Source

interface NewsRepository {
    suspend fun getSources(category: String): List<Source>
    suspend fun cacheSources(sources: List<Source>, category: String)
    suspend fun getArticlesBySource(sourceId: String, page: Int): List<Article>
    suspend fun searchArticles(query: String, page: Int): List<Article>
    suspend fun getLatestArticlesFromRemote(): List<Article>
    suspend fun cacheArticles(articles: List<Article>)
    suspend fun getCachedArticlesBySource(sourceId: String): List<Article>

}

