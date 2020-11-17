package edu.utcs.comicWiki.api

import com.google.gson.annotations.SerializedName

data class Team(
    @SerializedName("name")
    val name: String,
    @SerializedName("deck")
    val deck: String,
    @SerializedName("image")
    val image: Image,
    @SerializedName("api_detail_url")
    val apiDetailURL: String,
    @SerializedName("characters")
    val characterList: List<Character>,
    @SerializedName("character_friends")
    val characterFriendsList: List<Character>,
    @SerializedName("character_enemies")
    val characterEnemiesList: List<Character>
) {
    data class Image(
        @SerializedName("screen_url")
        val imageURL: String
    )

    companion object {

    }
}

