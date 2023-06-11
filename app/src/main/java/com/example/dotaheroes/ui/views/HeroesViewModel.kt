package com.example.dotaheroes.ui.views

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dotaheroes.data.Hero
import com.example.dotaheroes.network.OpenDotaApi
import com.example.dotaheroes.network.OpenDotaApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface HeroesUiState {
    object Loading : HeroesUiState

    data class Success(val heroes: List<Hero>) : HeroesUiState

    object Error: HeroesUiState
}

class HeroesViewModel: ViewModel() {

    private var _uiState: MutableStateFlow<HeroesUiState> = MutableStateFlow(HeroesUiState.Loading)
    val uiState: StateFlow<HeroesUiState> = _uiState.asStateFlow()

    init {
        getHeroes()
    }

    private fun getHeroes(){
        viewModelScope.launch {
            try {
                _uiState.value = HeroesUiState.Success(
                    OpenDotaApi.retrofitService.getHeroes()
                )
            }catch (e: IOException){
                    _uiState.value = HeroesUiState.Error
            }catch (e: HttpException){
                    _uiState.value = HeroesUiState.Error
            }
        }
    }

}