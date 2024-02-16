package com.example.abschlussprojektmyapp.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import coil.load
import com.example.abschlussprojektmyapp.MainViewModel
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
        binding = FragmentNewsDetailBinding.inflate(layoutInflater) //(inflater, container, false)

        binding.textView5.setOnClickListener {
            //val url = "https://www.deine-internetseite.com"
            val intent = Intent(Intent.ACTION_VIEW)
            //intent.data = Uri.parse(url)
            startActivity(intent)
        }


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


            binding.textView2.text = it.title
            binding.textView3.text = it.description
            binding.textView4.text = it.content
            binding.textView5.text = it.url
            binding.textView7.text = it.author
            binding.nameTV2.text = date2
            binding.ivsettingsperson.load(it.urlToImage)

            if(it.isLiked) {
                binding.thumpsLikedImage.visibility = View.VISIBLE
                binding.thumpsImage.visibility = View.GONE
            } else {
                binding.thumpsLikedImage.visibility = View.GONE
                binding.thumpsImage.visibility = View.VISIBLE
            }

        }

        //Die beiden OnClickListener um für beide Images zu gewährleisten, dass per Click der
        //Status geändert wird
        binding.thumpsImage.setOnClickListener {
            viewModel.changeLikedStatus(viewModel.selectedItem.value!!)
        }
        binding.thumpsLikedImage.setOnClickListener {
            viewModel.changeLikedStatus(viewModel.selectedItem.value!!)
        }


    }
}



