package com.example.ecd_app.room

import androidx.lifecycle.*
import kotlinx.coroutines.launch

class PostsViewModel(private val repository: PostsRepository) : ViewModel() {
    // Using LiveData and caching what allWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allPosts: LiveData<List<Post>> = repository.allPosts.asLiveData()
    //val exists: LiveData<Int> = repository.exists.as
    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(post: Post) = viewModelScope.launch {
        repository.insert(post)
    }
    fun exists(post_title: String): Boolean {
        return repository.exists(post_title)
    }
    fun deleteAll() = viewModelScope.launch {
        repository.deleteAll()
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