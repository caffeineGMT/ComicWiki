package superHero.cs371msuper.superhero.api

class ComicVineRepo(private val comicVineAPI: ComicVineAPI) {
    suspend fun fetchCharacters(): List<Character> {
        return comicVineAPI.fetchCharacters().results
    }
}