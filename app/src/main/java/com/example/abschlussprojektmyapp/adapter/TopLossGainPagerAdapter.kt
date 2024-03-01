package com.example.abschlussprojektmyapp.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.abschlussprojektmyapp.ui.TopLossGainFragment

/**

Ein PagerAdapter für die TopLossGainFragmente.
*@param fragment Das übergeordnete Fragment, zu dem der Adapter gehört.
 */
class TopLossGainPagerAdapter( fragment: Fragment ) : FragmentStateAdapter( fragment ) {

    /**
    Gibt die Anzahl der Fragmente zurück, die im Pager angezeigt werden sollen.
    *@return Die Anzahl der Fragmente (immer 2).
     */
    override fun getItemCount(): Int {
        return 1
    }

    /**
    Erstellt ein neues TopLossGainFragment basierend auf der Position im Pager.
    *@param position Die Position des Fragments im Pager.
    *@return Das erstellte TopLossGainFragment.
     */
    override fun createFragment(position: Int): Fragment {
        val fragment = TopLossGainFragment()
        return fragment
    }
}


