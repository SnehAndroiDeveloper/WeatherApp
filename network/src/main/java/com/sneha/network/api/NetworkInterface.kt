package com.sneha.network.api

import com.google.gson.JsonElement
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.PartMap
import retrofit2.http.QueryMap
import retrofit2.http.Url

/**
 * @author Created by Sneha on 15/08/2024
 */
interface NetworkInterface {

    @GET
    suspend fun getInputStreamRequest(
        @Url endpoint: String,
        @QueryMap queryParams: HashMap<String, Any>? = null,
    ): Response<ResponseBody>

    @GET
    suspend fun getRequest(
        @Url endpoint: String
    ): Response<JsonElement>

    @GET
    suspend fun getRequest(
        @Url endpoint: String,
        @QueryMap queryParams: HashMap<String, Any>? = null,
    ): Response<JsonElement>

    @POST
    suspend fun postRequest(
        @Url endpoint: String,
        @Body requestBody: Any? = null,
        @QueryMap queryParams: HashMap<String, Any>,
    ): Response<JsonElement>

    @POST
    suspend fun postRequest(
        @Url endpoint: String,
        @Body requestBody: Any? = null,
    ): Response<JsonElement>

    @FormUrlEncoded
    @POST
    suspend fun postFormUrlEncodedRequest(
        @Url endpoint: String,
        @FieldMap fieldParams: HashMap<String, Any>? = null
    ): Response<JsonElement>

    @PUT
    suspend fun putRequest(
        @Url endpoint: String,
        @Body requestBody: Any? = null
    ): Response<JsonElement>

    @DELETE
    suspend fun deleteRequest(
        @Url endpoint: String,
        @QueryMap queryParams: HashMap<String, Any>? = null
    ): Response<JsonElement>

    @HTTP(method = "DELETE", hasBody = true)
    suspend fun deleteBodyRequest(
        @Url endpoint: String,
        @Body requestBody: Any?
    ): Response<JsonElement>

    @PATCH
    suspend fun patchRequest(
        @Url endpoint: String,
        @Body requestBody: Any? = null
    ): Response<JsonElement>
}