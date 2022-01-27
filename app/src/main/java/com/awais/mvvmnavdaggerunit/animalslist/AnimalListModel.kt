package com.awais.mvvmnavdaggerunit.animalslist

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class ApiKey(
    val message: String?,
    val key: String?
)

@Parcelize
data class AnimalListModel(
    @SerializedName("name")
    val animalName: String? = null,
    val taxonomy: Taxonomy? = null,
    val location: String? = null,
    val speed: Speed? = null,
    val diet: String? = null,
    @SerializedName("lifespan")
    val lifeSpan: String? = null,
    @SerializedName("image")
    val imageUrl: String? = null
) : Parcelable

@Parcelize
data class Taxonomy(
    val kingdom: String?,
    val order: String?,
    val family: String?,
) : Parcelable

@Parcelize
data class Speed(
    val metric: String?,
    val imperial: String?
) : Parcelable
