package com.example.mtstest1.Models

data class Author (
    //var uri : String? = null,
    //var approved : String? = null,
    var authordisplay : String? = null,
    var authorid : String? = null,
    //var authorlast : String? = null,
    //var authorlastfirst : String? = null,
    //var authorlastlc : String? = null,
    //var titles : String? = null,
    var lastinitial : String? = null,
    //var works : String? = null
)
data class AuthorList(
    var author: List<Author>? = null
)