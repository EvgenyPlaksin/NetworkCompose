package com.lnight.networkcompose.model.data.remote

import com.lnight.networkcompose.model.data.remote.response.Dog
import de.jensklingenberg.ktorfit.http.GET


interface DogApi {

    @GET("breeds/image/random")
   suspend fun getDog(): Dog

}