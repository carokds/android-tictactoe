package de.sdomma.tictactoe.activities

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import de.sdomma.tictactoe.R
import de.sdomma.tictactoe.activities.fragments.GameFragment
import de.sdomma.tictactoe.activities.fragments.TitleFragment
import de.sdomma.tictactoe.databinding.ActivityFragmentsBinding

class FragmentsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFragmentsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFragmentsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_holder) as TitleFragment

//        if (currentFragment is TitleFragment) {
            currentFragment.onButtonClick = {
                val transaction = supportFragmentManager.beginTransaction()
                val gameFragment = GameFragment()
                val containerId = R.id.fragment_holder
                transaction.replace(containerId, gameFragment, "tag")
                transaction.commit()
            }
//        }
    }


}