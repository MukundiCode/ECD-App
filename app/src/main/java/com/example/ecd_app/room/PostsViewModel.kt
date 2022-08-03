package com.example.ecd_app.room

import androidx.lifecycle.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class PostsViewModel(private val repository: PostsRepository) : ViewModel() {
    // Using LiveData and caching what allWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allPosts: LiveData<List<Post>> = repository.allPosts.asLiveData()
    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(post: Post) = viewModelScope.launch {
        repository.insert(post)
    }
    fun deleteAll() = viewModelScope.launch {
        repository.deleteAll()
    }

    //suvanth
    fun searchDatabase(searchQuery: String): LiveData<List<Post>>{
        return repository.searchDatabase(searchQuery).asLiveData()
    }
}
class PostsViewModelFactory(private val repository: PostsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PostsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PostsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}