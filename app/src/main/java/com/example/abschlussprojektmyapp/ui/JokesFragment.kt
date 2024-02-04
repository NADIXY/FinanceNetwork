package com.example.abschlussprojektmyapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.abschlussprojektmyapp.MainViewmodel
import com.example.abschlussprojektmyapp.R
import com.example.abschlussprojektmyapp.adapter.JokeAdapter
import com.example.abschlussprojektmyapp.databinding.FragmentJokesBinding
import com.squareup.moshi.JsonAdapter

class JokesFragment : Fragment() {

    // Hier wird das ViewModel, in dem die Logik stattfindet, geholt
    private val viewModel: MainViewmodel by viewModels()

    // Das binding für das QuizFragment wird deklariert
    private lateinit var binding: FragmentJokesBinding

    /**
     * Lifecycle Funktion onCreateView
     * Hier wird das binding initialisiert und das Layout gebaut
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentJokesBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     * Lifecycle Funktion onViewCreated
     * Hier werden die Elemente eingerichtet und z.B. onClickListener gesetzt
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = JokeAdapter()
        binding.rvJokes.adapter = adapter
        viewModel.data.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        binding.btnRefresh.setOnClickListener {
            viewModel.loadData()

        }
    }
}
