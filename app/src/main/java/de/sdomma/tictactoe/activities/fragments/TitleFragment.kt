package de.sdomma.tictactoe.activities.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import de.sdomma.tictactoe.databinding.FragmentTitleBinding

class TitleFragment : Fragment() {

    var onButtonClick: () -> Unit = {}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentTitleBinding.inflate(layoutInflater)
        binding.btnStart.setOnClickListener {
            Toast.makeText(activity, "Game on!", Toast.LENGTH_SHORT).show()
            onButtonClick()
        }
        return binding.root
    }

}