package id.herdroid.newsapp.domain.usecase

import id.herdroid.newsapp.data.model.Article
import id.herdroid.newsapp.domain.repository.FavoriteRepository
import javax.inject.Inject

class RemoveFromFavoriteUseCase @Inject constructor(private val repository: FavoriteRepository) {
    suspend operator fun invoke(article: Article) = repository.removeFromFavorite(article)
}