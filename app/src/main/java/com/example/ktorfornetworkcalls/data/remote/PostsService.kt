package com.example.ktorfornetworkcalls.data.remote

import com.example.ktorfornetworkcalls.data.remote.dto.PostsRequest
import com.example.ktorfornetworkcalls.data.remote.dto.PostsResponse
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

interface PostsService {
    suspend fun getPosts() : List<PostsResponse>
    suspend fun createPost(postsRequest: PostsRequest) : PostsResponse?
    companion object{
        private fun DefaultRequest.DefaultRequestBuilder.addAuthHeader(){
            header(HttpHeaders.Authorization, "token")
        }
        fun provideHttpClient(): PostsService{
            val client = HttpClient(Android){
                install(Logging){
                    level = LogLevel.ALL
                }
                /**
                 * Add content negotiation to determine:
                 * 1. The media type between the client and the server
                 * 2. The deserialization and serialization content in a specific format
                 */
                install(ContentNegotiation){
                    json(
                        Json {
                            encodeDefaults = true
                            ignoreUnknownKeys = true
                            prettyPrint = true
                            isLenient = true
                        }
                    )
                }
                install(DefaultRequest){
                    header(HttpHeaders.ContentType, ContentType.Application.Json)
                    addAuthHeader()
                }
            }
            return PostServiceImpl(client = client)
        }

    }

}