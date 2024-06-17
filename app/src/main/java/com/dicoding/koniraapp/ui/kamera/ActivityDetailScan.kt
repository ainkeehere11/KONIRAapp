package com.dicoding.koniraapp.ui.kamera

import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dicoding.koniraapp.R
import com.dicoding.koniraapp.databinding.ActivityDetailScanBinding
import com.dicoding.koniraapp.helper.ImageClassifierHelper
import org.tensorflow.lite.task.vision.classifier.Classifications
import java.io.File

class ActivityDetailScan : AppCompatActivity() {

    private lateinit var binding: ActivityDetailScanBinding

    companion object {
        const val IMAGE_URI = "img_uri"
        const val TAG = "imagePicker"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailScanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val imageUriString = intent.getStringExtra(IMAGE_URI)
        if (imageUriString != null) {
            val imageUri = Uri.parse(imageUriString)
            displayImage(imageUri)

            val imageClassifierHelper = ImageClassifierHelper(
                contextValue = this,
                classifierListenerValue = object : ImageClassifierHelper.ClassifierListener {
                    override fun onError(errorMessage: String) {
                        Log.d(TAG, "Error: $errorMessage")
                    }

                    override fun onResults(results: List<Classifications>?, inferenceTime: Long) {
                        results?.let { showResults(it) }
                    }
                }
            )
            imageClassifierHelper.classifyStaticImage(imageUri)
        } else {
            Log.e(TAG, "No image URI provided")
            finish()
        }
    }

    private fun displayImage(uri: Uri) {
        Log.d(TAG, "Displaying image: $uri")
        binding.ivGambarDaun.setImageURI(uri)
    }

    private fun showResults(results: List<Classifications>) {
        val topResult = results.firstOrNull()
        topResult?.let { result ->
            val label = result.categories.firstOrNull()?.label ?: ""
            val score = result.categories.firstOrNull()?.score ?: 0f
            binding.tvpresentaseSakitdaunkopi.text = "$label ${score.formatToString()}"
        }
    }

    private fun Float.formatToString(): String {
        return String.format("%.2f%%", this * 100)
    }
}