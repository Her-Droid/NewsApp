package id.herdroid.newsapp.presentation.view.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.herdroid.newsapp.data.local.entity.FavoriteArticleEntity
import id.herdroid.newsapp.domain.repository.FavoriteRepository
import id.herdroid.newsapp.data.model.Article
import id.herdroid.newsapp.domain.usecase.AddToFavoriteUseCase
import id.herdroid.newsapp.domain.usecase.GetFavoritesUseCase
import id.herdroid.newsapp.domain.usecase.IsFavoriteUseCase
import id.herdroid.newsapp.domain.usecase.RemoveFromFavoriteUseCase
import id.herdroid.newsapp.utils.toEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val getFavoritesUseCase: GetFavoritesUseCase,
    private val removeFromFavoriteUseCase: RemoveFromFavoriteUseCase
) : ViewModel() {

    private val _favorites = MutableLiveData<List<Article>>()
    val favorites: LiveData<List<Article>> = _favorites

    fun loadFavorites() {
        viewModelScope.launch {
            _favorites.value = getFavoritesUseCase()
        }
    }

    fun removeFavorite(article: Article) {
        viewModelScope.launch {
            removeFromFavoriteUseCase(article)
            loadFavorites()
        }
    }
}
