package superHero.cs371msuper.superhero.api

import com.google.gson.annotations.SerializedName

data class Character(
    @SerializedName("name")
    val name: String,
    @SerializedName("deck")
    val deck: String,
    @SerializedName("image")
    val image: Image
) {
    companion object {

    }
}

data class Image(
    @SerializedName("thumb_url")
    val imageURL: String
)