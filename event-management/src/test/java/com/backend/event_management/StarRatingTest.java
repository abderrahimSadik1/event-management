package com.backend.event_management;

import org.junit.jupiter.api.Test;

import com.backend.event_management.model.StarRating;

import static org.junit.jupiter.api.Assertions.*;

class StarRatingTest {

    @Test
    void testEnumValues() {
        // Ensure all enum values are present
        StarRating[] ratings = StarRating.values();
        assertEquals(10, ratings.length); // There are 10 enum values
        assertTrue(ratings[0] == StarRating.HALF_STAR || ratings[0] == StarRating.ONE_STAR); // Checks that the first
                                                                                             // value is one of the
                                                                                             // correct values
    }

    @Test
    void testEnumGetValue() {
        // Ensure that the getValue() method returns the correct float value for each
        // star rating
        assertEquals(0.5f, StarRating.HALF_STAR.getValue());
        assertEquals(1.0f, StarRating.ONE_STAR.getValue());
        assertEquals(1.5f, StarRating.ONE_AND_HALF_STAR.getValue());
        assertEquals(2.0f, StarRating.TWO_STAR.getValue());
        assertEquals(2.5f, StarRating.TWO_AND_HALF_STAR.getValue());
        assertEquals(3.0f, StarRating.THREE_STAR.getValue());
        assertEquals(3.5f, StarRating.THREE_AND_HALF_STAR.getValue());
        assertEquals(4.0f, StarRating.FOUR_STAR.getValue());
        assertEquals(4.5f, StarRating.FOUR_AND_HALF_STAR.getValue());
        assertEquals(5.0f, StarRating.FIVE_STAR.getValue());
    }

    @Test
    void testEnumOrdinal() {
        // Ensure that the ordinal value corresponds to the expected index
        assertEquals(0, StarRating.HALF_STAR.ordinal());
        assertEquals(1, StarRating.ONE_STAR.ordinal());
        assertEquals(2, StarRating.ONE_AND_HALF_STAR.ordinal());
        assertEquals(3, StarRating.TWO_STAR.ordinal());
        assertEquals(4, StarRating.TWO_AND_HALF_STAR.ordinal());
        assertEquals(5, StarRating.THREE_STAR.ordinal());
        assertEquals(6, StarRating.THREE_AND_HALF_STAR.ordinal());
        assertEquals(7, StarRating.FOUR_STAR.ordinal());
        assertEquals(8, StarRating.FOUR_AND_HALF_STAR.ordinal());
        assertEquals(9, StarRating.FIVE_STAR.ordinal());
    }

    @Test
    void testEnumToString() {
        // Ensure that the toString() method returns the correct string representation
        assertEquals("HALF_STAR", StarRating.HALF_STAR.toString());
        assertEquals("ONE_STAR", StarRating.ONE_STAR.toString());
        assertEquals("ONE_AND_HALF_STAR", StarRating.ONE_AND_HALF_STAR.toString());
        assertEquals("TWO_STAR", StarRating.TWO_STAR.toString());
        assertEquals("TWO_AND_HALF_STAR", StarRating.TWO_AND_HALF_STAR.toString());
        assertEquals("THREE_STAR", StarRating.THREE_STAR.toString());
        assertEquals("THREE_AND_HALF_STAR", StarRating.THREE_AND_HALF_STAR.toString());
        assertEquals("FOUR_STAR", StarRating.FOUR_STAR.toString());
        assertEquals("FOUR_AND_HALF_STAR", StarRating.FOUR_AND_HALF_STAR.toString());
        assertEquals("FIVE_STAR", StarRating.FIVE_STAR.toString());
    }
}
