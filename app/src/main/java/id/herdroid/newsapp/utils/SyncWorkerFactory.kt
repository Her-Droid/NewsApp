package id.herdroid.newsapp.utils

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.Worker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.android.EntryPointAccessors
import id.herdroid.newsapp.domain.repository.NewsRepository
import javax.inject.Inject

@EntryPoint
@InstallIn(SingletonComponent::class)
interface SyncWorkerFactoryEntryPoint {
    fun newsRepository(): NewsRepository
}

class SyncWorkerFactory @Inject constructor(
    private val context: Context
) : WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        return when (workerClassName) {
            SyncWorker::class.java.name -> {
                val entryPoint = EntryPointAccessors.fromApplication(
                    appContext, SyncWorkerFactoryEntryPoint::class.java
                )
                val repository = entryPoint.newsRepository()
                SyncWorker(appContext, workerParameters, repository)
            }
            else -> null
        }
    }
}
