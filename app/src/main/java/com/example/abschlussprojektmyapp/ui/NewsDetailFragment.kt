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
import com.example.abschlussprojektmyapp.databinding.FragmentNewsDetailBinding
import java.text.SimpleDateFormat

class NewsDetailFragment : Fragment() {

    private lateinit var binding: FragmentNewsDetailBinding
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewsDetailBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getBusinessNews()

        //Die Business News Details werden beobachtet
        viewModel.selectedItem.observe(viewLifecycleOwner) {

            var dateFormatParse = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
            var date = dateFormatParse.parse(it.publishedAt)

            var dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            var date2 = dateFormat.format(date)

            binding.detailNewsAuthor.text = it.author
            binding.detailNewsTitle.text = it.title
            binding.detailNewsDate.text = date2
            binding.detailNewsDescription.text = it.description

        }

        binding.backStackNewsDetail.setOnClickListener {
            findNavController().navigate(R.id.newsFragment2)
        }

        
    }
}













