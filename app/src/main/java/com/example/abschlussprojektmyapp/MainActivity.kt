package com.example.abschlussprojektmyapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.abschlussprojektmyapp.data.remote.Api
import com.example.abschlussprojektmyapp.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

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
        //setContentView(R.layout.activity_main)

        /**

        Sucht das NavHostFragment mit der ID fragmentContainerView im supportFragmentManager.
         */
        val navHost =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment

        /**

        Verknüpft die bottomNavigationView mit dem NavController des gefundenen NavHostFragments.
         */

        binding.bottomNavigationView.setupWithNavController(navHost.navController)

        navHost.navController.addOnDestinationChangedListener { _, destination, _ -> //Fügt einen Listener hinzu, um Änderungen im Navigationsziel zu überwachen
            when (destination.id) { //Überprüft das Ziel und passt die Sichtbarkeit der unteren Navigationsleiste entsprechend an
                R.id.loginFragment -> binding.bottomNavigationView.visibility =
                    View.GONE //Wenn das Ziel das loginFragment ist, wird die untere Navigationsleiste ausgeblendet
                else -> binding.bottomNavigationView.visibility =
                    View.VISIBLE //In allen anderen Fällen wird die untere Navigationsleiste sichtbar gemacht
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


/*
//Api Test:
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    // Überprüfen, ob die API funktioniert
    checkApi()
}
private fun checkApi() {
    GlobalScope.launch(Dispatchers.IO) {
        try {
            val response = Api.retrofitService.getMarketData()
            Log.d("Response",response.toString())
        } catch (e: Exception) {
            Log.e("API_TEST", "Exception: ${e.message}")
        }
    }
}
}
 */



