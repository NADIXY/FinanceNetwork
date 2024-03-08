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
import coil.load
import com.example.abschlussprojektmyapp.MainViewModel
import com.example.abschlussprojektmyapp.data.model.SavedTopCurrency
import com.example.abschlussprojektmyapp.databinding.ItemSavedTopCurrencyBinding

class SavedTopCurrencyAdapter(
    private val dataset: List<SavedTopCurrency>,
    private val viewModel: MainViewModel
) : RecyclerView.Adapter<SavedTopCurrencyAdapter.SavedTopCurrencyViewHolder>() {

    inner class SavedTopCurrencyViewHolder(val binding: ItemSavedTopCurrencyBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedTopCurrencyViewHolder {
        val binding = ItemSavedTopCurrencyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SavedTopCurrencyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SavedTopCurrencyViewHolder, position: Int) {
        val item = dataset[position]

        holder.binding.savedTopCurrencyNameTextView.text =
            item.name

        holder.binding.savedTopCurrencyImageView.load("https://s2.coinmarketcap.com/static/img/coins/64x64/" + item.id + ".png")

        holder.binding.savedCurrencyChartImageView.load("https://s3.coinmarketcap.com/generated/sparklines/web/7d/usd/" + item.id + ".png")

        holder.binding.cvSavedTopCurrency.setOnClickListener {
            showDeleteAlertDialog(item,holder.itemView.context )

        }

        val hCard = holder.binding.cvSavedTopCurrency

        // ScaleY = Stretchen oben unten           AnimationsTyp, Start, Ende
        val animator = ObjectAnimator.ofFloat(hCard, View.SCALE_Y, 0f, 1f)
        animator.duration = 800
        animator.start()

        holder.binding.cvSavedTopCurrency.setOnClickListener {
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

    private fun showDeleteAlertDialog(savedTopCurrency: SavedTopCurrency, context: Context) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Delete")
        builder.setMessage("Do you really want to delete this savedTopCurrency?")
        builder.setIcon(android.R.drawable.ic_dialog_alert)

        builder.setPositiveButton("Yes") { dialogInterface, which ->
            viewModel.deleteTopCurrency(savedTopCurrency)
            Toast.makeText(context, "The savedTopCurrency has been deleted", Toast.LENGTH_LONG)
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