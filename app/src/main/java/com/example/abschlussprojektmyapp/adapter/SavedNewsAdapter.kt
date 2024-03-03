package com.example.abschlussprojektmyapp.adapter

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.abschlussprojektmyapp.MainViewModel
import com.example.abschlussprojektmyapp.data.model.SavedNews
import com.example.abschlussprojektmyapp.databinding.ItemSavedNewsBinding
import java.text.SimpleDateFormat

/**

 * Der Adapter kümmert sich um das Erstellen der Listeneinträge
 */
class SavedNewsAdapter(
    private val dataset: List<SavedNews>,
    private val viewModel: MainViewModel
) : RecyclerView.Adapter<SavedNewsAdapter.SavedNewsViewHolder>() {

    inner class SavedNewsViewHolder(val binding: ItemSavedNewsBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedNewsViewHolder {
        val binding = ItemSavedNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SavedNewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SavedNewsViewHolder, position: Int) {
        val item = dataset[position]

        var dateFormatParse = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        var date = dateFormatParse.parse(item.publishedAt)

        var dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        var date2 = dateFormat.format(date)

        holder.binding.date.text = date2.toString()
        holder.binding.title.text = item.title
        holder.binding.author.text = item.author

        val hCard = holder.binding.cvSavedNews

        // ScaleY = Stretchen oben unten           AnimationsTyp, Start, Ende
        val animator = ObjectAnimator.ofFloat(hCard, View.SCALE_Y, 0f, 1f)
        animator.duration = 800
        animator.start()

        holder.binding.cvSavedNews.setOnClickListener {
            showDeleteAlertDialog(item,holder.itemView.context )

            // RotationY = object horizontal rotieren                  Start, Ende
            val rotator = ObjectAnimator.ofFloat(hCard, View.ROTATION_Y, 0f, 360f)
            rotator.duration = 600

            // Animationen abspielen.
            val set = AnimatorSet()
            set.playTogether(rotator)
            set.start()
        }
    }

    private fun showDeleteAlertDialog(savedNews: SavedNews, context: Context) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Delete")
        builder.setMessage("Do you really want to delete this savedNews?")
        builder.setIcon(android.R.drawable.ic_dialog_alert)

        builder.setPositiveButton("Yes") { dialogInterface, which ->
            viewModel.deleteSavedNews(savedNews)
            Toast.makeText(context, "The savedNews has been deleted", Toast.LENGTH_LONG)
                .show()
            dialogInterface.dismiss()
        }

        builder.setNeutralButton("Cancel") { dialogInterface, which ->
            Toast.makeText(context, "Deletion process aborted", Toast.LENGTH_LONG)
                .show()
            dialogInterface.dismiss()
        }

        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

}
