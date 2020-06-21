package com.example.githubprofiles.network

import com.example.githubprofiles.model.ProfilesResponse
import retrofit2.Call
import retrofit2.http.GET

interface Api {

    @GET("repositories")
    fun getGithubProfiles(): Call<List<ProfilesResponse>>
}