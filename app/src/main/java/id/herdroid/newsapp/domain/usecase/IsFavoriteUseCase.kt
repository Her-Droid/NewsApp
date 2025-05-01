package id.herdroid.newsapp.domain.usecase

import id.herdroid.newsapp.domain.repository.FavoriteRepository
import javax.inject.Inject

class IsFavoriteUseCase @Inject constructor(private val repository: FavoriteRepository) {
    suspend operator fun invoke(url: String): Boolean = repository.isFavorite(url)
}