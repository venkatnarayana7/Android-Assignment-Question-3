package com.example.android_assignement_question_3.model

data class Current(

    val temp_c: Double,

    val humidity: Int,

    val wind_kph: Double,

    val feelslike_c: Double,

    val condition: Condition

)