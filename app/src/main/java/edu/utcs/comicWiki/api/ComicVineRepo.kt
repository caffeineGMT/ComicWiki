package edu.utcs.comicWiki.api

class ComicVineRepo(private val comicVineAPI: ComicVineAPI) {
    suspend fun fetchCharacters(): List<Character> {
        return comicVineAPI.fetchCharacters().results
    }
}