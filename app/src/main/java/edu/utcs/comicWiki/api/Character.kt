package edu.utcs.comicWiki.api

import com.google.gson.annotations.SerializedName

data class Character(
    @SerializedName("name")
    val name: String,
    @SerializedName("deck")
    val deck: String,
    @SerializedName("image")
    val image: Image,
    @SerializedName("api_detail_url")
    val apiDetailURL: String
) {
    data class Image(
        @SerializedName("screen_url")
        val imageURL: String
    )

    companion object {

    }
}

