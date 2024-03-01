package com.example.abschlussprojektmyapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.abschlussprojektmyapp.databinding.ActivityMainBinding

/**
 * Main Activity, dient als Einstiegspunkt für die App
 */
class MainActivity : AppCompatActivity() {

    /**

    Deklariert eine private lateinit Variable namens binding vom Typ ActivityMainBinding.
     */
    private lateinit var binding: ActivityMainBinding


    /**

    Überschreibt die Methode onCreate des Elternobjekts.
     * @param savedInstanceState Ein Bundle-Objekt, das den Zustand der Aktivität enthält.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /**

        Erstellt ein Binding-Objekt für die Aktivität mithilfe des generierten Binding-Klasse ActivityMainBinding.
        @param layoutInflater Der LayoutInflater, der verwendet wird, um die Ansicht zu erstellen.
         */
        binding = ActivityMainBinding.inflate(layoutInflater)

        /**

        Legt die Ansicht der Aktivität auf die Wurzelansicht des Binding-Objekts fest.
        @param binding.root Die Wurzelansicht des Binding-Objekts.
         */
        setContentView(binding.root)

        /**

        Sucht das NavHostFragment mit der ID fragmentContainerView im supportFragmentManager.
         */
        val navHost =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment

        navHost.navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.homeFragment || destination.id == R.id.newsFragment2 || destination.id == R.id.chatFragment ||
                destination.id == R.id.currencyConverterFragment2 || destination.id == R.id.top_Gain_LoseFragment
                || destination.id == R.id.notesFragment
            ) {
                binding.bottomNavigationView.visibility = View.VISIBLE
                binding.bottomNavigationView.setupWithNavController(navHost.navController)
                binding.bottomNavigationView.setOnItemSelectedListener {
                    NavigationUI.onNavDestinationSelected(it, navHost.navController)
                    navHost.navController.popBackStack(it.itemId, false)
                    true
                }
            } else {
                binding.bottomNavigationView.visibility = View.GONE
            }
        }

        /**

        Fügt einen Rückruf hinzu, der aufgerufen wird, wenn die Zurück-Taste gedrückt wird.
        @param isEnabled Gibt an, ob der Rückruf aktiviert ist oder nicht
         */
        onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            /**

            Überschreibt die Methode handleOnBackPressed, um das Verhalten des Zurück-Buttons anzupassen.
             */
            override fun handleOnBackPressed() {
                /**

                Navigiert zur übergeordneten Destination im aktuellen Navigationsgraphen.
                 */
                binding.fragmentContainerView.findNavController().navigateUp()

            }
        })
    }
}