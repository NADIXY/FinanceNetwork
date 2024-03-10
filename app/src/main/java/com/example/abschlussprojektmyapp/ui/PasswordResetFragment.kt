package com.example.abschlussprojektmyapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.abschlussprojektmyapp.MainViewModel
import com.example.abschlussprojektmyapp.R
import com.example.abschlussprojektmyapp.databinding.FragmentPasswordResetBinding

class PasswordResetFragment: Fragment() {

    private lateinit var binding: FragmentPasswordResetBinding
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPasswordResetBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Button um Passwort-Vergessen Mail zu senden
        // Email wird aus den Input-Feldern geholt
        // Wenn Email kein leerer String ist wird die sendPasswordResetFunktion im ViewModel aufgerufen
        binding.btSendEmailPasswordReset.setOnClickListener {
            val email = binding.tietEmailPasswordReset.text.toString()

            if (email != "") {
                viewModel.sendPasswordReset(email)
            }

        }

        // Button um zurück zum SignInFragment zu navigieren
        binding.btBackToLogin.setOnClickListener {
            findNavController().navigate(R.id.signInFragment)
        }

    }
}