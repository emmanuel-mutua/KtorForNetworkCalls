package com.example.ktorfornetworkcalls.data.remote.dto

import kotlinx.serialization.Serializable


@Serializable
data class PostsResponse(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String,
)