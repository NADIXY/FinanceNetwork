package com.example.abschlussprojektmyapp.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.abschlussprojektmyapp.R

class CurrencySpinnerAdapter(context: Context, private val currencies: Array<String>
) : ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, currencies) {

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getDropDownView(position, convertView, parent)
        if (position == 0) {
            // Set the first item as hint
            (view as TextView).setTextColor(ContextCompat.getColor(context, R.color.gray))
        } else {
            (view as TextView).setTextColor(ContextCompat.getColor(context, R.color.black))
        }
        return view
    }
}





