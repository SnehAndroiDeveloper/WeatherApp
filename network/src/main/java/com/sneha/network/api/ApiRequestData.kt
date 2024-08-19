package com.sneha.network.api

import com.sneha.network.RequestMethodType
import okhttp3.MultipartBody

/**
 * @author Created by Sneha on 15/08/2024
 */
/**
 * @param [networkResponseModel] network response model (Required)
 * @param [endpoint] endpoint URL of network request (Required)
 * @param [requestMethodType] request method type for network request (Required)
 * @param [queryParams] query parameters for network request (Optional)
 * @param [requestHeaders] headers for network request (Optional)
 * @param [requestBody] body of network request (Optional)
 * @param [isFormUrlEncoded] if need to make network request as FormUrlEncoded (Optional)
 * @param [bodyMapParams] if network request is FormUrlEncoded then need to send this param (Optional)
 */
data class ApiRequestData<ResponseModel>(
    var networkResponseModel: Class<ResponseModel>? = null,
    var endpoint: String,
    var requestMethodType: RequestMethodType,
    var queryParams: HashMap<String, Any>? = null,
    var requestHeaders: HashMap<String, String>? = null,
    var requestBody: Any? = null,
    var isFormUrlEncoded: Boolean = false,
    var bodyMapParams: HashMap<String, Any>? = null,
)