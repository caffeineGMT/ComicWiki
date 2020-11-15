package edu.utcs.comicWiki.ui

import android.net.Uri
import android.util.Log
import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import edu.utcs.comicWiki.api.Character
import edu.utcs.comicWiki.api.ComicVineAPI
import edu.utcs.comicWiki.api.ComicVineRepo
import edu.utcs.comicWiki.glide.Glide
import kotlin.random.Random

class MainViewModel : ViewModel() {
    var width = 350
    var height = 500
    private val random = Random(System.currentTimeMillis())

    // TODO: references
    private val comicVineAPI = ComicVineAPI.create()
    private val comicVineRepo = ComicVineRepo(comicVineAPI)
    private val characters = MutableLiveData<List<Character>>()

    fun netFetchCharacters() = viewModelScope.launch(
        context = viewModelScope.coroutineContext
                + Dispatchers.IO
    ) {
        characters.postValue(comicVineRepo.fetchCharacters())
    }

    fun observeCharacterDeck(): LiveData<List<Character>> {
        return characters
    }

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
        val url = characters.value?.get(0)?.image?.imageURL
        Log.d(javaClass.simpleName, "Built: $url")
        println(characters.value?.get(1)?.name.toString())
        return url.toString()
    }

    fun netFetchImage(imageView: ImageView) {
        Glide.fetch(randomHeroURL(), safePiscumURL(), imageView)
    }

    fun getCharacterAt(position: Int): Character? {
        val localList = characters.value?.toList()
        localList?.let {
            if (position >= it.size)
                return null
            return it[position]
        }
        return null
    }

    fun getCharactersCount(): Int {
        return characters.value?.size ?: 0
    }
}
