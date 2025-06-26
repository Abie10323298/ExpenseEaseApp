package com.example.expenseeaseapp


import androidx.lifecycle.LiveData




class CategoryRepository(private val categoryDao: CategoryDao) {
    val getAllCategories: LiveData<List<Category>> = categoryDao.getAllCategories()

    suspend fun insertCategory(category: Category) {
        categoryDao.insertCategory(category)
    }
    suspend fun delete(category: Category){
        categoryDao.delete(category)
    }
}