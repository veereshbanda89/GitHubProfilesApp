package com.example.githubprofiles.network

import android.util.Log
import com.example.githubprofiles.constants.App
import com.example.githubprofiles.constants.Constants
import com.example.githubprofiles.model.ProfilesResponse
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import com.example.githubprofiles.constants.isConnectedToNetwork
import okhttp3.Interceptor


object GitHubProfilesRepository {

    private val api: Api

    init {

        val httpCacheDirectory = File(App.getContext().getCacheDir(), "responses")
        val cacheSize = 100 * 1024 * 1024 // 100 MiB
        val cache = Cache(httpCacheDirectory, cacheSize.toLong())

        val httpClient = OkHttpClient.Builder().cache(cache)
            .addNetworkInterceptor(getRewriteResponse())
            .addInterceptor(getRewriteResponseOffline())
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.GITHUB_BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(Api::class.java)
    }

    private fun getRewriteResponse(): Interceptor {
            val REWRITE_RESPONSE_INTERCEPTOR: Interceptor = object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
                val originalResponse = chain.proceed(chain.request())
                val cacheControl = originalResponse.header("Cache-Control")
                return if (cacheControl == null || cacheControl!!.contains("no-store") || cacheControl!!.contains(
                        "no-cache"
                    ) ||
                    cacheControl!!.contains("must-revalidate") || cacheControl!!.contains("max-age=0")
                ) {
                    originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, max-age=" + 5000)
                        .build()
                } else {
                    originalResponse
                }
            }
        }
        return REWRITE_RESPONSE_INTERCEPTOR
    }



    private fun getRewriteResponseOffline(): Interceptor{
            val REWRITE_RESPONSE_INTERCEPTOR_OFFLINE = object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
                var request = chain.request()
                if (!App.getContext().isConnectedToNetwork()) {
                    request = request.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, only-if-cached")
                        .build()
                }
                return chain.proceed(request)
            }
        }
        return REWRITE_RESPONSE_INTERCEPTOR_OFFLINE
    }

    fun getProfiles(
        onSuccess: (profiles: List<ProfilesResponse>) -> Unit,
        onError: () -> Unit
        ) {
        api.getGithubProfiles().enqueue(object : Callback<List<ProfilesResponse>> {

            override fun onResponse(
                call: Call<List<ProfilesResponse>>,
                response: Response<List<ProfilesResponse>>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()

                    if (responseBody != null) {
                        onSuccess.invoke(responseBody)
                    } else {
                        Log.d("Repository", "Failed to get response")
                        onError.invoke()
                    }
                }else{
                    Log.i("Repository", "Failed" + response.message())
                    onError.invoke()
                }
            }

            override fun onFailure(call: Call<List<ProfilesResponse>>, t: Throwable) {
                Log.e("Repository", "onFailure" + t.message)
                onError.invoke()
            }
        })
    }
}