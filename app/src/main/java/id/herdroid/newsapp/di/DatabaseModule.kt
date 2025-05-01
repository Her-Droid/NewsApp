package id.herdroid.newsapp.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import id.herdroid.newsapp.data.local.AppDatabase
import id.herdroid.newsapp.data.local.dao.FavoriteArticleDao
import id.herdroid.newsapp.data.local.dao.OfflineArticleDao
import id.herdroid.newsapp.data.local.dao.OfflineSourceDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(app, AppDatabase::class.java, "news_database")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideOfflineArticleDao(database: AppDatabase): OfflineArticleDao {
        return database.offlineArticleDao()
    }

    @Provides
    @Singleton
    fun provideOfflineSourceDao(database: AppDatabase): OfflineSourceDao {
        return database.offlineSourceDao()
    }


    @Provides
    @Singleton
    fun provideFavoriteArticleDao(database: AppDatabase): FavoriteArticleDao {
        return database.favoriteArticleDao()
    }


}

