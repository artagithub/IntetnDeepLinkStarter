package com.example.deeplinkstarter.enum

enum class AppParametersEnum {



    USER_NAME("USER_NAME"),
    USER_PASS("USER_PASS"),
    USER_ID("USER_ID"),
    COUNTRY("COUNTRY");


    val value:String

    constructor(value: String) {
        this.value = value
    }

    fun value() : String{
        return this.value
    }

}