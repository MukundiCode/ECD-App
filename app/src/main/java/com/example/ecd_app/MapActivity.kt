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

const val EXTRA_USER_MAP = "EXTRA_USER_MAP"
const val EXTRA_MAP_TITLE= "EXTRA_MAP_TITLE"
const val FILE_NAME= "UserMaps.data"

private const val REQUEST_CODE = 1234


class MapActivity : AppCompatActivity() {

    private lateinit var userMapsList: MutableList<UserMap>
    private lateinit var mapAdapter : MapsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        userMapsList = generateSampleData().toMutableList()
        setContentView(R.layout.activity_map)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Facilities"




        //set layout manager on the recycler view
        //set adapter on recycler view
        val rvMaps: RecyclerView = findViewById(R.id.rvMaps)
//        userMapsList = generateSampleData().toMutableList()
        rvMaps.layoutManager = LinearLayoutManager(this)
        mapAdapter = MapsAdapter(this, userMapsList, object : MapsAdapter.OnClickListener {
            override fun onItemClick(position: Int) {
//                Toast.makeText(this@MapActivity,"hi from main", Toast.LENGTH_LONG).show()
                val intent = Intent(this@MapActivity, DisplayMapActivity::class.java)
                intent.putExtra(EXTRA_USER_MAP, userMapsList[position])
                startActivity(intent)
            }

        })
        rvMaps.adapter = mapAdapter
    }





    private fun generateSampleData(): List<UserMap> {
        return listOf(
            UserMap(
                "Bhabisana Curated Facilities",
                listOf(
                    Place("Branner Hall", "Best dorm at Stanford", 37.426, -122.163),
                    Place("Gates CS building", "Many long nights in this basement", 37.430, -122.173),
                    Place("Pinkberry", "First date with my wife", 37.444, -122.170)
                )
            ),
            UserMap("Mental health resources",
                listOf(
                    Place("Tokyo", "Overnight layover", 35.67, 139.65),
                    Place("Ranchi", "Family visit + wedding!", 23.34, 85.31),
                    Place("Singapore", "Inspired by \"Crazy Rich Asians\"", 1.35, 103.82)
                )),
            UserMap("Physical Health Resources",
                listOf(
                    Place("Gardens by the Bay", "Amazing urban nature park", 1.282, 103.864),
                    Place("Jurong Bird Park", "Family-friendly park with many varieties of birds", 1.319, 103.706),
                    Place("Sentosa", "Island resort with panoramic views", 1.249, 103.830),
                    Place("Botanic Gardens", "One of the world's greatest tropical gardens", 1.3138, 103.8159)
                )
            ),
            UserMap("Milk donation sights",
                listOf(
                    Place("Chicago", "Urban center of the midwest, the \"Windy City\"", 41.878, -87.630),
                    Place("Rochester, Michigan", "The best of Detroit suburbia", 42.681, -83.134),
                    Place("Mackinaw City", "The entrance into the Upper Peninsula", 45.777, -84.727),
                    Place("Michigan State University", "Home to the Spartans", 42.701, -84.482),
                    Place("University of Michigan", "Home to the Wolverines", 42.278, -83.738)
                )
            ),
            UserMap("Mums Support",
                listOf(
                    Place("Champ's Diner", "Retro diner in Brooklyn", 40.709, -73.941),
                    Place("Althea", "Chicago upscale dining with an amazing view", 41.895, -87.625),
                    Place("Shizen", "Elegant sushi in San Francisco", 37.768, -122.422),
                    Place("Citizen Eatery", "Bright cafe in Austin with a pink rabbit", 30.322, -97.739),
                    Place("Kati Thai", "Authentic Portland Thai food, served with love", 45.505, -122.635)
                )
            )
        )
    }
}