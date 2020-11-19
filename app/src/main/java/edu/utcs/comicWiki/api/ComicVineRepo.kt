package edu.utcs.comicWiki.api

class ComicVineRepo(private val comicVineAPI: ComicVineAPI) {
    suspend fun fetCharacterFromPath(characterPath: String?): Character {
        return comicVineAPI.fetchCharacterFromPath(characterPath).results
    }

    suspend fun fetchCharacterList(): List<Character> {
        return comicVineAPI.fetchCharacters().results
    }

    suspend fun fetchPowers(): List<Power> {
        return comicVineAPI.fetchPowers().results
    }

    suspend fun fetchTeamList(): List<Team> {
        return comicVineAPI.fetchTeams().results
    }

    suspend fun fetchTeam(team_apiPath: String?): Team {
        return comicVineAPI.fetchTeam(team_apiPath).results
    }

    suspend fun searchCharacters(keyWord: String): List<Character>? {
        println(keyWord)
        return comicVineAPI.searchCharacter(keyWord).results
    }

    suspend fun fetchTeamMembers(characterList: List<String>?): List<Character>? {
        return comicVineAPI.searchCharacters("spider-man").results
    }

}