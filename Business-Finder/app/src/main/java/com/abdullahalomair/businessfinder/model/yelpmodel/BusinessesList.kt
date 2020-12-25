package com.abdullahalomair.businessfinder.model.yelpmodel

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*


@Entity
data class BusinessesList (
	@PrimaryKey var id:UUID = UUID.randomUUID(),
	val businesses : List<Businesses> = emptyList(),
)