package id.herdroid.newsapp.domain.usecase

import id.herdroid.newsapp.data.model.Source
import id.herdroid.newsapp.domain.repository.NewsRepository
import javax.inject.Inject

class GetSourcesUseCase @Inject constructor(private val repository: NewsRepository) {
    suspend operator fun invoke(category: String): List<Source> {
        return repository.getSources(category)
    }
}
