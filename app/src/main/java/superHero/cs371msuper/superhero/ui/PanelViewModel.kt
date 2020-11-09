package superHero.cs371msuper.superhero.ui

import android.net.Uri
import android.util.Log
import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import superHero.cs371msuper.superhero.api.SuperHeroAPI
import superHero.cs371msuper.superhero.api.CatRepository
import superHero.cs371msuper.superhero.glide.Glide
import kotlin.random.Random

class PanelViewModel : ViewModel() {
    //Initial guess for width and height
    var width = 350
    var height = 500
    private val random = Random(System.currentTimeMillis())
    private val catFactApi = SuperHeroAPI.create()
    private val catRepository = CatRepository(catFactApi)
    private val catFact = MutableLiveData<String>()

    //SSS
    fun netFetchCatFact() = viewModelScope.launch(
        context = viewModelScope.coroutineContext
                + Dispatchers.IO) {
        // Update LiveData from IO dispatcher, use postValue
        catFact.postValue(catRepository.fetchFact())
    }
    //RRR
    // // XXX Write this function
    // fun netFetchCatFact() {}
    //EEE

    fun observeCatFact(): LiveData<String> {
        return catFact
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
        val url = "https://cdn.jsdelivr.net/gh/akabab/superhero-api@0.3.0/api/images/md/577-scarlet-spider.jpg"
        Log.d(javaClass.simpleName, "Built: $url")
        return url
    }

    fun netFetchImage(imageView: ImageView) {
        Glide.fetch(randomHeroURL(), safePiscumURL(), imageView)
    }
}
