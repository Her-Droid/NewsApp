package id.herdroid.newsapp.data.mapper

import id.herdroid.newsapp.data.local.entity.OfflineSourceEntity
import id.herdroid.newsapp.data.model.Source


fun Source.toEntity(): OfflineSourceEntity {
    return OfflineSourceEntity(
        id = id,
        name = name,
        description = description,
        category = category
    )
}

fun OfflineSourceEntity.toDomain(): Source {
    return Source(
        id = id,
        name = name,
        description = description,
        category = category,
        url = "", language = "", country = ""
    )
}



