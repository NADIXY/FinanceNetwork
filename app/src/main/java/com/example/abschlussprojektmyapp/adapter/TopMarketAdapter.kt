package com.example.abschlussprojektmyapp.adapter

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
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
import com.example.abschlussprojektmyapp.databinding.TopCurrencyLayoutBinding

/**

 *Diese Klasse stellt einen Adapter für die Top-Marktliste dar.
 *@param context Der Kontext der Anwendung
 *@param list Die Liste der Kryptowährungen, die angezeigt werden sollen
 */
class TopMarketAdapter(
    private val context: Context,
    private val list: List<CryptoCurrency>,
) : RecyclerView.Adapter<TopMarketAdapter.TopMarketViewHolder>() {

    /**

     *Eine innere Klasse, die von RecyclerView.ViewHolder erbt und eine Referenz auf die Ansicht hält.
     *@param view Die Ansicht, die in der inneren Klasse gehalten wird.
     */
    inner class TopMarketViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var binding =
            TopCurrencyLayoutBinding.bind(view) //Eine Variable, die das Layout der obersten Währung bindet.
    }

    /**

     *Erstellt und gibt einen neuen TopMarketViewHolder zurück.
     *@param parent Die übergeordnete Ansichtsgruppe, in der der neue View angezeigt werden soll.
     *@param viewType Der Typ der Ansicht.
     *@return Ein neuer TopMarketViewHolder, der mit dem aufgeblasenen Layout initialisiert ist.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopMarketViewHolder {
        return TopMarketViewHolder(
            LayoutInflater.from(context).inflate(R.layout.top_currency_layout, parent, false)
        )
    }

    /**

     *Überschreibt die Methode onBindViewHolder aus der RecyclerView.Adapter-Klasse.
     *Bindet die Daten an die angegebene ViewHolder-Position.
     *@param holder Der ViewHolder, an den die Daten gebunden werden sollen
     *@param position Die Position der Daten in der Liste
     */
    override fun onBindViewHolder(holder: TopMarketViewHolder, position: Int) {
        val item = list[position] //Holt das Element aus der Liste an der angegebenen Position.

        val hCard = holder.binding.topCurrencyCardView

        // ScaleY = Stretchen oben unten           AnimationsTyp, Start, Ende
        val animator = ObjectAnimator.ofFloat(hCard, View.SCALE_Y, 0f, 1f)
        animator.duration = 800
        animator.start()

        holder.binding.topCurrencyCardView.setOnClickListener {

            // RotationY = object horizontal rotieren                  Start, Ende
            val rotator = ObjectAnimator.ofFloat(hCard, View.ROTATION_Y, 0f, 360f)
            rotator.duration = 600

            // Animationen abspielen.
            val set = AnimatorSet()
            set.playTogether(rotator)
            set.start()
        }


        holder.binding.topCurrencyNameTextView.text =
            item.name //Setzt den Text des topCurrencyNameTextView im Binding auf den Namen des Elements.

        //Hier setzen wir per Click auf die CardView um ins
        //newsFragment navigieren zu können
        holder.binding.topCurrencyCardView.setOnClickListener {
            holder.itemView.findNavController().navigate(R.id.top_Gain_LoseFragment)
        }

        /**

         *Lädt ein Bild von der angegebenen URL und zeigt es in der ImageView an.
         *@param holder Die ViewHolder, der die ImageView enthält
         *@param item Das Element, das die ID des Bildes enthält
         */
        Glide.with(holder.binding.root)
            .load(
                "https://s2.coinmarketcap.com/static/img/coins/64x64/" + item.id + ".png"
            )
            .placeholder(R.drawable.ic_launcher_background)
            .into(holder.binding.topCurrencyImageView)
        /**

         *Gibt eine Debug-Nachricht aus, die die URL des geladenen Bildes enthält.
         *@param tag Der Tag, der der Nachricht zugeordnet ist
         *@param message Die Nachricht, die ausgegeben werden soll
         */
        Log.d("test", "https://s2.coinmarketcap.com/static/img/coins/64x64/" + item.id + ".png")

        /*
         Überprüft, ob die prozentuale Änderung in den letzten 24 Stunden größer als 0 ist.
         Wenn ja, wird der Text und die Farbe des topCurrencyChangeTextView entsprechend geändert.
         Wenn nein, wird der Text des topCurrencyChangeTextView auf das Symbol des Elements gesetzt und die Farbe entsprechend geändert.
         */
        if (item.quotes!![0].percentChange24h > 0) {
            holder.binding.topCurrencyChangeTextView.setTextColor(context.resources.getColor(R.color.green))
            // Setze den Text des topCurrencyChangeTextView im Binding auf den Prozentwert der Änderung in den letzten 24 Stunden
            holder.binding.topCurrencyChangeTextView.text =
                "+ ${String.format("%.02f", item.quotes[0].percentChange24h)} %"
        } else {
            holder.binding.topCurrencyChangeTextView.setTextColor(context.resources.getColor(R.color.red))
            // Setze den Text des topCurrencyChangeTextView im Binding auf den Prozentwert der Änderung in den letzten 24 Stunden
            holder.binding.topCurrencyChangeTextView.text =
                "${String.format("%.02f", item.quotes[0].percentChange24h)} %"
        }
    }

    /**

    Gibt die Anzahl der Elemente in der Liste zurück.
    @return Die Anzahl der Elemente in der Liste.
     */
    override fun getItemCount(): Int {
        return list.size
    }

}