package com.example.ecd_app.mapModels

import java.io.Serializable

/**
 * @author Suvanth Ramruthen
 * Object data class that holds information related to places held within a UserMap object
 * @property _title map title
 * @property places list of places(map markers)
 */
data class UserMap(
    val _title: String,
    val places: List<Place>
    ): Serializable