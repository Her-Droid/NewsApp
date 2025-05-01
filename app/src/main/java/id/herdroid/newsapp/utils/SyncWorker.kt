package id.herdroid.newsapp.utils

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.AssistedInject
import id.herdroid.newsapp.domain.repository.NewsRepository

@HiltWorker
class SyncWorker @AssistedInject constructor(
    context: Context,
    workerParams: WorkerParameters,
    private val repository: NewsRepository
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            val articles = repository.getLatestArticlesFromRemote()
            repository.cacheArticles(articles)

            NotificationHelper.showNotification(
                context = applicationContext,
                title = "Sinkronisasi Berhasil",
                message = "Artikel terbaru telah disimpan untuk dibaca offline."
            )

            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}
