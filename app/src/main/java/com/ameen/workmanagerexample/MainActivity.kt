package com.ameen.workmanagerexample

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.work.*
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
    }
}