package id.herdroid.newsapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.herdroid.newsapp.data.local.entity.FavoriteArticleEntity

@Dao
interface FavoriteArticleDao {

    @Query("SELECT * FROM favorite_articles")
    suspend fun getAllFavorites(): List<FavoriteArticleEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(article: FavoriteArticleEntity)

    @Delete
    suspend fun deleteFavorite(article: FavoriteArticleEntity)

    @Query("SELECT EXISTS(SELECT * FROM favorite_articles WHERE url = :url)")
    suspend fun isFavorite(url: String): Boolean
}

