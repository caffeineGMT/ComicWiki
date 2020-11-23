package edu.utcs.comicwiki.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.utcs.comicwiki.api.ComicVineAPI
import edu.utcs.comicwiki.api.ComicVineRepo
import edu.utcs.comicwiki.model.Character
import edu.utcs.comicwiki.model.Team
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {
    private val comicVineAPI = ComicVineAPI.create()
    private val comicVineRepo = ComicVineRepo(comicVineAPI)
    private val searchedCharacterResults = MutableLiveData<List<Character>>()

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

}