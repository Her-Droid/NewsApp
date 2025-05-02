package id.herdroid.newsapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.herdroid.newsapp.data.local.entity.OfflineArticleEntity

@Dao
interface OfflineArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticles(articles: List<OfflineArticleEntity>)

    @Query("SELECT * FROM cached_articles WHERE sourceId = :sourceId")
    suspend fun getCachedArticlesBySource(sourceId: String): List<OfflineArticleEntity>

    @Query("DELETE FROM cached_articles WHERE sourceId = :sourceId")
    suspend fun clearCachedArticlesBySource(sourceId: String)

    @Query("SELECT * FROM cached_articles")
    suspend fun getCachedArticles(): List<OfflineArticleEntity>

}

