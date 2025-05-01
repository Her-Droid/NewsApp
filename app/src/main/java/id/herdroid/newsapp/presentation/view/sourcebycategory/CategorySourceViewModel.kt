package id.herdroid.newsapp.presentation.view.sourcebycategory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.herdroid.newsapp.data.model.Source
import id.herdroid.newsapp.domain.repository.NewsRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategorySourceViewModel @Inject constructor(
    private val newsRepository: NewsRepository
) : ViewModel() {

    private val _sources = MutableLiveData<List<Source>>()
    val sources: LiveData<List<Source>> = _sources

    private var fullList: List<Source> = emptyList()

    fun loadSources(category: String, isOnline: Boolean) {
        viewModelScope.launch {
            try {
                val result = if (isOnline) {
                    newsRepository.getSources(category)
                } else {
                    newsRepository.getSources(category)
                }
                fullList = result
                _sources.postValue(result)
            } catch (e: Exception) {
                _sources.postValue(emptyList())
            }
        }
    }

    fun filterSources(query: String) {
        _sources.value = if (query.isEmpty()) {
            fullList
        } else {
            fullList.filter {
                it.name.contains(query, ignoreCase = true)
            }
        }
    }
}
