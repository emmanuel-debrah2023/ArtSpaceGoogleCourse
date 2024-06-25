package com.example.artspacegooglecourse

import kotlinx.serialization.Serializable

@Serializable
object Gallery

@Serializable
data class Art(val id: Int, val imageId: String)


