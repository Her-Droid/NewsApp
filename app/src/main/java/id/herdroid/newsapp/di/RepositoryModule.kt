package id.herdroid.newsapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.herdroid.newsapp.data.api.NewsApiService
import id.herdroid.newsapp.data.local.dao.FavoriteArticleDao
import id.herdroid.newsapp.data.local.dao.OfflineArticleDao
import id.herdroid.newsapp.data.local.dao.OfflineSourceDao
import id.herdroid.newsapp.data.repository.FavoriteRepositoryImpl
import id.herdroid.newsapp.data.repository.NewsRepositoryImpl
import id.herdroid.newsapp.domain.repository.FavoriteRepository
import id.herdroid.newsapp.domain.repository.NewsRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideNewsRepository(
        apiService: NewsApiService,
        offlineArticleDao: OfflineArticleDao,
        offlineSourceDao: OfflineSourceDao
    ): NewsRepository {
        return NewsRepositoryImpl(apiService, offlineArticleDao, offlineSourceDao)
    }

    @Provides
    @Singleton
    fun provideFavoriteRepository(dao: FavoriteArticleDao): FavoriteRepository {
        return FavoriteRepositoryImpl(dao)
    }
}
