package edu.utcs.comicwiki.api

import edu.utcs.comicwiki.model.Character
import edu.utcs.comicwiki.model.Power
import edu.utcs.comicwiki.model.Team
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ComicVineAPI {

    var apiKey: String
        get() = "b685154d28f6b3fc55b27a06dfaed34041028bd2"
        set(value) = TODO()

    @GET("{characterPath}/?api_key=b685154d28f6b3fc55b27a06dfaed34041028bd2&format=json&limit=20")
    suspend fun fetchCharacterFromPath(@Path("characterPath") characterPath: String?): CharacterResponse

    @GET("characters/?api_key=b685154d28f6b3fc55b27a06dfaed34041028bd2&format=json&limit=20")
    suspend fun fetchCharacters(): CharacterListResponse

    @GET("powers/?api_key=b685154d28f6b3fc55b27a06dfaed34041028bd2&format=json")
    suspend fun fetchPowers(): PowerListResponse

    @GET("teams/?api_key=b685154d28f6b3fc55b27a06dfaed34041028bd2&format=json&limit=20")
    suspend fun fetchTeams(): TeamListResponse

    @GET("{teamPath}/?api_key=b685154d28f6b3fc55b27a06dfaed34041028bd2&format=json")
    suspend fun fetchTeam(@Path("teamPath") team_apiPath: String?): TeamResponse

    @GET("search/?api_key=b685154d28f6b3fc55b27a06dfaed34041028bd2&format=json&limit=5&resources=character")
    suspend fun searchCharacter(@Query("query") keyWord: String): CharacterListResponse

    @GET("search/?api_key=b685154d28f6b3fc55b27a06dfaed34041028bd2&format=json&resources=character")
    suspend fun searchCharacters(@Query("query") keyWords: String): CharacterListResponse


    class CharacterResponse(val results: Character)
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