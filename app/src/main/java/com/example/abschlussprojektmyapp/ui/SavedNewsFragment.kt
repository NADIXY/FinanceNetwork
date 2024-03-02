package com.example.abschlussprojektmyapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.example.abschlussprojektmyapp.MainViewModel
import com.example.abschlussprojektmyapp.R
import com.example.abschlussprojektmyapp.adapter.SavedNewsAdapter
import com.example.abschlussprojektmyapp.databinding.FragmentSavedNewsBinding

class SavedNewsFragment : Fragment() {
    private lateinit var binding: FragmentSavedNewsBinding
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSavedNewsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getBusinessNews()

        /**

         * Beobachtet die Notizen im ViewModel und aktualisiert die RecyclerView, wenn sich die Daten ändern.
         * @param viewLifecycleOwner Der LifecycleOwner der aktuellen Ansicht
         * @param binding Das Binding-Objekt für die RecyclerView
         * @param rvNotes Die RecyclerView, die die Notizen anzeigt
         * @param NoteAdapter Der Adapter für die RecyclerView
         * @param it Die Liste der aktualisierten Notizen
         * @param viewModel Das ViewModel, das die Notizen verwaltet
         */

        viewModel.savedNews.observe(viewLifecycleOwner) {
            binding.rvSavedNews.adapter = SavedNewsAdapter(it,viewModel)
        }

        binding.backStackSavedNews.setOnClickListener {
            findNavController().navigate(R.id.newsFragment2)
        }

        // Der SnapHelper sorgt dafür, dass die RecyclerView immer auf das aktuelle List Item springt
        val helper: SnapHelper = PagerSnapHelper()
        helper.attachToRecyclerView(binding.rvSavedNews)

    }

}