package com.dicoding.koniraapp.ui.kamera


import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.dicoding.koniraapp.databinding.ActivityDetailScanBinding
import com.dicoding.koniraapp.repo.PredictionRepository
import com.dicoding.koniraapp.retrofit.api.ApiPredictService
import com.dicoding.koniraapp.retrofit.response.PredictResponse
import com.dicoding.koniraapp.utils.uriToFile
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ActivityDetailScan : AppCompatActivity() {

    private lateinit var binding: ActivityDetailScanBinding
    private lateinit var viewModel: PredictionViewModel

    companion object {
        const val IMAGE_URI = "img_uri"
        const val TAG = "imagePicker"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailScanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Retrofit and PredictionRepository
        val apiService = createApiService()
        val predictionRepository = PredictionRepository(apiService)

        // Use ViewModelProvider with PredictionViewModelFactory
        val factory = PredictionViewModelFactory(predictionRepository)
        viewModel = ViewModelProvider(this, factory).get(PredictionViewModel::class.java)

        val imageUriString = intent.getStringExtra(IMAGE_URI)
        if (imageUriString != null) {
            val imageUri = Uri.parse(imageUriString)
            displayImage(imageUri)

            // Convert Uri to File and send it to ViewModel
            val file = uriToFile(imageUri, this)
            Log.d(
                "ActivityDetailScan","imagefile: ${file.path}"
            )

            val imageFile = file.asRequestBody("image/jpeg".toMediaType())
            val multipartBody = MultipartBody.Part.createFormData(
                "image",
                file.name,
                imageFile
            )

//            val imagePart = MultipartBody.Part.createFormData("image", file.name, RequestBody.create(
//                "image/*".toMediaTypeOrNull(), file))
            viewModel.getPrediction(multipartBody, ::handleSuccess, ::handleError)
        } else {
            Log.e(TAG, "No image URI provided")
            finish()
        }
    }


    private fun displayImage(uri: Uri) {
        Log.d(TAG, "Displaying image: $uri")
        binding.ivGambarDaun.setImageURI(uri)
    }



    private fun handleSuccess(response: PredictResponse) {
        val data = response.status?.data
        val accuracy = data?.accuracy ?: "No accuracy"
        val jsonMemberClass = data?.jsonMemberClass ?: "No class"
        val message = response.status?.message ?: "No message"
        binding.tvpresentaseSakitdaunkopi.text = "Accuracy: $accuracy\nClass: $jsonMemberClass\nMessage: $message"
    }


    private fun handleError(errorMessage: String) {
        Log.e(TAG, "Error: $errorMessage")
    }

    private fun createApiService(): ApiPredictService {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api-model-w3senv2swq-uc.a.run.app")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(ApiPredictService::class.java)
    }
}