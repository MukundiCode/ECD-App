/**
 * @author Tinashe Mukundi Chitamba and Suvanth Ramruthen
 * Class observes database changes and notifies UI, ie livedata
 */

package com.example.ecd_app.room

import androidx.lifecycle.*
import kotlinx.coroutines.launch

class PostsViewModel(private val repository: PostsRepository) : ViewModel() {
    val allPosts: LiveData<List<Post>> = repository.allPosts.asLiveData()

    fun insert(post: Post) = viewModelScope.launch {
        repository.insert(post)
    }
    fun exists(post_title: String): Boolean {
        return repository.exists(post_title)
    }
    fun deleteAll() = viewModelScope.launch {
        repository.deleteAll()
    }

    //suvanth
    fun searchDatabase(searchQuery: String): LiveData<List<Post>>{
        return repository.searchDatabase(searchQuery).asLiveData()
    }

    //suvanth
    fun filterDatabase(filterQuery: String): LiveData<List<Post>>{
        return repository.filterDatabase(filterQuery).asLiveData()
    }

    fun getPostByCategory(meta: String): LiveData<List<Post>>{
        return repository.getByCategory(meta).asLiveData()
    }

    fun getPostByTitle(title: String): List<Post>{
        return repository.getByTitle(title)
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