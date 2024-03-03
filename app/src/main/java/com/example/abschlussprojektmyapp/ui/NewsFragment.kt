package com.example.abschlussprojektmyapp.ui

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.abschlussprojektmyapp.MainViewModel
import com.example.abschlussprojektmyapp.R
import com.example.abschlussprojektmyapp.adapter.BusinessNewsAdapter
import com.example.abschlussprojektmyapp.databinding.FragmentNewsBinding

class NewsFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentNewsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getBusinessNews()

        binding.backStackNews.setOnClickListener {
            findNavController().navigate(R.id.homeFragment)
        }

        binding.menu.setOnClickListener {
            showPopupMenu(it)
        }

        binding.rvBusinessNews.setOnClickListener {
            viewModel.getBusinessNews()
            findNavController().navigate(R.id.newsDetailFragment)
        }

        viewModel.newsList.observe(viewLifecycleOwner) {
            Log.d("Test_Api", "$it")
            binding.rvBusinessNews.adapter = BusinessNewsAdapter(it.articles, viewModel)
        }

    }

    private fun showPopupMenu(view: View) {
        val popup = PopupMenu(requireContext(), view)
        val inflater: MenuInflater = popup.menuInflater
        inflater.inflate(
            R.menu.news_menu,
            popup.menu
        )
        popup.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.news_option -> {
                    findNavController().navigate(R.id.savedNewsFragment)
                    true

                }
                R.id.close -> {
                    val builder = AlertDialog.Builder(requireContext())
                    builder.setTitle("Close")
                    builder.setMessage("Do you really want to close?")
                    builder.setPositiveButton("Yes") { dialog, which ->
                        activity?.finish()
                        Toast.makeText(requireContext(), "closed", Toast.LENGTH_SHORT).show()
                    }
                    builder.setNegativeButton("Cancel") { dialog, which -> }
                    val dialog: AlertDialog = builder.create()
                    dialog.show()

                    true
                }
                else -> true
            }
        }
        popup.show()
    }

}





