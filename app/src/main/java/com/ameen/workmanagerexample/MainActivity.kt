package com.ameen.workmanagerexample

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.work.*
import com.ameen.workmanagerexample.chain.FirstChainWorker
import com.ameen.workmanagerexample.chain.SecondChainWorker
import com.ameen.workmanagerexample.chain.ThirdChainWorker
import com.ameen.workmanagerexample.databinding.ActivityMainBinding
import com.ameen.workmanagerexample.worker.SimpleWorker
import com.ameen.workmanagerexample.worker.SimpleWorkerRetry
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val workInstance = WorkManager.getInstance(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonStartWork.setOnClickListener {

            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .setRequiresCharging(true)
                .build()

            val data = workDataOf("WORK_DATA" to "YOUR MESSAGE.")

            val workRequest = OneTimeWorkRequestBuilder<SimpleWorker>()
                .setInputData(data)
                .setConstraints(constraints)
                .build()

            workInstance.enqueue(workRequest)
        }

        binding.buttonWorkStatus.setOnClickListener {
            Toast.makeText(
                this,
                "WorkStatus: ${WorkStatusSingleton.workStatus} && Message: ${WorkStatusSingleton.workStatusMessage}",
                Toast.LENGTH_SHORT
            ).show()
        }

        binding.buttonResetStatus.setOnClickListener {
            WorkStatusSingleton.workStatus = false
            WorkStatusSingleton.workStatusMessage = ""
            WorkStatusSingleton.workRetryCount = 0
        }


        binding.buttonRetryWork.setOnClickListener {
            val workRequest = OneTimeWorkRequestBuilder<SimpleWorkerRetry>()
                .setBackoffCriteria(
                    BackoffPolicy.LINEAR, // Default is Exponential
                    10, //Default is 10 Seconds
                    TimeUnit.SECONDS
                )
                .build()

            workInstance.enqueue(workRequest)
        }


        binding.buttonSingleChainWorkSuccess.setOnClickListener {
            val firstWorkRequest = OneTimeWorkRequestBuilder<FirstChainWorker>()
                .setInputData(workDataOf("isSuccess" to true))
                .build()

            val secondWorkRequest = OneTimeWorkRequestBuilder<SecondChainWorker>()
                .setInputData(workDataOf("isSuccess" to true))
                .build()

            val thirdWorkRequest = OneTimeWorkRequestBuilder<ThirdChainWorker>()
                .setInputData(workDataOf("isSuccess" to true))
                .build()

            workInstance.beginWith(firstWorkRequest)
                .then(secondWorkRequest)
                .then(thirdWorkRequest)
                .enqueue()
        }


        binding.buttonSingleChainWorkFail.setOnClickListener {
            val firstWorkRequest = OneTimeWorkRequestBuilder<FirstChainWorker>()
                .setInputData(workDataOf("isSuccess" to true))
                .build()

            val secondWorkRequest = OneTimeWorkRequestBuilder<SecondChainWorker>()
                .setInputData(workDataOf("isSuccess" to false))
                .build()

            val thirdWorkRequest = OneTimeWorkRequestBuilder<ThirdChainWorker>()
                .setInputData(workDataOf("isSuccess" to true))
                .build()

            workInstance.beginWith(firstWorkRequest)
                .then(secondWorkRequest)
                .then(thirdWorkRequest)
                .enqueue()
        }

        binding.buttonGroupChainWorkSuccess.setOnClickListener {
            val firstWorkRequest1 = OneTimeWorkRequestBuilder<FirstChainWorker>()
                .setInputData(workDataOf("isSuccess" to true))
                .build()

            val firstWorkRequest2 = OneTimeWorkRequestBuilder<FirstChainWorker>()
                .setInputData(workDataOf("isSuccess" to true))
                .build()

            val secondWorkRequest = OneTimeWorkRequestBuilder<SecondChainWorker>()
                .setInputData(workDataOf("isSuccess" to true))
                .build()

            val thirdWorkRequest = OneTimeWorkRequestBuilder<ThirdChainWorker>()
                .setInputData(workDataOf("isSuccess" to true))
                .build()

            workInstance.beginWith(listOf(firstWorkRequest1, firstWorkRequest2))
                .then(secondWorkRequest)
                .then(thirdWorkRequest)
                .enqueue()
        }

        binding.buttonGroupChainWorkFail.setOnClickListener {
            val firstWorkRequest1 = OneTimeWorkRequestBuilder<FirstChainWorker>()
                .setInputData(workDataOf("isSuccess" to true))
                .build()

            val firstWorkRequest2 = OneTimeWorkRequestBuilder<FirstChainWorker>()
                .setInputData(workDataOf("isSuccess" to false))
                .build()

            val secondWorkRequest = OneTimeWorkRequestBuilder<SecondChainWorker>()
                .setInputData(workDataOf("isSuccess" to true))
                .build()

            val thirdWorkRequest = OneTimeWorkRequestBuilder<ThirdChainWorker>()
                .setInputData(workDataOf("isSuccess" to true))
                .build()

            workInstance.beginWith(listOf(firstWorkRequest1, firstWorkRequest2))
                .then(secondWorkRequest)
                .then(thirdWorkRequest)
                .enqueue()
        }
    }
}