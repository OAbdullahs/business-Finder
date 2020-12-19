package com.abdullahalomair.businessfinder

fun getWeatherAnimationName(weatherCode:Int):String{
    return when(weatherCode){
        1000 -> "@raw/w_sunny"
        1003 -> "@raw/w_partially_cloudy"
        1006, 1009 -> "@raw/w_cloudy"
        1030, 1072,1135,1147,1150,1153,1171 -> "@raw/w_fog"
        1063,1180,1183,1186,1189,1192,1195,1198,1201,1240,1243,1246 -> "@raw/w_rain"
        1066, 1114,1117,1210,1213,1216,1219,1222,1225,1237,1255,1261,1264,1282 -> "@raw/w_snow"
        1069, 1087,1204,1207, 1249,1252,1258,1273,1276 -> "@raw\\w_thunder"
        else ->{
            ""
        }
    }
}