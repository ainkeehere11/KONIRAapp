package com.dicoding.koniraapp.retrofit.response

import com.google.gson.annotations.SerializedName

data class PredictResponse(

	@field:SerializedName("status")
	val status: Status? = null
)

data class Data(

	@field:SerializedName("accuracy")
	val accuracy: String? = null,

	@field:SerializedName("class")
	val jsonMemberClass: String? = null
)

data class Status(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("message")
	val message: String? = null
)
