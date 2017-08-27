package com.offer.challenge.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.offer.challenge.service.OfferService;

import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping(value = "/")
public class OfferController {

	private static final String OFFER_URL = "offers";
	private static final String ALL_OFFERS_URL = "alloffers";
	
	@Autowired
	private OfferService offerService;
	
	@RequestMapping(value = OFFER_URL, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public String createOffer(
			@RequestParam(required = true) String merchant,
			@Valid @RequestBody Offer offerRequest) {
		return offerService.createOffer(merchant, offerRequest);
	}

	@RequestMapping(value = OFFER_URL, method = RequestMethod.GET)
	public Set<Offer> getOffers(
			@RequestParam(required = false) String merchant) {
		return offerService.getOffers(merchant);
	}

	@RequestMapping(value = ALL_OFFERS_URL, method = RequestMethod.GET)
	public Map<String, Set<Offer>> getAllOffers() {
		return offerService.getAllOffers();
	}

	@RequestMapping(value = OFFER_URL, method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public String updateOffer(
			@RequestParam(required = true) String merchant,
			@RequestParam(required = true) String offerName,
			@Valid @RequestBody UpdateOfferRequest updateOfferRequest) {
		return offerService.updateOffer(merchant, offerName, updateOfferRequest);
	}

	@RequestMapping(value = OFFER_URL, method = RequestMethod.DELETE)
	public String deleteOffer(
			@RequestParam(required = true) String merchant,
			@RequestParam(required = true) String offerName) {
		return offerService.deleteOffer(merchant, offerName);
	}
}
