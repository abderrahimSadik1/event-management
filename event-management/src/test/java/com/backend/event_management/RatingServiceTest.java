package com.backend.event_management;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.backend.event_management.model.Rating;
import com.backend.event_management.model.StarRating;
import com.backend.event_management.repository.RatingRepository;
import com.backend.event_management.service.RatingService;

public class RatingServiceTest {

    @Mock
    private RatingRepository ratingRepository;

    @InjectMocks
    private RatingService ratingService;

    private Rating rating;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        rating = new Rating();
        rating.setId(1L);
        rating.setStars(StarRating.FIVE_STAR);
        rating.setContenu("Great event!");
    }

    @Test
    void saveRating_shouldSaveRating() {
        // Arrange
        when(ratingRepository.save(any(Rating.class))).thenReturn(rating);

        // Act
        Rating result = ratingService.saveRating(rating);

        // Assert
        assertNotNull(result);
        assertEquals(StarRating.FIVE_STAR, result.getStars()); // Check the score
        verify(ratingRepository, times(1)).save(rating); // Verify save was called once
    }

    @Test
    void getAllRatings_shouldReturnListOfRatings() {
        // Arrange
        when(ratingRepository.findAll()).thenReturn(Arrays.asList(rating));

        // Act
        List<Rating> result = ratingService.getAllRatings();

        // Assert
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    void getRatingById_shouldReturnRating() {
        // Arrange
        when(ratingRepository.findById(1L)).thenReturn(Optional.of(rating));

        // Act
        Rating result = ratingService.getRatingById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId()); // Check the ID
    }

    @Test
    void getRatingById_shouldThrowExceptionWhenNotFound() {
        // Arrange
        when(ratingRepository.findById(2L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            ratingService.getRatingById(2L);
        });
        assertEquals("Rating not found", exception.getMessage());
    }

    @Test
    void deleteRating_shouldDeleteRating() {
        // Arrange
        doNothing().when(ratingRepository).deleteById(1L);

        // Act
        ratingService.deleteRating(1L);

        // Assert
        verify(ratingRepository, times(1)).deleteById(1L); // Verify delete was called once
    }
}
