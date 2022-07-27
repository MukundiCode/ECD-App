package com.example.ecd_app

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.ecd_app.databinding.ActivityCreateMapBinding
import com.example.ecd_app.mapModels.Place
import com.example.ecd_app.mapModels.UserMap

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

import com.google.android.gms.maps.model.Marker
import com.google.android.material.snackbar.Snackbar

class CreateMapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityCreateMapBinding
    private  var markers = mutableListOf<Marker>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCreateMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = intent.getStringExtra(EXTRA_MAP_TITLE)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        mapFragment.view?.let { Snackbar.make(it, "Long press to add a marker!", Snackbar.LENGTH_INDEFINITE)
            .setAction("OK", {})
            .setActionTextColor(Color.WHITE)
            .show() }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_create_app, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //check that item is a save menu option
        if(item.itemId==R.id.miSave){

            if (markers.isEmpty()){
                Toast.makeText(this, "There must be at least one marker", Toast.LENGTH_SHORT).show()
                return true
            }
            val places= markers.map {
                    marker ->
                marker.title?.let { marker.snippet?.let { it1 ->
                    Place(it,
                        it1, marker.position.latitude, marker.position.longitude)
                } }
            }
            val userMap = intent.getStringExtra(EXTRA_MAP_TITLE)?.let { UserMap(it,
                places as List<Place>
            ) }
            val data = Intent()
            data.putExtra(EXTRA_USER_MAP, userMap)
            setResult(Activity.RESULT_OK, data)
            finish()




            return true

        }
        return super.onOptionsItemSelected(item)
    }



    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.setOnInfoWindowClickListener { markerToDelete ->
            markers.remove(markerToDelete)
            markerToDelete.remove()
        }

        mMap.setOnMapLongClickListener { latLng ->
//            val captureMarker = mMap.addMarker(MarkerOptions().position(latLng).title("my new marker").snippet("a cool snippet"))
//            if (captureMarker != null) {
//                markers.add(captureMarker)
//            }
            showAlertDialog(latLng)


        }

        // Add a marker in Sydney and move the camera
        val bbp = LatLng(-33.975340, 18.515350)
//        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in BBP"))
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(bbp,15f))
    }

    private fun showAlertDialog(latLng: LatLng){
        val placeFormView = LayoutInflater.from(this).inflate(R.layout.dialog_create_place, null)
        val dialog =
            AlertDialog.Builder(this)
                .setTitle("Create a marker")
                .setView(placeFormView)
                .setNegativeButton("Cancel", null)
                .setPositiveButton("OK",null).show()
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(){
            val title = placeFormView.findViewById<EditText>(R.id.etTitle).text.toString()
            val description = placeFormView.findViewById<EditText>(R.id.etDescription).text.toString()
            if(title.trim().isEmpty() || description.trim().isEmpty()){
                Toast.makeText(this@CreateMapActivity, "Please enter a title/description", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            val captureMarker = mMap.addMarker(MarkerOptions().position(latLng).title(title).snippet(description))
            if (captureMarker != null) {
                markers.add(captureMarker)
                dialog.dismiss()
            }
        }


    }
}