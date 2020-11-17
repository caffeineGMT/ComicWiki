package edu.utcs.comicWiki.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface ComicVineAPI {
    
    var apiKey: String
        get() = "b685154d28f6b3fc55b27a06dfaed34041028bd2"
        set(value) = TODO()

    @GET("characters/?api_key=b685154d28f6b3fc55b27a06dfaed34041028bd2&format=json&limit=20")
    suspend fun fetchCharacters(): CharacterListResponse

    @GET("powers/?api_key=b685154d28f6b3fc55b27a06dfaed34041028bd2&format=json")
    suspend fun fetchPowers(): PowerListResponse

    @GET("teams/?api_key=b685154d28f6b3fc55b27a06dfaed34041028bd2&format=json&limit=20")
    suspend fun fetchTeams(): TeamListResponse

    @GET("{team}/?api_key=b685154d28f6b3fc55b27a06dfaed34041028bd2&format=json")
    suspend fun fetchTeam(@Path("team") team_apiPath: String?): TeamResponse

    class CharacterListResponse(val results: List<Character>)
    class PowerListResponse(val results: List<Power>)
    class TeamListResponse(val results: List<Team>)
    class TeamResponse(val results: Team)

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