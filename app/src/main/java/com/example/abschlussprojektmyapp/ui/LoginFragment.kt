package com.example.abschlussprojektmyapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.abschlussprojektmyapp.MainViewmodel
import com.example.abschlussprojektmyapp.R
import com.example.abschlussprojektmyapp.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    private val viewmodel: MainViewmodel by activityViewModels()
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginBTN.setOnClickListener {
            if (binding.emailET.text.isEmpty() || binding.passwordET.text.isEmpty()) {
                Toast.makeText(requireContext(), "Failed input must not be empty!", Toast.LENGTH_SHORT).show()
            } else {
                viewmodel.login(binding.emailET.text.toString(), binding.passwordET.text.toString())
            }
        }

        binding.registerBTN.setOnClickListener {
            if (binding.emailET.text.isEmpty() || binding.passwordET.text.isEmpty()) {
                Toast.makeText(requireContext(), "Failed input must not be empty!", Toast.LENGTH_SHORT).show()
            } else {
                viewmodel.register(binding.emailET.text.toString(), binding.passwordET.text.toString())
            }
        }

        viewmodel.user.observe(viewLifecycleOwner) { user ->
            if (user != null) {
                findNavController().navigate(R.id.profileFragment)
            }
        }
    }

}




