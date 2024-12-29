package com.backend.event_management;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.backend.event_management.model.Evenement;
import com.backend.event_management.model.Rating;
import com.backend.event_management.model.StarRating;
import com.backend.event_management.model.Utilisateur;

import static org.junit.jupiter.api.Assertions.*;

public class RatingTest {

    @Mock
    private Utilisateur utilisateur;

    @Mock
    private Evenement evenement;

    @InjectMocks
    private Rating rating;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRatingCreation() {
        // Create a new Rating instance using builder
        rating = Rating.builder()
                .stars(StarRating.FIVE_STAR)
                .contenu("Great event!")
                .utilisateur(utilisateur)
                .evenement(evenement)
                .build();

        // Assertions to verify the Rating instance is created correctly
        assertNotNull(rating);
        assertEquals(StarRating.FIVE_STAR, rating.getStars());
        assertEquals("Great event!", rating.getContenu());
        assertEquals(utilisateur, rating.getUtilisateur());
        assertEquals(evenement, rating.getEvenement());
    }

    @Test
    public void testRatingWithNoArgsConstructor() {
        // Create Rating instance using no-args constructor
        rating = new Rating();
        rating.setStars(StarRating.THREE_STAR);
        rating.setContenu("Average event");

        // Assertions
        assertNotNull(rating);
        assertNull(rating.getId()); // Should be null since it's not persisted
        assertEquals(StarRating.THREE_STAR, rating.getStars());
        assertEquals("Average event", rating.getContenu());
    }

    @Test
    public void testSetGetStars() {
        // Set star rating
        rating.setStars(StarRating.FOUR_STAR);
        assertEquals(StarRating.FOUR_STAR, rating.getStars());
    }

    @Test
    public void testSetGetContenu() {
        // Set content
        rating.setContenu("Excellent event!");
        assertEquals("Excellent event!", rating.getContenu());
    }

    @Test
    public void testSetGetUtilisateur() {
        // Set mock utilisateur
        rating.setUtilisateur(utilisateur);
        assertEquals(utilisateur, rating.getUtilisateur());
    }

    @Test
    public void testSetGetEvenement() {
        // Set mock evenement
        rating.setEvenement(evenement);
        assertEquals(evenement, rating.getEvenement());
    }

    @Test
    public void testRatingBuilder() {
        // Test the builder pattern for Rating
        rating = Rating.builder()
                .stars(StarRating.TWO_STAR)
                .contenu("Needs improvement")
                .build();

        assertNotNull(rating);
        assertEquals(StarRating.TWO_STAR, rating.getStars());
        assertEquals("Needs improvement", rating.getContenu());
    }
}
