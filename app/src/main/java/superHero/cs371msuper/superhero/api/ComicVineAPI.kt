package superHero.cs371msuper.superhero.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ComicVineAPI {

    @GET("characters/?api_key=b685154d28f6b3fc55b27a06dfaed34041028bd2&format=json")
    suspend fun fetchCharacters(): CharactersResponse

    class CharactersResponse(val results: List<Character>)

    companion object Factory {
        fun create(): ComicVineAPI {
            val retrofit: Retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://comicvine.gamespot.com/api/")
                .build()
            return retrofit.create(ComicVineAPI::class.java)
        }
    }
}