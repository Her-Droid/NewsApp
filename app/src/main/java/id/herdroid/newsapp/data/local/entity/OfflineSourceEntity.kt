package id.herdroid.newsapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cached_sources")
data class OfflineSourceEntity(
    @PrimaryKey val id: String,
    val name: String,
    val description: String,
    val category: String
)
