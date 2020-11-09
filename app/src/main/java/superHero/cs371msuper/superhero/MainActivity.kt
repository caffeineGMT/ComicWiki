package superHero.cs371msuper.superhero

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import superHero.cs371msuper.superhero.ui.PanelFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.frameUL, PanelFragment.newInstance())
                .replace(R.id.frameUR, PanelFragment.newInstance())
                .replace(R.id.frameLL, PanelFragment.newInstance())
                .replace(R.id.frameLR, PanelFragment.newInstance())
                .commitNow()
        }
    }

}
