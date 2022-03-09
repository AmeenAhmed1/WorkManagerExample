package com.ameen.workmanagerexample.worker

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.work.ListenableWorker
import androidx.work.testing.TestWorkerBuilder
import androidx.work.workDataOf
import com.ameen.workmanagerexample.chain.FirstChainWorker
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.Executor
import java.util.concurrent.Executors

@RunWith(AndroidJUnit4::class)
class SimpleWorkerTest {

    private lateinit var context: Context
    private lateinit var executor: Executor

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        executor = Executors.newSingleThreadExecutor()
    }

    @Test
    fun testSimpleWork_StartWorker_And_Return_Result_Success() {

        val worker = TestWorkerBuilder<SimpleWorker>(
            context = context,
            executor = executor
        ).build()

        val result = worker.doWork()
        assertEquals(ListenableWorker.Result.success(), result)
    }

    @Test
    fun testWorker_If_InputData_True_Return_Success() {
        val worker = TestWorkerBuilder<FirstChainWorker>(
            context = context,
            executor = executor,
            inputData = workDataOf("isSuccess" to true)
        ).build()

        val result = worker.doWork()
        MatcherAssert.assertThat(result, Matchers.`is`(ListenableWorker.Result.success()))
    }


    @Test
    fun testWorker_If_InputData_False_Return_Fail() {
        val worker = TestWorkerBuilder<FirstChainWorker>(
            context = context,
            executor = executor,
            inputData = workDataOf("isSuccess" to false)
        ).build()

        val result = worker.doWork()
        MatcherAssert.assertThat(result, Matchers.`is`(ListenableWorker.Result.failure()))
    }
}