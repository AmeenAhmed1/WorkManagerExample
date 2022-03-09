package com.ameen.workmanagerexample.worker

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.work.ListenableWorker
import androidx.work.testing.TestWorkerBuilder
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.Executor
import java.util.concurrent.Executors

@RunWith(AndroidJUnit4::class)
class SimpleWorkerRetryTest {

    private lateinit var context: Context
    private lateinit var executor: Executor

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        executor = Executors.newSingleThreadExecutor()
    }

    @Test
    fun testSimpleWork_StartWorker_And_Return_Result_Retry() {

        val worker = TestWorkerBuilder<SimpleWorkerRetry>(
            context = context,
            executor = executor
        ).build()

        val result = worker.doWork()
        Assert.assertEquals(ListenableWorker.Result.retry(), result)
    }
}