package com.globalmobility.technicaltest.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.globalmobility.technicaltest.repository.RepositoryNetwork
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeVM @Inject constructor(private val repository: RepositoryNetwork) : ViewModel() {

    private val _state: MutableStateFlow<HomeState> = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state

    init {
        getCharacters()
    }


    /**
     *
     * Consulta el repositorio para obtener los datos de los personajes desde el api
     * retrasa la consulta por dos segundos para mostrar el loading
     *
     * Si la solicitud no es correcta muestra un mensaje de error
     *
     * @author Joaquin Espinoza
     *
     * @throws exception
     */
    private fun getCharacters() {
        viewModelScope.launch {
//            awaiting two minutes for showing loading progressbar
            _state.value = _state.value.copy(
                loading = true
            )
            delay(2000)
            try {
                val result = repository.getCharacters()
                if (result.isSuccessful) {
                    val data = result.body()
//                    validate null body
                    if (data != null) {
                        _state.value = _state.value.copy(
                            success = true,
                            loading = false,
                            characters = data.result
                        )
                    }
                } else {
                    _state.value = _state.value.copy(
                        error = true,
                        loading = false,
                        smsError = "Error inesperado, intente mas tarde"
                    )
                }

            } catch (e: Exception) {
                //catching to exception
                _state.value = _state.value.copy(
                    error = true,
                    loading = false,
                    smsError = e.message.toString()
                )

            }


        }
    }

}