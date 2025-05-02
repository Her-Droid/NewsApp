package id.herdroid.newsapp.data.repository

import id.herdroid.newsapp.data.api.NewsApiService
import id.herdroid.newsapp.data.local.dao.OfflineArticleDao
import id.herdroid.newsapp.data.local.dao.OfflineSourceDao
import id.herdroid.newsapp.data.mapper.toDomain
import id.herdroid.newsapp.data.model.Article
import id.herdroid.newsapp.data.model.Source
import id.herdroid.newsapp.domain.repository.NewsRepository
import id.herdroid.newsapp.data.mapper.toEntity
import javax.inject.Inject


class NewsRepositoryImpl @Inject constructor(
    private val apiService: NewsApiService,
    private val offlineArticleDao: OfflineArticleDao,
    private val offlineSourceDao: OfflineSourceDao
) : NewsRepository {

    override suspend fun getArticlesBySource(sourceId: String, page: Int): List<Article> {
        val response = apiService.getTopHeadlinesBySource(sourceId = sourceId, page = page)
        val articles = response.body()?.articles?.map { it.toDomain() } ?: emptyList()
        if (page == 1) {
            val entities = articles.map { it.toEntity(sourceId) }
            offlineArticleDao.clearCachedArticlesBySource(sourceId)
            offlineArticleDao.insertArticles(entities)
        }

        return articles
    }


    override suspend fun searchArticles(query: String, page: Int): List<Article> {
        val response = apiService.searchArticles(query = query, page = page)
        return response.body()?.articles?.map { it.toDomain() } ?: emptyList()
    }

    override suspend fun getSources(category: String): List<Source> {
        return try {
            val response = apiService.getSources(category)
            if (response.isSuccessful) {
                val sources = response.body()?.sources ?: emptyList()
                cacheSources(sources, category)
                sources
            } else {
                offlineSourceDao.getCachedSources(category).map { it.toDomain() }
            }
        } catch (e: Exception) {
            offlineSourceDao.getCachedSources(category).map { it.toDomain() }
        }
    }

    override suspend fun cacheSources(sources: List<Source>, category: String) {
        val entities = sources.map { it.toEntity() }
        offlineSourceDao.clearCachedSources(category)
        offlineSourceDao.insertSources(entities)
    }

    override suspend fun getLatestArticlesFromRemote(): List<Article> {
        return try {
            val response = apiService.searchArticles(query = "latest", page = 1)
            val articles = response.body()?.articles?.map { it.toDomain() } ?: emptyList()
            cacheArticles(articles)
            articles
        } catch (e: Exception) {
            offlineArticleDao.getCachedArticles().map { it.toDomain() }
        }
    }

    override suspend fun cacheArticles(articles: List<Article>) {
        val groupedBySourceId = articles.groupBy { it.source?.id.orEmpty() }

        groupedBySourceId.forEach { (sourceId, articlesForSource) ->
            val entities = articlesForSource.map { it.toEntity(sourceId) }
            offlineArticleDao.clearCachedArticlesBySource(sourceId)
            offlineArticleDao.insertArticles(entities)
        }
    }



    override suspend fun getCachedArticlesBySource(sourceId: String): List<Article> {
        return offlineArticleDao.getCachedArticlesBySource(sourceId).map { it.toDomain() }
    }

}