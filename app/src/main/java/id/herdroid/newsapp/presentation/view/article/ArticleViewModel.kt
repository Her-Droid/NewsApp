package id.herdroid.newsapp.presentation.view.article

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import dagger.hilt.android.lifecycle.HiltViewModel
import id.herdroid.newsapp.data.model.Article
import id.herdroid.newsapp.domain.repository.NewsRepository
import id.herdroid.newsapp.domain.usecase.GetArticlesBySourceUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleViewModel @Inject constructor(
    private val getArticlesBySourceUseCase: GetArticlesBySourceUseCase,
    private val newsRepository: NewsRepository
) : ViewModel() {

    fun getArticlesPager(sourceId: String): Pager<Int, Article> {
        return Pager(PagingConfig(pageSize = 20)) {
            object : PagingSource<Int, Article>() {
                override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
                    val page = params.key ?: 1
                    return try {
                        val articles = getArticlesBySourceUseCase(sourceId, page)
                        LoadResult.Page(
                            data = articles,
                            prevKey = if (page == 1) null else page - 1,
                            nextKey = if (articles.isEmpty()) null else page + 1
                        )
                    } catch (e: Exception) {
                        LoadResult.Error(e)
                    }
                }

                override fun getRefreshKey(state: PagingState<Int, Article>): Int? = null
            }
        }
    }

    private val _offlineArticles = MutableLiveData<List<Article>>()
    val offlineArticles: LiveData<List<Article>> get() = _offlineArticles

    fun loadOfflineArticles() {
        viewModelScope.launch {
            val cached = newsRepository.getCachedArticles()
            _offlineArticles.value = cached
        }
    }
}
