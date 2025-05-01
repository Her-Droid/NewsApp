package id.herdroid.newsapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.herdroid.newsapp.data.local.entity.OfflineSourceEntity

@Dao
interface OfflineSourceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSources(sources: List<OfflineSourceEntity>)

    @Query("SELECT * FROM cached_sources WHERE category = :category")
    suspend fun getCachedSources(category: String): List<OfflineSourceEntity>

    @Query("DELETE FROM cached_sources WHERE category = :category")
    suspend fun clearCachedSources(category: String)
}
