package net.apocalypt.modernmovies.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import net.apocalypt.modernmovies.data.TMDBRepository

class ViewModelFactory(val respository: TMDBRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainActivityViewModel(respository) as T
    }

}