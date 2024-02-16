package com.example.abschlussprojektmyapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.abschlussprojektmyapp.MainViewModel
import com.example.abschlussprojektmyapp.R
import com.example.abschlussprojektmyapp.data.model.newsapi.Article
import com.example.abschlussprojektmyapp.databinding.TopNewsLayoutBinding
import java.text.SimpleDateFormat

class NewsAdapter(
    private val dataset: List<Article>,
    private val viewModel: MainViewModel
) : RecyclerView.Adapter<NewsAdapter.ItemViewHolder>() {

    /**
     * der ViewHolder umfasst die View uns stellt einen Listeneintrag dar
     */
    inner class ItemViewHolder(val binding: TopNewsLayoutBinding ) :
        RecyclerView.ViewHolder(binding.root)

    /**
     * hier werden neue ViewHolder erstellt
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding =
            TopNewsLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    /**
     * hier findet der Recyclingprozess statt
     * die vom ViewHolder bereitgestellten Parameter erhalten die Information des Listeneintrags
     */
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset[position]

        if (item.urlToImage != null && item.urlToImage.isNotEmpty()) {
            holder.binding.topNewsImageView.visibility = View.VISIBLE
            holder.binding.topNewsImageView.load(item.urlToImage)
        } else {
            holder.binding.topNewsImageView.visibility = View.GONE
            val layoutParams = holder.binding.topNewsCardView.layoutParams
            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
            holder.binding.topNewsCardView.layoutParams = layoutParams
        }

        var dateFormatParse = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        var date = dateFormatParse.parse(item.publishedAt)

        var dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        var date2 = dateFormat.format(date)

        holder.binding.topNewsTitelTextView.text = item.title
        holder.binding.topNewsDataView.text = date2.toString()


        //Hier setzen wir per Click auf die CardView um ins
        //newsFragment navigieren zu können
        holder.binding.topNewsCardView.setOnClickListener {
            holder.itemView.findNavController().navigate(R.id.newsFragment2)
        }

    }

    /**
     * damit der LayoutManager weiß, wie lang die Liste ist
     */
    override fun getItemCount(): Int {
        return dataset.size
    }
}