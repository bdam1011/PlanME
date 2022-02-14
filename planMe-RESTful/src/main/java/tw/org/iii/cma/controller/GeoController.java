package tw.org.iii.cma.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.maps.GeoApiContext;
import com.google.maps.PlaceDetailsRequest;
import com.google.maps.PlacesApi;
import com.google.maps.model.LatLng;
import com.google.maps.model.PlaceDetails;
import com.google.maps.model.PlaceType;
import com.google.maps.model.PlacesSearchResponse;
import com.google.maps.model.RankBy;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/foodmap")
public class GeoController {
	@GetMapping(path = "/map/{query}")
	public ResponseEntity<?> getDetail(@Value("${gmaps.api.key}") String apiKey, 
										@PathVariable String query) {
		GeoApiContext geoApiContext = new GeoApiContext.Builder()
				.apiKey(apiKey)
				.build();
		
		//textSearchQuery
		PlacesSearchResponse placesSearchResponse = null;
		try {
			placesSearchResponse = PlacesApi.textSearchQuery(geoApiContext, query).await();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		//PlaceDetails
		PlaceDetails placeDetails = null;
		try {
			placeDetails = PlacesApi.placeDetails(geoApiContext,placesSearchResponse.results[0].placeId)
					.fields(
							PlaceDetailsRequest.FieldMask.PLACE_ID,
							PlaceDetailsRequest.FieldMask.NAME,
							PlaceDetailsRequest.FieldMask.PHOTOS,
							PlaceDetailsRequest.FieldMask.FORMATTED_ADDRESS,
							PlaceDetailsRequest.FieldMask.FORMATTED_PHONE_NUMBER,
							PlaceDetailsRequest.FieldMask.URL,
							PlaceDetailsRequest.FieldMask.OPENING_HOURS,
							PlaceDetailsRequest.FieldMask.RATING)		
					.await();
		} catch (Exception e) {
			
			return new ResponseEntity<>(e.toString(), HttpStatus.BAD_REQUEST);
		}
		if(placeDetails == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(placeDetails);
		
		//Geocoding for placeId,latitude,longitude
//		GeocodingResult[] geocodingResults = null;
//		try {
//			geocodingResults = GeocodingApi.newRequest(geoApiContext)
//					.place(placesSearchResponse.results[0].placeId)
//					.await();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		final String placeId = geocodingResults[0].placeId;
//		final double latitude = geocodingResults[0].geometry.location.lat;
//		final double longitude = geocodingResults[0].geometry.location.lng;
		
//		//PlacesSearch
//		LatLng location = new LatLng(latitude, longitude);
//		PlacesSearchResponse nearbySearch = null;
//		try {
//			nearbySearch = PlacesApi.nearbySearchQuery(geoApiContext, location)
//					.radius(500)
//					.rankby(RankBy.PROMINENCE)
//					.openNow(true)
//					.type(PlaceType.RESTAURANT)
//					.type(PlaceType.MEAL_TAKEAWAY)
//					.type(PlaceType.MEAL_DELIVERY)
//					.await();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return ResponseEntity.ok(nearbySearch);
		
		// placeAutocomplete不吃中文??
//		AutocompletePrediction[] predictions = null;
//		try {
//			SessionToken sessionToken= new SessionToken();
//			String encodeQuery = URLEncoder.encode(query, "UTF-8");
//			predictions = PlacesApi.placeAutocomplete(geoApiContext, encodeQuery, sessionToken)
//					.radius(500)
//					.types(PlaceAutocompleteType.GEOCODE)
//					.await();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return ResponseEntity.ok(predictions);
	}
	@GetMapping(path = "/map/{latitude}/{longitude}")
	public ResponseEntity<?> getfood(@Value("${gmaps.api.key}") String apiKey,
									 @PathVariable double latitude,
									 @PathVariable double longitude) {
		GeoApiContext geoApiContext = new GeoApiContext.Builder()
				.apiKey(apiKey)
				.build();
		//PlacesSearch
		LatLng location = new LatLng(latitude, longitude);
		PlacesSearchResponse nearbySearch = null;
		PlaceDetails placeDetails = null;
		List<PlaceDetails> nearByDetail = new ArrayList<>();
		try {
			nearbySearch = PlacesApi.nearbySearchQuery(geoApiContext, location)
					.radius(500)
					.rankby(RankBy.PROMINENCE)
					.openNow(true)
					.type(PlaceType.RESTAURANT)
					.type(PlaceType.MEAL_TAKEAWAY)
					.type(PlaceType.MEAL_DELIVERY)
					.type(PlaceType.BAKERY)
					.type(PlaceType.BAR)
					.type(PlaceType.CAFE)
					.await();
			for(int i = 0;i<nearbySearch.results.length;i++) {
				placeDetails = PlacesApi.placeDetails(geoApiContext,nearbySearch.results[i].placeId)
						.fields(
								PlaceDetailsRequest.FieldMask.PLACE_ID,
								PlaceDetailsRequest.FieldMask.NAME,
								PlaceDetailsRequest.FieldMask.PHOTOS,
								PlaceDetailsRequest.FieldMask.FORMATTED_ADDRESS,
								PlaceDetailsRequest.FieldMask.FORMATTED_PHONE_NUMBER,
								PlaceDetailsRequest.FieldMask.URL,
								PlaceDetailsRequest.FieldMask.OPENING_HOURS,
								PlaceDetailsRequest.FieldMask.RATING)		
						.await();
				
				nearByDetail.add(placeDetails);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResponseEntity.ok(nearByDetail);
	}

}


