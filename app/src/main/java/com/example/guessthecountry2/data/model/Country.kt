package com.example.guessthecountry2.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Country(
    @Json(name = "name") val name: CountryName,
    @Json(name = "capital") val capital: List<String>?,
    @Json(name = "region") val region: String?,
    @Json(name = "subregion") val subregion: String?,
    @Json(name = "population") val population: Long?,
    @Json(name = "languages") val languages: Map<String, String>?,
    @Json(name = "currencies") val currencies: Map<String, Currency>?,
    @Json(name = "borders") val borders: List<String>?,
    @Json(name = "flags") val flags: Flag
)

@JsonClass(generateAdapter = true)
data class CountryName(
    @Json(name = "common") val common: String
)

@JsonClass(generateAdapter = true)
data class Currency(
    @Json(name = "name") val name: String?
)

@JsonClass(generateAdapter = true)
data class Flag(
    @Json(name = "png") val png: String
)