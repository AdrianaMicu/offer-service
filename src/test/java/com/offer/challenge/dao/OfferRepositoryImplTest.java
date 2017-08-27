package com.offer.challenge.dao;

import com.offer.challenge.controllers.Offer;
import com.offer.challenge.exceptions.OfferException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Optional;

import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class OfferRepositoryImplTest {

    @InjectMocks
    OfferRepositoryImpl offerRepository;

    private Offer offer1;
    private Offer offer2;

    private String merchant1;
    private String merchant2;

    @Before
    public void setup() {
        offer1 = new Offer();
        offer1.setName("name1");
        offer1.setDescription("description1");
        offer1.setPriceCurrency(Currency.getInstance("GBP"));
        offer1.setPriceAmount(new BigDecimal("20"));

        offer2 = new Offer();
        offer2.setName("name2");
        offer2.setDescription("description2");
        offer2.setPriceCurrency(Currency.getInstance("EUR"));
        offer2.setPriceAmount(new BigDecimal("50"));

        merchant1 = "merchant1";
        merchant2 = "merchant2";
    }

    @Test
    public void createOffer_shouldSucceed() {
        String result1 = offerRepository.createOffer(merchant1, offer1);
        String result2 = offerRepository.createOffer(merchant2, offer2);

        assertEquals(String.format("Successfully created offer %s for %s", offer1.getName(), merchant1), result1);
        assertEquals(String.format("Successfully created offer %s for %s", offer2.getName(), merchant2), result2);

        assertTrue(offerRepository.getOffers(merchant1).isPresent());
        assertTrue(offerRepository.getOffers(merchant2).isPresent());
        assertTrue(offerRepository.getOffer(merchant1, offer1.getName()).isPresent());
        assertTrue(offerRepository.getOffer(merchant2, offer2.getName()).isPresent());
        assertOffer(offer1, offerRepository.getOffer(merchant1, offer1.getName()).get());
        assertOffer(offer2, offerRepository.getOffer(merchant2, offer2.getName()).get());
    }

    @Test
    public void createOffer_shouldNotAllowDuplicateOffersAndShouldThrowException() {
        String result1 = offerRepository.createOffer(merchant1, offer1);
        Throwable throwable = catchThrowable(() -> offerRepository.createOffer(merchant1, offer1));

        assertThat(throwable).isNotNull();
        assertThat(throwable).isInstanceOf(OfferException.class);
        assertEquals(String.format("Offer %s already exists for %s", offer1.getName(), merchant1), throwable.getMessage());

        assertEquals(String.format("Successfully created offer %s for %s", offer1.getName(), merchant1), result1);
        assertTrue(offerRepository.getOffers(merchant1).isPresent());
        assertEquals(offerRepository.getOffers(merchant1).get().size(), 1);
        assertTrue(offerRepository.getOffer(merchant1, offer1.getName()).isPresent());
        assertOffer(offer1, offerRepository.getOffer(merchant1, offer1.getName()).get());
    }

    @Test
    public void updateOffer_shouldSucceed() {
        String result1 = offerRepository.createOffer(merchant1, offer1);

        assertEquals(String.format("Successfully created offer %s for %s", offer1.getName(), merchant1), result1);
        assertTrue(offerRepository.getOffers(merchant1).isPresent());
        assertTrue(offerRepository.getOffer(merchant1, offer1.getName()).isPresent());
        assertOffer(offer1, offerRepository.getOffer(merchant1, offer1.getName()).get());

        Offer offerToUpdate = new Offer();
        offerToUpdate.setName(offer1.getName());
        offerToUpdate.setDescription("updatedDescription");
        offerToUpdate.setPriceAmount(new BigDecimal("200"));
        String updateResult = offerRepository.updateOffer(merchant1, offerToUpdate);

        assertEquals("Successfully updated offer " + offer1.getName(), updateResult);
        assertTrue(offerRepository.getOffers(merchant1).isPresent());
        assertTrue(offerRepository.getOffer(merchant1, offer1.getName()).isPresent());
        assertOffer(offerToUpdate, offerRepository.getOffer(merchant1, offer1.getName()).get());
    }

    @Test
    public void getOffer_shouldSucceed() {
        offerRepository.createOffer(merchant1, offer1);

        Optional<Offer> offerFound = offerRepository.getOffer(merchant1, offer1.getName());
        Optional<Offer> offerNotFound = offerRepository.getOffer(merchant1, offer2.getName());

        assertTrue(offerFound.isPresent());
        assertFalse(offerNotFound.isPresent());
    }

    @Test
    public void deleteOffer_shouldSucceed() {
        offerRepository.createOffer(merchant1, offer1);
        offerRepository.createOffer(merchant1, offer2);

        assertTrue(offerRepository.getOffer(merchant1, offer1.getName()).isPresent());
        assertTrue(offerRepository.getOffer(merchant1, offer2.getName()).isPresent());

        offerRepository.deleteOffer(merchant1, offer1);

        assertTrue(offerRepository.getOffers(merchant1).isPresent());
        assertFalse(offerRepository.getOffer(merchant1, offer1.getName()).isPresent());
    }

    private void assertOffer(Offer expected, Offer actual) {
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getDescription(), actual.getDescription());
        assertEquals(expected.getPriceCurrency(), actual.getPriceCurrency());
        assertEquals(expected.getPriceAmount(), actual.getPriceAmount());
    }
}
