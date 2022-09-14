package com.example.ecd_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ecd_app.databinding.ActivityDisplayMapBinding
import com.example.ecd_app.mapModels.UserMap
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.LatLngBounds

/**
 * @author Suvanth Ramruthen
 * Android Studio Base fragment template was used. This class leverages Google Maps API. Renders MAP
 */
class DisplayMapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap//Google map Object
    private lateinit var userMap: UserMap//User Map object to be referenced
    private lateinit var binding: ActivityDisplayMapBinding//Binding map fragment to activity

    /**
     * onCreate is called to setup activity with the correct details and retrieve intents
     * @param savedInstanceState
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDisplayMapBinding.inflate(layoutInflater)
        setContentView(binding.root)//setting appropriate view

        userMap = intent.getSerializableExtra(EXTRA_USER_MAP) as UserMap //getting serialized user map to be displayed

        supportActionBar?.title = userMap._title//setting title to map title
        supportActionBar?.setDisplayHomeAsUpEnabled(true)//enabling

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add markers from place list in UserMap
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val boundsBuilder = LatLngBounds.builder()//BUilds boundaries of map display
        //adding place markers to map display
        for(place in userMap.places){
            val latLngMarker = LatLng(place.latitude, place.longitude) //construct marker based on positions received
            boundsBuilder.include(latLngMarker)//adding coordinates to boundsBuilder
            mMap.addMarker(MarkerOptions().position(latLngMarker).title(place.title).snippet(place.description))//adding markers title and description to marker

        }
        //Animation for map zoom in
        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(boundsBuilder.build(), 1000, 1000, 0))
    }
}