package com.ameen.workmanagerexample

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.ameen.workmanagerexample.databinding.ActivityMainBinding
import com.ameen.workmanagerexample.worker.SimpleWorker

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    val workInstance = WorkManager.getInstance(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonStartWork.setOnClickListener {
            val workRequest = OneTimeWorkRequestBuilder<SimpleWorker>().build()
            workInstance.enqueue(workRequest)
        }

        binding.buttonWorkStatus.setOnClickListener {
            Toast.makeText(
                this,
                "WorkStatus: ${WorkStatusSingleton.workStatus}",
                Toast.LENGTH_SHORT
            ).show()
        }

        binding.buttonResetStatus.setOnClickListener {
            WorkStatusSingleton.workStatus = false
        }
    }
}