package com.globalmobility.technicaltest.model

import com.google.gson.annotations.SerializedName

data class LocationRM(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)
