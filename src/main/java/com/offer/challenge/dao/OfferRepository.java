package com.offer.challenge.dao;

import com.offer.challenge.controllers.Offer;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface OfferRepository {

	String createOffer(String merchant, Offer offer);

	String updateOffer(String merchant, Offer offer);

	String deleteOffer(String merchant, Offer offer);

	Optional<Offer> getOffer(String merchant, String offerName);

	Optional<Set<Offer>> getOffers(String merchant);

	Optional<Map<String, Set<Offer>>> getAllOffers();
}
