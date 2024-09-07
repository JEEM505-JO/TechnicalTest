package com.globalmobility.technicaltest.model

import com.globalmobility.technicaltest.model.Characters
import com.globalmobility.technicaltest.model.InfoRM
import com.google.gson.annotations.SerializedName

data class ObjectRM(
    @SerializedName("info")
    val info: InfoRM,
    @SerializedName("results")
    val result: List<Characters>
)
