package com.example.ecd_app

import java.io.Serializable

/**
 * Article data class holds the properties of the article objects
 * @property articleTitle title of article
 * @property articleDescriptor description of article
 * @property articleFileName file name of asset
 */
data class Article(
    val articleTitle: String,//title of article
    val articleDescriptor: String,//description of article
    val articleFileName: String//file name of asset
): Serializable
