package com.example.ecd_app.mapModels

import java.io.Serializable

/**
 * @author Suvanth Ramruthen
 *Object data class that holds information related to map marker locations
 * @property title marker title
 * @property description marker description used for contact details
 * @property latitude marker latitude position
 * @property longitude marker longitude position
 */
data class Place(
    val title: String,
    val description: String,
    val latitude: Double,
    val longitude: Double
    ):Serializable
