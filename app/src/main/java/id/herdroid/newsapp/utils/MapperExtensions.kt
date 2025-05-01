package id.herdroid.newsapp.utils

import id.herdroid.newsapp.data.local.entity.FavoriteArticleEntity
import id.herdroid.newsapp.data.model.Article
import id.herdroid.newsapp.data.model.Source

fun Article.toEntity(): FavoriteArticleEntity {
    return FavoriteArticleEntity(
        url = url,
        title = title,
        author = author,
        publishedAt = publishedAt,
        urlToImage = urlToImage,
        description = description,
        sourceId = source?.id ?: "",
        sourceName = source?.name ?: "",
        sourceDescription = source?.description ?: "",
        sourceUrl = source?.url ?: "",
        sourceCategory = source?.category ?: "",
        sourceLanguage = source?.language ?: "",
        sourceCountry = source?.country ?: ""
    )
}

fun FavoriteArticleEntity.toArticle(): Article {
    return Article(
        title = title,
        url = url,
        author = author,
        publishedAt = publishedAt,
        urlToImage = urlToImage,
        description = description,
        source = Source(
            id = sourceId,
            name = sourceName,
            description = sourceDescription,
            url = sourceUrl,
            category = sourceCategory,
            language = sourceLanguage,
            country = sourceCountry
        )
    )
}


fun FavoriteArticleEntity.toDomain(): Article {
    return toArticle()
}


