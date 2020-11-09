package superHero.cs371msuper.superhero.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

data class Connections(val groupAffiliation: String, val relatives: String)


interface SuperHeroAPI {
    
    @GET("connections/1.json")
    suspend fun fetchConnections(): Connections

    companion object Factory {
        fun create(): SuperHeroAPI {
            val retrofit: Retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://cdn.jsdelivr.net/gh/akabab/superhero-api@0.3.0/api/")
                .build()
            return retrofit.create(SuperHeroAPI::class.java)
        }
    }
}