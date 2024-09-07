package com.globalmobility.technicaltest.ui.home

import com.globalmobility.technicaltest.model.Characters

data class HomeState(
    val loading: Boolean = false,
    val success: Boolean = false,
    val error: Boolean = false,
    val smsError: String = "",
    val characters: List<Characters> = emptyList()
)
