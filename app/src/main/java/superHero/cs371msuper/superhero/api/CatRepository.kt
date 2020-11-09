package superHero.cs371msuper.superhero.api

class CatRepository(private val superHeroAPI: SuperHeroAPI) {

    // These two versions are functionally identical
    //suspend fun fetchFact() = catFactApi.fetchFact().fact
    suspend fun fetchFact(): String {
        //SSS
        return superHeroAPI.fetchConnections().groupAffiliation
        //EEE // XXX Write me
    }
}