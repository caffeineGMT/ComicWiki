package edu.utcs.comicWiki.ui

import android.net.Uri
import android.util.Log
import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.utcs.comicWiki.api.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import edu.utcs.comicWiki.glide.Glide
import edu.utcs.comicWiki.model.Character
import edu.utcs.comicWiki.model.Power
import edu.utcs.comicWiki.model.Team
import kotlin.random.Random

class MainViewModel : ViewModel() {
    var width = 350
    var height = 500
    private val random = Random(System.currentTimeMillis())

    // TODO: helper function
    companion object {
        fun URL2Path(str: String): String {
            return str.substringAfter("api/").substringBeforeLast("/")
        }
    }

    // TODO: references
    private val comicVineAPI = ComicVineAPI.create()
    private val comicVineRepo = ComicVineRepo(comicVineAPI)
    private val characterList = MutableLiveData<List<Character>>()
    private val teamList = MutableLiveData<List<Team>>()
    private val team_apiPath = MutableLiveData<String>()
    private val team = MutableLiveData<Team>()
    private val teamMembers = MutableLiveData<List<Character>>()
    private val teamFriends = MutableLiveData<List<Character>>()
    private val teamEnemies = MutableLiveData<List<Character>>()
    private val powers = MutableLiveData<List<Power>>()

    private val searchedCharacterResults = MutableLiveData<List<Character>>()

    // TODO: characterList
    fun netFetchCharacterList() = viewModelScope.launch(
        context = viewModelScope.coroutineContext
                + Dispatchers.IO
    ) {
        characterList.postValue(comicVineRepo.fetchCharacterList())
    }

    fun observeCharacterList(): LiveData<List<Character>> {
        return characterList
    }

    fun getCharacterListAt(position: Int): Character? {
        val localList = characterList.value?.toList()
        localList?.let {
            if (position >= it.size)
                return null
            return it[position]
        }
        return null
    }

    fun getCharacterListCount(): Int {
        return characterList.value?.size ?: 0
    }

    // TODO: teamList
    fun netFetchTeamList() = viewModelScope.launch(
        context = viewModelScope.coroutineContext
                + Dispatchers.IO
    ) {
        teamList.postValue(comicVineRepo.fetchTeamList())
    }

    fun observeTeamList(): LiveData<List<Team>> {
        return teamList
    }

    fun getTeamListAt(position: Int): Team? {
        val localList = teamList.value?.toList()
        localList?.let {
            if (position >= it.size)
                return null
            return it[position]
        }
        return null
    }

    fun getTeamListCount(): Int {
        return teamList.value?.size ?: 0
    }

    // TODO: single team
    fun netFetchTeam() = viewModelScope.launch(
        context = viewModelScope.coroutineContext
                + Dispatchers.IO
    ) {
        team.postValue(comicVineRepo.fetchTeam(team_apiPath.value))
    }

    fun observeTeam(): LiveData<Team> {
        return team
    }

    fun set_team_apiPath(apiDetailURL: String) {
        team_apiPath.value = URL2Path(apiDetailURL)
    }

    fun getTeamMemberAt(position: Int): Character? {
        val local = teamMembers.value?.toList()
        local?.let {
            if (position >= it.size)
                return null
            return it[position]
        }
        return null
    }

    fun getTeamMembersCount(): Int {
        return teamMembers.value?.size ?: 0
    }


    //region: implements search
    fun netFetch_SearchCharacter(keyWord: String) {
        viewModelScope.launch(
            context = viewModelScope.coroutineContext
                    + Dispatchers.IO
        ) {
            searchedCharacterResults.postValue(comicVineRepo.searchCharacters(keyWord))
        }
    }

    fun observeSearchResult(): LiveData<List<Character>> {
        return searchedCharacterResults
    }

    fun getSearchResultsAt(position: Int): Character? {
        val localList = searchedCharacterResults.value?.toList()
        localList?.let {
            if (position >= it.size)
                return null
            return it[position]
        }
        return null
    }

    fun getSearchResultsCount(): Int {
        return searchedCharacterResults.value?.size ?: 0
    }

    // endregion


    // region: implements teamMembers
    fun netFetch_teamMembers() {
        viewModelScope.launch(
            context = viewModelScope.coroutineContext
                    + Dispatchers.IO
        ) {
            val limit = 10
            val characterList = team.value?.characters
            val result = mutableListOf<Character>()

            if (characterList.isNullOrEmpty())
                return@launch

            if (characterList.size > limit) {
                val paths = mutableListOf<String>()
                for (i in 0..limit)
                    paths.add(URL2Path(characterList[i].apiDetailURL))
                for (path in paths)
                    result.add(comicVineRepo.fetCharacterFromPath(path))
            } else {
                val paths = characterList.map {
                    URL2Path(it.apiDetailURL)
                }
                for (path in paths)
                    result.add(comicVineRepo.fetCharacterFromPath(path))
            }

            teamMembers.postValue(result)
        }
    }

    fun observeTeamMembers(): LiveData<List<Character>> {
        return teamMembers
    }

    fun netFetch_teamFriends() {
        viewModelScope.launch(
            context = viewModelScope.coroutineContext
                    + Dispatchers.IO
        ) {
            val names = team.value?.characterFriends?.map {
                it.name
            }
            teamFriends.postValue(comicVineRepo.fetchTeamMembers(names))
        }
    }

    fun observeTeamFriends(): LiveData<List<Character>> {
        return teamFriends
    }

    fun netFetch_teamEnemies() {
        viewModelScope.launch(
            context = viewModelScope.coroutineContext
                    + Dispatchers.IO
        ) {
            val names = team.value?.characterEnemies?.map {
                it.name
            }
            teamEnemies.postValue(comicVineRepo.fetchTeamMembers(names))
        }
    }

    fun observeTeamEnemies(): LiveData<List<Character>> {
        return teamEnemies
    }
    // endregion

    // region: other
    private fun safePiscumURL(): String {
        val builder = Uri.Builder()
        builder.scheme("https")
            .authority("picsum.photos")
            .appendPath(width.toString())
            .appendPath(height.toString())
        val url = builder.build().toString()
        Log.d(javaClass.simpleName, "Built: $url")
        return url
    }

    // Generates greater variety of images and we can control the randomness
    // But some numbers return error images, so have a backup.
    private fun randomHeroURL(): String {
        val builder = Uri.Builder()
        builder.scheme("https")
            .authority("picsum.photos")
            .appendPath(width.toString())
            .appendPath(height.toString())
            .appendQueryParameter(
                "image",
                random.nextInt(1000).toString()
            )
//        val url =
//            "https://cdn.jsdelivr.net/gh/akabab/superhero-api@0.3.0/api/images/md/577-scarlet-spider.jpg"
        val url = characterList.value?.get(0)?.image?.screenURL
        Log.d(javaClass.simpleName, "Built: $url")
        println(characterList.value?.get(1)?.name.toString())
        return url.toString()
    }

    fun netFetchImage(imageView: ImageView) {
        Glide.fetch(randomHeroURL(), safePiscumURL(), imageView)
    }

    // endregion

}
