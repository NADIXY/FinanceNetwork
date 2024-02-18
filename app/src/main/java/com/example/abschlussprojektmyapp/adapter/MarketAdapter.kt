package com.example.abschlussprojektmyapp.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.abschlussprojektmyapp.R
import com.example.abschlussprojektmyapp.data.model.cryptoapi.CryptoCurrency
import com.example.abschlussprojektmyapp.databinding.CurrencyItemLayoutBinding

class MarketAdapter(
    var context: Context,
    var list: List<CryptoCurrency>) :
    RecyclerView.Adapter<MarketAdapter.MarketViewHolder>() {

    inner class MarketViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var binding = CurrencyItemLayoutBinding.bind(view)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MarketAdapter.MarketViewHolder {
        return MarketViewHolder(
            LayoutInflater.from(context).inflate(R.layout.currency_item_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MarketViewHolder, position: Int) {
        val item = list[position]

        holder.binding.currencyNameTextView.text = item.name
        holder.binding.currencySymbolTextView.text = item.symbol

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
        Log.d("text", "${String.format("$%.02f", item.quotes[0].price)}") // Loggt den formatierten Preis aus dem item-Objekt

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
    }

    override fun getItemCount(): Int {
        return list.size
    }
}
