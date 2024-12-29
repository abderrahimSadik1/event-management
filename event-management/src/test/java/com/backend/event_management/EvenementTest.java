package com.backend.event_management;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.backend.event_management.model.Evenement;
import com.backend.event_management.model.Utilisateur;

import java.sql.Date;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class EvenementTest {

    @Mock
    private Utilisateur createur;

    @Mock
    private Utilisateur participant1;

    @Mock
    private Utilisateur participant2;

    @InjectMocks
    private Evenement evenement;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testEvenementCreation() {
        // Initialize date
        Date currentDate = new Date(System.currentTimeMillis());

        // Create a new Evenement instance using builder
        evenement = Evenement.builder()
                .nom("Event Test")
                .description("Test Event Description")
                .date(currentDate)
                .lieu("Test Location")
                .imageUrl("http://example.com/image.jpg")
                .createur(createur)
                .participants(Arrays.asList(participant1, participant2))
                .build();

        // Assertions to verify the Evenement instance is created correctly
        assertNotNull(evenement);
        assertEquals("Event Test", evenement.getNom());
        assertEquals("Test Event Description", evenement.getDescription());
        assertEquals(currentDate, evenement.getDate());
        assertEquals("Test Location", evenement.getLieu());
        assertEquals("http://example.com/image.jpg", evenement.getImageUrl());
        assertEquals(createur, evenement.getCreateur());
        assertTrue(evenement.getParticipants().contains(participant1));
        assertTrue(evenement.getParticipants().contains(participant2));
    }

    @Test
    public void testEvenementWithNoArgsConstructor() {
        // Create Evenement instance using no-args constructor
        evenement = new Evenement();
        evenement.setNom("Another Event");
        evenement.setDescription("Another test event");

        // Assertions
        assertNotNull(evenement);
        assertNull(evenement.getId()); // Should be null since it's not persisted
        assertEquals("Another Event", evenement.getNom());
        assertEquals("Another test event", evenement.getDescription());
    }

    @Test
    public void testSetGetCreateur() {
        // Set a mock createur
        evenement.setCreateur(createur);
        assertEquals(createur, evenement.getCreateur());
    }

    @Test
    public void testSetGetParticipants() {
        // Set mock participants
        evenement.setParticipants(Arrays.asList(participant1, participant2));
        assertTrue(evenement.getParticipants().contains(participant1));
        assertTrue(evenement.getParticipants().contains(participant2));
    }

    @Test
    public void testAddParticipant() {
        // Add a new participant
        evenement.setParticipants(Arrays.asList(participant1, participant2));

        assertEquals(2, evenement.getParticipants().size());
        assertTrue(evenement.getParticipants().contains(participant2));
    }

    @Test
    public void testEvenementBuilder() {
        // Test the builder pattern for Evenement
        Date currentDate = new Date(System.currentTimeMillis());
        evenement = Evenement.builder()
                .nom("Event using builder")
                .date(currentDate)
                .build();

        assertNotNull(evenement);
        assertEquals("Event using builder", evenement.getNom());
        assertEquals(currentDate, evenement.getDate());
    }
}
