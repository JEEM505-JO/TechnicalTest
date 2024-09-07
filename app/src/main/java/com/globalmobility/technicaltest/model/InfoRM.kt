package com.globalmobility.technicaltest.model

import com.google.gson.annotations.SerializedName

data class InfoRM(
    @SerializedName("count")
    val count: Int,
    @SerializedName("pages")
    val pages: Int,
    @SerializedName("next")
    val next: String,
    @SerializedName("prev")
    val prev: Int?
)