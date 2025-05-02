package id.herdroid.newsapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import id.herdroid.newsapp.data.local.dao.FavoriteArticleDao
import id.herdroid.newsapp.data.local.dao.OfflineArticleDao
import id.herdroid.newsapp.data.local.dao.OfflineSourceDao
import id.herdroid.newsapp.data.local.entity.FavoriteArticleEntity
import id.herdroid.newsapp.data.local.entity.OfflineArticleEntity
import id.herdroid.newsapp.data.local.entity.OfflineSourceEntity

@Database(
    entities = [FavoriteArticleEntity::class, OfflineArticleEntity::class, OfflineSourceEntity::class],
    version = 3,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteArticleDao(): FavoriteArticleDao
    abstract fun offlineArticleDao(): OfflineArticleDao
    abstract fun offlineSourceDao(): OfflineSourceDao
}
