package id.herdroid.newsapp.domain.usecase

import id.herdroid.newsapp.data.model.Article
import id.herdroid.newsapp.domain.repository.NewsRepository
import javax.inject.Inject

class GetArticlesBySourceUseCase @Inject constructor(
    private val repository: NewsRepository
) {
    suspend operator fun invoke(sourceId: String, page: Int = 1): List<Article> {
        return repository.getArticlesBySource(sourceId, page)
    }
}

