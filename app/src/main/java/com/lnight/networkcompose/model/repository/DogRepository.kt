package com.lnight.networkcompose.model.repository

import com.lnight.networkcompose.model.data.remote.DogApi
import com.lnight.networkcompose.model.data.remote.response.Dog
import com.lnight.networkcompose.model.util.Resource
import dagger.hilt.android.scopes.ActivityScoped
import timber.log.Timber
import javax.inject.Inject

@ActivityScoped
class DogRepository @Inject constructor(
    private val api: DogApi
){
    suspend fun getData(): Resource<Dog> {
        Timber.tag("TAG").e("getData called")
        val response = try {
            api.getDog()
        } catch (e: Exception) {
            return Resource.Error("An unknown error occured.")
        }
        return Resource.Success(response)
    }
}