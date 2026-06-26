package com.example.guessthecountry2.data.remote

import com.example.guessthecountry2.data.model.Country
import retrofit2.http.GET
import retrofit2.http.Header

interface CountryApiService {

    @GET("v5/all")
    suspend fun getAllCountries(
        @Header("Authorization") token: String
    ): List<Country>
}