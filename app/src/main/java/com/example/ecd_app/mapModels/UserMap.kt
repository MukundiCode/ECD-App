package com.example.ecd_app.mapModels

import com.example.ecd_app.mapModels.Place
import java.io.Serializable

data class UserMap(
    val _title: String,
    val places: List<Place>
    ): Serializable