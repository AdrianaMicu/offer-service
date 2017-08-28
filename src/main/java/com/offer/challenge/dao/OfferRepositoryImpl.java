package com.offer.challenge.dao;

import com.offer.challenge.exceptions.OfferException;
import org.springframework.stereotype.Repository;

import com.offer.challenge.controllers.Offer;

import java.util.Optional;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;

@Repository
public class OfferRepositoryImpl implements OfferRepository {

	private Map<String, Set<Offer>> merchantOffersMap = new HashMap<>();

	public String createOffer(String merchant, Offer offer) {
		Set<Offer> currentOffers = merchantOffersMap.get(merchant);
		if (currentOffers == null) {
			currentOffers = new HashSet<>();
			merchantOffersMap.put(merchant, currentOffers);
		}
		if (!currentOffers.add(offer)) {
			throw new OfferException(String.format("Offer %s already exists for %s", offer.getName(), merchant));
		}
		return String.format("Successfully created offer %s for %s", offer.getName(), merchant);
	}

	public Optional<Set<Offer>> getOffers(String merchant) {
		return Optional.ofNullable(merchantOffersMap.get(merchant));
	}

	public Optional<Map<String, Set<Offer>>> getAllOffers() {
		return merchantOffersMap.isEmpty() ? Optional.empty() : Optional.of(merchantOffersMap);
	}

	public String updateOffer(String merchant, Offer offer) {
		Set<Offer> offers = merchantOffersMap.get(merchant);
		offers.remove(offer);
		offers.add(offer);
		return "Successfully updated offer " + offer.getName();
	}

	public Optional<Offer> getOffer(String merchant, String offerName) {
		Set<Offer> offers = merchantOffersMap.get(merchant);

		return offers == null ? Optional.empty() : offers.stream()
				.filter(o -> o.getName().equalsIgnoreCase(offerName))
				.findFirst();
	}

	public void deleteOffer(String merchant, Offer offer) {
		Set<Offer> offers = merchantOffersMap.get(merchant);
		offers.remove(offer);
 	}
}
