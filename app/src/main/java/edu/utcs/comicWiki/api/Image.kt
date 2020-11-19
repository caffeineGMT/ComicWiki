package edu.utcs.comicWiki.api

import com.google.gson.annotations.SerializedName

data class Image(
    @SerializedName("icon_url")
    val iconURL: String,
    @SerializedName("medium_url")
    val mediumURL: String,
    @SerializedName("screen_url")
    val screenURL: String,
    @SerializedName("screen_large_url")
    val scrennLargeURL: String,
    @SerializedName("small_url")
    val smallURL: String,
    @SerializedName("super_url")
    val superURL: String,
    @SerializedName("thumb_url")
    val thumbURL: String,
    @SerializedName("tiny_url")
    val tinyURL: String,
    @SerializedName("original_url")
    val originalURL: String
)