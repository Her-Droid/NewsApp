package id.herdroid.newsapp.presentation.view.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.herdroid.newsapp.data.model.Article
import id.herdroid.newsapp.domain.repository.FavoriteRepository
import id.herdroid.newsapp.domain.usecase.AddToFavoriteUseCase
import id.herdroid.newsapp.domain.usecase.IsFavoriteUseCase
import id.herdroid.newsapp.domain.usecase.RemoveFromFavoriteUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val addToFavoriteUseCase: AddToFavoriteUseCase,
    private val removeFromFavoriteUseCase: RemoveFromFavoriteUseCase,
    private val isFavoriteUseCase: IsFavoriteUseCase
) : ViewModel() {

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> get() = _isFavorite

    fun checkIsFavorite(url: String) {
        viewModelScope.launch {
            _isFavorite.value = isFavoriteUseCase(url)
        }
    }

    fun toggleFavorite(article: Article): Boolean {
        viewModelScope.launch {
            val current = _isFavorite.value ?: false
            if (current) {
                removeFromFavoriteUseCase(article)
                _isFavorite.value = false
            } else {
                addToFavoriteUseCase(article)
                _isFavorite.value = true
            }
        }
        return !(_isFavorite.value ?: false)
    }


}



