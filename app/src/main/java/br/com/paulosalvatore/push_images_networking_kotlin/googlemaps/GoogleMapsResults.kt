package br.com.paulosalvatore.push_images_networking_kotlin.googlemaps

import com.google.gson.annotations.SerializedName

/**
 * Created by paulo on 01/05/2018.
 */

object GoogleMaps {
	data class Results(
			@SerializedName(value = "results")
			val addresses: List<Address>,
			@SerializedName(value = "status")
			val status: String? = null
	)

	data class Address(
			@SerializedName(value = "formatted_address")
			val formattedAddress: String? = null,
			@SerializedName(value = "geometry")
			val geometry: Geometry? = null
	)

	data class Geometry(
			@SerializedName(value = "location")
			val location: Location? = null
	)

	data class Location(
			@SerializedName(value = "lat")
			val latitude: Double = 0.0,
			@SerializedName(value = "lng")
			val longitude: Double = 0.0
	)
}
