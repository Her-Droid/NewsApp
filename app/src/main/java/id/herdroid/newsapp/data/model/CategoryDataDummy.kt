package id.herdroid.newsapp.data.model

import id.herdroid.newsapp.R

object CategoryDataDummy {
    fun getCategories(): List<Category> {
        return listOf(
            Category("business", "Business"),
            Category("entertainment", "Entertainment"),
            Category("general", "General"),
            Category("health", "Health"),
            Category("science", "Science"),
            Category("sports", "Sports"),
            Category("technology", "Technology"),
        )
    }
}
