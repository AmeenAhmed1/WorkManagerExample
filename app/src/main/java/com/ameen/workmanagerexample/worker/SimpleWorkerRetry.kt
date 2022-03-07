package com.ameen.workmanagerexample.worker

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.ameen.workmanagerexample.WorkStatusSingleton

class SimpleWorkerRetry(context: Context, parameters: WorkerParameters) :
    Worker(context, parameters) {

    override fun doWork(): Result {
        return if (WorkStatusSingleton.workRetryCount < 3) {
            WorkStatusSingleton.workRetryCount++
            Result.retry()
        } else
            Result.success()
    }
}