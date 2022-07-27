package com.example.ecd_app

import java.io.Serializable

data class Article(
    val articleTitle: String,
    val articleDescriptor: String,
    val articleFileName: String
): Serializable
