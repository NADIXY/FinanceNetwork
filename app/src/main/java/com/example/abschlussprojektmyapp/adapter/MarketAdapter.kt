package com.example.abschlussprojektmyapp.adapter

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bumptech.glide.Glide
import com.example.abschlussprojektmyapp.MainViewModel
import com.example.abschlussprojektmyapp.R
import com.example.abschlussprojektmyapp.data.model.cryptoapi.CryptoCurrency
import com.example.abschlussprojektmyapp.databinding.CurrencyItemLayoutBinding

class MarketAdapter(
    private var list: List<CryptoCurrency>,
    private val viewModel: MainViewModel,
    var context: Context,
) : RecyclerView.Adapter<MarketAdapter.MarketViewHolder>() {

    inner class MarketViewHolder(val binding: CurrencyItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MarketAdapter.MarketViewHolder {
        val binding =
            CurrencyItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MarketViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MarketViewHolder, position: Int) {
        val item = list[position]

        holder.binding.currencyNameTextView.text = item.name
        holder.binding.currencySymbolTextView.text = item.symbol

        val hCard = holder.binding.currencyCardView

        // ScaleY = Stretchen oben unten           AnimationsTyp, Start, Ende
        val animator = ObjectAnimator.ofFloat(hCard, View.SCALE_Y, 0f, 1f)
        animator.duration = 800
        animator.start()

        holder.binding.currencyCardView.setOnClickListener {

            // RotationY = object horizontal rotieren                  Start, Ende
            val rotator = ObjectAnimator.ofFloat(hCard, View.ROTATION_Y, 0f, 360f)
            rotator.duration = 600

            // Animationen abspielen.
            val set = AnimatorSet()
            set.playTogether(rotator)
            set.start()
        }

        holder.binding.currencyCardView.setOnClickListener {
            holder.itemView.findNavController().navigate(R.id.top_Gain_LoseFragment)
        }

        Glide.with(holder.binding.root).load(
            "https://s2.coinmarketcap.com/static/img/coins/64x64/" + item.id + ".png"
        )
            .thumbnail(Glide.with(context).load(R.drawable.spinner))
            .into(holder.binding.currencyImageView)

        Glide.with(holder.binding.root).load(

            "https://s3.coinmarketcap.com/generated/sparklines/web/7d/usd/" + item.id + ".png"
        )
            .thumbnail(Glide.with(context).load(R.drawable.spinner))
            .into(holder.binding.currencyChartImageView)

        // Setzt den Text des currencyPriceTextView im binding-Objekt auf den formatierten Preis aus dem item-Objekt
        holder.binding.currencyPriceTextView.text =
            "${String.format("$%.02f", item.quotes[0].price)}"
        Log.d(
            "text",
            "${String.format("$%.02f", item.quotes[0].price)}"
        ) // Loggt den formatierten Preis aus dem item-Objekt

        // Überprüft, ob die prozentuale Veränderung in den letzten 24 Stunden im ersten Zitat des item-Objekts größer als 0 ist
        if (item.quotes!![0].percentChange24h > 0) {
            holder.binding.currencyChangeTextView.setTextColor(context.resources.getColor(R.color.green))
            // Setze den Text des CurrencyChangeTextView im Binding auf den Prozentwert der Änderung in den letzten 24 Stunden
            holder.binding.currencyChangeTextView.text =
                "+ ${String.format("%.02f", item.quotes[0].percentChange24h)} %"
        } else {
            holder.binding.currencyChangeTextView.setTextColor(context.resources.getColor(R.color.red))
            // Setze den Text des CurrencyChangeTextView im Binding auf den Prozentwert der Änderung in den letzten 24 Stunden
            holder.binding.currencyChangeTextView.text =
                "${String.format("%.02f", item.quotes[0].percentChange24h)} %"
        }

        if (holder is MarketAdapter.MarketViewHolder) {

            holder.binding.currencyNameTextView.text =
                item.name

            holder.binding.currencyImageView.load("https://s2.coinmarketcap.com/static/img/coins/64x64/" + item.id + ".png")

            holder.binding.currencyChartImageView.load("https://s3.coinmarketcap.com/generated/sparklines/web/7d/usd/" + item.id + ".png"){
                error(R.drawable.spinner)
            }

            holder.binding.currencyCardView.setOnClickListener {
                showSavedAlertDialog(item, holder.itemView.context)

            }
        }
    }

    private fun showSavedAlertDialog(cryptoCurrency: CryptoCurrency, context: Context?) {

        val builder = AlertDialog.Builder(context)
        builder.setTitle("Save")
        builder.setMessage("Do you really want to save this Crypto Currency?")
        builder.setIcon(android.R.drawable.ic_dialog_alert)

        builder.setPositiveButton("Yes") { dialogInterface, which ->
            viewModel.saveTopCurrency(cryptoCurrency)
            Toast.makeText(context, "The Crypto Currency has been saved", Toast.LENGTH_LONG)
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

    override fun getItemCount(): Int {
        return list.size
    }
}
