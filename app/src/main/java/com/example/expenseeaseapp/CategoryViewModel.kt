package com.example.expenseeaseapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CategoryViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: CategoryRepository
    val categories = AppDatabase.getDatabase(application).categoryDao().getAllCategories()

    init {
        val dao = AppDatabase.getDatabase(application).categoryDao()
        repository = CategoryRepository(dao)
    }

    fun insert(category: Category) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertCategory(category)
        }
    }

    fun delete(category: Category) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(category)
        }
    }
}