package com.ameen.workmanagerexample.chain

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class SecondChainWorker(context: Context, parameters: WorkerParameters) :
    Worker(context, parameters) {

    private val TAG = "ChainWorker"

    override fun doWork(): Result {
        val success = inputData.getBoolean("isSuccess", false)

        Log.i(TAG, "doWork: OnSecondChainWorker")

        return if (success) Result.success()
        else Result.failure()
    }
}