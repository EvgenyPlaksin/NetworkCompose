package com.lnight.networkcompose.model.data.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class Dog(
    val message: String,
    val status: String
)