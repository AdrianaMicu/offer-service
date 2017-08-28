package com.offer.challenge.service;

import com.offer.challenge.controllers.Offer;
import com.offer.challenge.controllers.UpdateOfferRequest;

import java.util.Map;
import java.util.Set;

public interface OfferService {

	String createOffer(String merchant, Offer offerRequest);

	String updateOffer(String merchant, String offerName, UpdateOfferRequest updateOfferRequest);

	void deleteOffer(String merchant, String offerName);

	Set<Offer> getOffers(String merchant);

	Map<String, Set<Offer>> getAllOffers();
}
