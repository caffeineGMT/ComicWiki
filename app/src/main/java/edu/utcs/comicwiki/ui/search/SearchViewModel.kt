package edu.utcs.comicwiki.ui.search

import androidx.lifecycle.*
import edu.utcs.comicwiki.api.ComicVineAPI
import edu.utcs.comicwiki.api.ComicVineRepo
import edu.utcs.comicwiki.model.Character
import edu.utcs.comicwiki.model.GenericItem
import edu.utcs.comicwiki.model.Location
import edu.utcs.comicwiki.model.StoryArc
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {
    private val comicVineAPI = ComicVineAPI.create()
    private val comicVineRepo = ComicVineRepo(comicVineAPI)

    private val searchedCharacterResults = MutableLiveData<List<Character>>()
    private val locations = MutableLiveData<List<GenericItem>>()
    private val storyArcs = MutableLiveData<List<GenericItem>>()
    private val characters = MutableLiveData<List<GenericItem>>()

    private var liveStoryArc = MediatorLiveData<List<StoryArc>>().apply {
//        value =
    }

    init {
        liveStoryArc.apply {
            addSource(searchedCharacterResults) {

            }
        }
    }

    // region: search result
    fun netFetch_SearchCharacter(keyWord: String) {
        viewModelScope.launch(
            context = viewModelScope.coroutineContext
                    + Dispatchers.IO
        ) {
            searchedCharacterResults.postValue(comicVineRepo.searchCharacters(keyWord))
        }
    }

    fun observeSearchCharacterResult(): LiveData<List<Character>> {
        return searchedCharacterResults
    }

    fun getSearchCharacterResultsAt(position: Int): Character? {
        val localList = searchedCharacterResults.value?.toList()
        localList?.let {
            if (position >= it.size)
                return null
            return it[position]
        }
        return null
    }

    fun getSearchCharacterResultsCount(): Int {
        return searchedCharacterResults.value?.size ?: 0
    }

    // endregion


    // region: generic search
    fun netFetch_Search(query: String, resources: String) {
        viewModelScope.launch(
            context = viewModelScope.coroutineContext
                    + Dispatchers.IO
        ) {
            when (resources) {
                "character" -> characters.postValue(comicVineRepo.search(query, "character"))
                "location" -> locations.postValue(comicVineRepo.search(query, "team"))
                "story_arc" -> storyArcs.postValue(comicVineRepo.search(query, "issue"))
            }
        }
    }

    fun observeSearchResults(resources: String): LiveData<List<GenericItem>> {
        return when (resources) {
            "character" -> characters
            "location" -> locations
            "story_arc" -> storyArcs
            else -> MutableLiveData<List<GenericItem>>()
        }
    }


    fun getSearchResultsAt(position: Int, resources: String): GenericItem? {
        when (resources) {
            "character" -> characters.value?.toList()?.let {
                if (position >= it.size)
                    return null
                return it[position]
            }
            "location" -> locations.value?.toList()?.let {
                if (position >= it.size)
                    return null
                return it[position]
            }
            "story_arc" -> storyArcs.value?.toList()?.let {
                if (position >= it.size)
                    return null
                return it[position]
            }
        }

        return null
    }

    fun getSearchResultsCount(resources: String): Int {
        return when (resources) {
            "character" -> characters.value?.size ?: 0
            "location" -> locations.value?.size ?: 0
            "story_arc" -> storyArcs.value?.size ?: 0
            else -> 0
        }
    }
    // endregion


}