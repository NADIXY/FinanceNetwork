package com.example.abschlussprojektmyapp.ui

import android.app.AlertDialog
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import coil.load
import com.example.abschlussprojektmyapp.MainViewModel
import com.example.abschlussprojektmyapp.R
import com.example.abschlussprojektmyapp.data.model.Profile
import com.example.abschlussprojektmyapp.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentProfileBinding

    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        if (uri != null) {
            //Uri bezieht sich auf das Bild
            viewModel.uploadProfilePicture(uri)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Funktion um Bild vom Gerät auszuwählen
        // Startet den Ressource-Picker und zeigt uns alle Bilder auf dem Gerät an
        binding.ivProfilePic.setOnClickListener {
            getContent.launch("image/*")
        }

        // Snapshot Listener: Hört auf Änderungen in dem Firestore Document, das beobachtet wird
        // Hier: Referenz auf Profil wird beobachtet
        viewModel.profileRef.addSnapshotListener { value, error ->
            if (error == null && value != null) {
                // Umwandeln des Snapshots in eine Klassen-Instanz von der Klasse Profil und setzen der Felder
                val myProfile = value.toObject(Profile::class.java)
                binding.tietFirstName.setText(myProfile!!.firstName)
                binding.tietLastName.setText(myProfile.lastName)
                binding.ivProfilePic.load(myProfile.profilePicture)
            }
        }


        // Beim Klick auf Logout, wird die Logout Funktion im ViewModel aufgerufen
        binding.btLogout.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Log out")
            builder.setMessage("Do you really want to log out?")
            builder.setPositiveButton("Yes") { dialog, which ->
                viewModel.logout()
                Toast.makeText(requireContext(), "You are logged out", Toast.LENGTH_SHORT).show()
            }
            builder.setNegativeButton("Cancel") { dialog, which -> }
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }


        binding.continueBTN.setOnClickListener {
            findNavController().navigate(R.id.homeFragment)

        }

        // User LiveData aus dem ViewModel wird beobachtet
        // Wenn User gleich null (also der User nicht mehr eingeloggt ist) wird zum LoginFragment navigiert
        viewModel.user.observe(viewLifecycleOwner) {
            if (it == null) {
                findNavController().navigate(R.id.loginFragment)
            }
        }

        // Neue Profil-Daten in Firestore speichern
        binding.btSave.setOnClickListener {
            val firstName = binding.tietFirstName.text.toString()
            val lastName = binding.tietLastName.text.toString()

            if (firstName != "" && lastName != "") {
                val newProfile = Profile(firstName, lastName)
                viewModel.updateProfile(newProfile)
            }

        }


    }

}
