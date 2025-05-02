package id.herdroid.newsapp.data.mapper

import id.herdroid.newsapp.data.local.entity.OfflineArticleEntity
import id.herdroid.newsapp.data.model.Article
import id.herdroid.newsapp.data.model.ArticleDto
import id.herdroid.newsapp.data.model.Source
import id.herdroid.newsapp.data.model.SourceDto

fun ArticleDto.toDomain(): Article {
    return Article(
        url = url,
        title = title.orEmpty(),
        author = author,
        publishedAt = publishedAt.orEmpty(),
        urlToImage = urlToImage,
        description = description,
        source = source.toDomain(),
    )
}

fun SourceDto.toDomain(): Source {
    return Source(
        id = id.orEmpty(),
        name = name.orEmpty(),
        description = description.orEmpty(),
        url = url.orEmpty(),
        category = category.orEmpty(),
        language = language.orEmpty(),
        country = country.orEmpty()
    )
}


fun Article.toEntity(sourceId: String): OfflineArticleEntity {
    return OfflineArticleEntity(
        url = this.url,
        title = this.title,
        description = this.description,
        urlToImage = this.urlToImage,
        publishedAt = this.publishedAt,
        sourceName = this.source?.name,
        author = this.author,
        sourceId = sourceId
    )
}


fun OfflineArticleEntity.toDomain(): Article {
    return Article(
        url = url,
        title = title,
        description = description,
        urlToImage = urlToImage,
        publishedAt = publishedAt ?: "",
        source = Source(
            id = "",
            name = sourceName.orEmpty(),
            description = "",
            url = "",
            category = "",
            language = "",
            country = ""
        ),
        author = author
    )
}



