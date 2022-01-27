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
    val animalName: String?,
    val taxonomy: Taxonomy?,
    val location: String?,
    val speed: Speed?,
    val diet: String?,
    @SerializedName("lifespan")
    val lifeSpan: String?,
    @SerializedName("image")
    val imageUrl: String?
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
