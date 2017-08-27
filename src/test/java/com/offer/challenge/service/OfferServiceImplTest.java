package com.offer.challenge.service;

import com.offer.challenge.controllers.Offer;
import com.offer.challenge.controllers.UpdateOfferRequest;
import com.offer.challenge.dao.OfferRepository;
import com.offer.challenge.exceptions.OfferException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Captor;
import org.mockito.ArgumentCaptor;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Optional;

import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OfferServiceImplTest {

    @InjectMocks
    private OfferServiceImpl offerService;

    @Mock
    private OfferRepository offerRepo;

    @Captor
    private ArgumentCaptor<Offer> offerArgumentCaptor;

    @Test
    public void getOffers_shouldThrowException() {
        String merchant = "merchant";
        when(offerRepo.getOffers(merchant)).thenReturn(Optional.empty());

        Throwable throwable = catchThrowable(() -> offerService.getOffers(merchant));

        assertThat(throwable).isNotNull();
        assertThat(throwable).isInstanceOf(OfferException.class);
        assertEquals("Could not find any offers for merchant " + merchant, throwable.getMessage());
        verify(offerRepo).getOffers(anyString());
        verifyNoMoreInteractions(offerRepo);
    }

    @Test
    public void getAllOffers_shouldThrowException() {
        when(offerRepo.getAllOffers()).thenReturn(Optional.empty());

        Throwable throwable = catchThrowable(() -> offerService.getAllOffers());

        assertThat(throwable).isNotNull();
        assertThat(throwable).isInstanceOf(OfferException.class);
        assertEquals("Could not find any offers", throwable.getMessage());
        verify(offerRepo).getAllOffers();
        verifyNoMoreInteractions(offerRepo);
    }

    @Test
    public void updateOffer_shouldSucceed() {
        String merchant = "merchant";
        String originalName = "offerName";
        String originalDescription = "originalDescription";
        Currency originalCurrency = Currency.getInstance("GBP");
        BigDecimal originalAmount = new BigDecimal("20");
        Offer offer = new Offer();
        offer.setName(originalName);
        offer.setDescription(originalDescription);
        offer.setPriceCurrency(originalCurrency);
        offer.setPriceAmount(originalAmount);
        when(offerRepo.getOffer(merchant, originalName)).thenReturn(Optional.of(offer));

        UpdateOfferRequest updateOfferRequest = new UpdateOfferRequest();
        updateOfferRequest.setDescription("updatedDescription");
        updateOfferRequest.setPriceAmount(new BigDecimal("200"));

        offerService.updateOffer(merchant, originalName, updateOfferRequest);

        verify(offerRepo).updateOffer(anyString(), offerArgumentCaptor.capture());

        Offer updateOffer = offerArgumentCaptor.getValue();
        assertEquals(originalName, updateOffer.getName());
        assertNotEquals(originalDescription, updateOffer.getDescription());
        assertNotEquals(originalAmount, updateOffer.getPriceAmount());
        assertEquals(originalCurrency, updateOffer.getPriceCurrency());
        assertEquals(updateOfferRequest.getDescription(), updateOffer.getDescription());
        assertEquals(updateOfferRequest.getPriceAmount(), updateOffer.getPriceAmount());
    }

    @Test(expected = OfferException.class)
    public void updateOffer_shouldThrowException() {
        when(offerRepo.getOffer(anyString(), anyString())).thenReturn(Optional.empty());
        offerService.updateOffer("merchant", "offerName", mock(UpdateOfferRequest.class));
    }
}
