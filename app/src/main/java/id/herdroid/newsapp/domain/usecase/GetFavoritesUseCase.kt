package id.herdroid.newsapp.domain.usecase

import id.herdroid.newsapp.data.model.Article
import id.herdroid.newsapp.domain.repository.FavoriteRepository
import javax.inject.Inject

class GetFavoritesUseCase @Inject constructor(private val repository: FavoriteRepository) {
    suspend operator fun invoke(): List<Article> = repository.getFavorites()
}