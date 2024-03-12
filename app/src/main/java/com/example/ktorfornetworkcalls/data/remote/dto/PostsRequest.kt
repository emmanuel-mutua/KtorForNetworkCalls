package com.example.ktorfornetworkcalls.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class PostsRequest(
    val userId: Int,
    val title: String,
    val body: String,
)