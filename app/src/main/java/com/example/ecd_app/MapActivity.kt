package com.example.ecd_app

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ecd_app.mapModels.Place
import com.example.ecd_app.mapModels.UserMap
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.*

const val EXTRA_USER_MAP = "EXTRA_USER_MAP" //user maps extra
const val EXTRA_MAP_TITLE= "EXTRA_MAP_TITLE"
const val FILE_NAME= "UserMaps.data"

private const val REQUEST_CODE = 1234


/**
 * MapActivity displays a list of UserMaps that are loaded
 */
class MapActivity : AppCompatActivity() {

    private lateinit var userMapsList: MutableList<UserMap>//list that can be changed contains userMap objects
    private lateinit var mapAdapter : MapsAdapter//adapter for map list

    /**
     * onCreate function called to setup the Map List activity
     * @param savedInstanceState saved bundle state
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userMapsList = generateSampleData().toMutableList() //generate map data from list
        setContentView(R.layout.activity_map)//setting xml layout
        supportActionBar?.setDisplayHomeAsUpEnabled(true)//enabling back button
        supportActionBar?.title = "Facilities"//setting title



        val rvMaps: RecyclerView = findViewById(R.id.rvMaps)//recycler view reference for ui component
        rvMaps.layoutManager = LinearLayoutManager(this)//layout manager for recycler view lsit
        mapAdapter = MapsAdapter(this, userMapsList, object : MapsAdapter.OnClickListener {
            override fun onItemClick(position: Int) {
                val intent = Intent(this@MapActivity, DisplayMapActivity::class.java)//displays map clicked
                intent.putExtra(EXTRA_USER_MAP, userMapsList[position])//passes user map from list at position in list clciked
                startActivity(intent)//starting activity
            }

        })
        rvMaps.adapter = mapAdapter//setting adapter
    }


    /**
     * Generate map data
     * @return list of maps with markers
     */
    private fun generateSampleData(): List<UserMap> {
        return listOf(
            UserMap(
                "Clinics",
                listOf(
                    Place("Alphen Clinic", "021 444 9628", -34.019495929474544, 18.445572941731818),
                    Place("Claremont Clinic", "021 444 6426", -33.982751201362156, 18.4668554213602),
                    Place("Wynberg Clinic", "021 444 6613", -34.00444339207022, 18.470424811047856),
                    Place("Eastridge Clinic", "021 444 6379", -34.0453381891057, 18.621833393583543),
                    Place("Simon's Town Satellite Clinic", "021 786 1555", -34.179956171210314, 18.436279135206565)
                )
            ),
            UserMap("Hospitals",
                listOf(
                    Place("Groote Schuur Tertiary Hospital ", "021 404 9111", -33.940851202152935, 18.461772123197637),
                    Place("Mowbray Maternity Regional Hospital", "021 685 3026", -33.949155082066106, 18.4746776975524),
                    Place("DP Marais TB Hospital", "021 713 7600", -34.06210396647039, 18.459630868720563),
                    Place("Mitchell's Plain Hospital", "021 832 9200 ", -34.02146629490426, 18.61368319755477),
                    Place("Valkenberg Hospital", "021 440 3111", -33.93603264713252, 18.47776105337481)
                )),
            UserMap("Physical Health Resources",
                listOf(
                    Place("Red Cross War Memorial Children’s Hospital", "021 658 5111", -33.95450369148614, 18.48742408221094),
                    Place("UCT Paediatrics & Child Health Lab", "021 658 5314", -33.96948264030165, 18.543180243578416),
                    Place("Western Cape Infant Mental Health", "079 427 5217", -33.95373308339075, 18.474873768717185)
                )
            ),
            UserMap("Mums Support",
                listOf(
                    Place("Milk Matters", "021 659 5599", -33.94835607514468, 18.47447660591739),
                    Place("Breastfeeding Matters", "083 455 8338", -33.963411210481624, 18.476212924540327),
                    Place("Well Mother & Child Clinic", "021 689 6930", -33.95925630911498, 18.47676438036296)
                )
            ),
            UserMap("Counselling",
                listOf(
                    Place("Newlands Therapy Centre", "082 872 0192", -33.972420, 18.450300),
                    Place("LifeLine Western Cape", "021 461 1113", -33.950900, 18.481140),
                    Place("Hope House", "021 715 0424", -34.062248, 18.457150)
                )
            )
        )
    }
}