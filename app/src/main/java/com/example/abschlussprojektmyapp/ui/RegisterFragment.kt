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
import com.example.abschlussprojektmyapp.databinding.FragmentRegisterBinding

class RegisterFragment: Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentRegisterBinding
    private var isLoggedIn = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Button um zurück zum SignInFragment zu navigieren
        binding.backBt.setOnClickListener {
            findNavController().navigate(R.id.signInFragment)
        }

        // Button um User zu registrieren
        // Erst werden email und passwort aus den Eingabefeldern geholt
        // Wenn beide nicht leer sind rufen wir die register Funktion im ViewModel auf
        binding.registerBt.setOnClickListener {
            val email = binding.emailRegister.text.toString()
            val password = binding.passwordRegister.text.toString()

            if (email != "" && password.length >= 8) {
                viewModel.register(email, password)
            } else {
                // Password must contain at least 8 characters
                if (password.length < 8) {
                    // Fehlermeldung anzeigen
                    binding.passwordRegister.error = "Password must contain at least 8 characters"
                }
                // Vorgang abbrechen
                return@setOnClickListener
            }
        }

        // User LiveData aus dem ViewModel wird beobachtet
        // Wenn User nicht gleich null (also der User eingeloggt ist) wird zum HomeFragment navigiert
        viewModel.user.observe(viewLifecycleOwner) { user ->
            if (user != null) {
                isLoggedIn = true
                findNavController().navigate(R.id.homeFragment)
            }
        }

    }

}