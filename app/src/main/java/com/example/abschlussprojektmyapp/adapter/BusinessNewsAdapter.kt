package com.example.abschlussprojektmyapp.adapter

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.abschlussprojektmyapp.MainViewModel
import com.example.abschlussprojektmyapp.R
import com.example.abschlussprojektmyapp.data.model.SavedNews
import com.example.abschlussprojektmyapp.data.model.newsapi.Article
import com.example.abschlussprojektmyapp.databinding.ItemNewsBinding
import java.text.SimpleDateFormat

class BusinessNewsAdapter(
    private val dataset: List<Article>,
    private val viewModel: MainViewModel
) : RecyclerView.Adapter<BusinessNewsAdapter.ItemViewHolder>() {

    /**
     * der ViewHolder umfasst die View uns stellt einen Listeneintrag dar
     */
    inner class ItemViewHolder(val binding: ItemNewsBinding) :
        RecyclerView.ViewHolder(binding.root)

    /**
     * hier werden neue ViewHolder erstellt
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding =
            ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    /**
     * hier findet der Recyclingprozess statt
     * die vom ViewHolder bereitgestellten Parameter erhalten die Information des Listeneintrags
     */
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset[position]

        val hCard = holder.binding.itemNews

        // ScaleY = Stretchen oben unten           AnimationsTyp, Start, Ende
        val animator = ObjectAnimator.ofFloat(hCard, View.SCALE_Y, 0f, 1f)
        animator.duration = 800
        animator.start()

        holder.binding.itemNews.setOnClickListener {

            // RotationY = object horizontal rotieren                  Start, Ende
            val rotator = ObjectAnimator.ofFloat(hCard, View.ROTATION_Y, 0f, 360f)
            rotator.duration = 600

            // Animationen abspielen.
            val set = AnimatorSet()
            set.playTogether(rotator)
            set.start()
        }

        if (item.urlToImage != null && item.urlToImage.isNotEmpty()) {
            holder.binding.imageView4.visibility = View.VISIBLE
            holder.binding.imageView4.load(item.urlToImage)
        } else {
            holder.binding.imageView4.visibility = View.GONE
            val layoutParams = holder.binding.itemNews.layoutParams
            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
            holder.binding.itemNews.layoutParams = layoutParams
        }

        var dateFormatParse = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        var date = dateFormatParse.parse(item.publishedAt)

        var dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        var date2 = dateFormat.format(date)

        holder.binding.titleText.text = item.title
        holder.binding.publishedAt.text = date2.toString()
        holder.binding.author.text = item.author

        //Hier setzen wir per Click auf die CardView um ins
        //navigieren newsFragment2 navigieren zu können

        holder.binding.itemNews.setOnClickListener {
            viewModel.getBusinessNews()
            viewModel.setSelectedItem(item)
            holder.itemView.findNavController().navigate(R.id.newsDetailFragment)
        }

        if (holder is ItemViewHolder) {

            var dateFormatParse = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
            var date = dateFormatParse.parse(item.publishedAt)

            var dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            var date2 = dateFormat.format(date)

            holder.binding.titleText.text = item.title
            holder.binding.publishedAt.text = date2.toString()
            holder.binding.author.text = item.author

            holder.binding.saveArticle.setOnClickListener {
                showSavedAlertDialog(item,holder.itemView.context )
                //viewModel.saveSavedNews(item)

            }

        }
    }

    private fun showSavedAlertDialog(article: Article, context: Context) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Save")
        builder.setMessage("Do you really want to save this news?")
        builder.setIcon(android.R.drawable.ic_dialog_alert)

        builder.setPositiveButton("Yes") { dialogInterface, which ->
            viewModel.saveSavedNews(article)
            Toast.makeText(context, "The news has been saved", Toast.LENGTH_LONG)
                .show()
            dialogInterface.dismiss()
        }

        builder.setNeutralButton("Cancel") { dialogInterface, which ->
            Toast.makeText(context, "Memory aborted", Toast.LENGTH_LONG)
                .show()
            dialogInterface.dismiss()
        }

        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }


    /**
     * damit der LayoutManager weiß, wie lang die Liste ist
     */
    override fun getItemCount(): Int {
        return dataset.size
    }

}
