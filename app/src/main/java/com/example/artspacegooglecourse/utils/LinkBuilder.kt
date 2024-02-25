package com.example.artspacegooglecourse.utils

fun linkBuilder(
    id : String,
    apiLink: String
):String {
    println("$apiLink/$id/full/full/0/default.jpg")
  return("$apiLink/$id/full/full/0/default.jpg")
}
