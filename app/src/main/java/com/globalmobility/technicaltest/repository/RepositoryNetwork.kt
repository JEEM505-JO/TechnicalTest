package com.globalmobility.technicaltest.repository

import com.globalmobility.technicaltest.network.EndPoints
import javax.inject.Inject

class RepositoryNetwork @Inject constructor(private val endPoints: EndPoints){

    suspend fun getCharacters() = endPoints.getCharacters()
}