package com.offer.challenge.service;

import com.offer.challenge.controllers.UpdateOfferRequest;
import com.offer.challenge.exceptions.OfferException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.offer.challenge.controllers.Offer;
import com.offer.challenge.dao.OfferRepository;

import java.util.Map;
import java.util.Set;

@Service
public class OfferServiceImpl implements OfferService {

	@Autowired
	private OfferRepository offerRepo;
	
	public String createOffer(String merchant, Offer offerRequest) {
		return offerRepo.createOffer(merchant, offerRequest);
	}

	public Set<Offer> getOffers(String merchant) {
		return offerRepo.getOffers(merchant).orElseThrow(() -> new OfferException("Could not find any offers for merchant " + merchant));
	}

	public Map<String, Set<Offer>> getAllOffers() {
		return offerRepo.getAllOffers().orElseThrow(() -> new OfferException("Could not find any offers"));
	}

	public String updateOffer(String merchant, String offerName, UpdateOfferRequest offerRequest) {
		Offer offerToUpdate = offerRepo.getOffer(merchant, offerName).orElseThrow(() -> new OfferException(String.format("Offer %s for merchant %s not found", offerName, merchant)));
		if (StringUtils.isNotBlank(offerRequest.getDescription())) {
			offerToUpdate.setDescription(offerRequest.getDescription());
		}
		if (offerRequest.getPriceCurrency() != null) {
			offerToUpdate.setPriceCurrency(offerRequest.getPriceCurrency());
		}
		if (offerRequest.getPriceAmount() != null) {
			offerToUpdate.setPriceAmount(offerRequest.getPriceAmount());
		}
		return offerRepo.updateOffer(merchant, offerToUpdate);
	}

	public void deleteOffer(String merchant, String offerName) {
		Offer offerToDelete = offerRepo.getOffer(merchant, offerName).orElseThrow(() -> new OfferException(String.format("Offer %s for merchant %s not found", offerName, merchant)));
		offerRepo.deleteOffer(merchant, offerToDelete);
	}
}
