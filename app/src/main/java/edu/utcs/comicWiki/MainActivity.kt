package edu.utcs.comicWiki

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import edu.utcs.comicWiki.ui.CharacterFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainerView, CharacterFragment.newInstance())
                .commit()
        }
    }

}
