package com.ameen.workmanagerexample.worker

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.ameen.workmanagerexample.WorkStatusSingleton

class SimpleWorker(context: Context, parameters: WorkerParameters) : Worker(context, parameters) {

    override fun doWork(): Result {
        Thread.sleep(500)
        WorkStatusSingleton.workStatus = true
        return Result.success()
    }
}