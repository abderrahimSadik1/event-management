package com.backend.event_management;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.backend.event_management.model.Evenement;
import com.backend.event_management.model.Notification;
import com.backend.event_management.model.Utilisateur;

import java.util.Arrays;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class NotificationTest {

    @Mock
    private Utilisateur sender;

    @Mock
    private Utilisateur receiver1;

    @Mock
    private Utilisateur receiver2;

    @Mock
    private Evenement evenement;

    @InjectMocks
    private Notification notification;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testNotificationCreation() {
        // Initialize date
        Date currentDate = new Date();

        // Create a new Notification instance using builder
        notification = Notification.builder()
                .message("Test Notification Message")
                .date(currentDate)
                .Sender(sender)
                .Receivers(Arrays.asList(receiver1, receiver2))
                .evenement(evenement)
                .build();

        // Assertions to verify the Notification instance is created correctly
        assertNotNull(notification);
        assertEquals("Test Notification Message", notification.getMessage());
        assertEquals(currentDate, notification.getDate());
        assertEquals(sender, notification.getSender());
        assertTrue(notification.getReceivers().contains(receiver1));
        assertTrue(notification.getReceivers().contains(receiver2));
        assertEquals(evenement, notification.getEvenement());
    }

    @Test
    public void testNotificationWithNoArgsConstructor() {
        // Create Notification instance using no-args constructor
        notification = new Notification();
        notification.setMessage("Another Notification");
        notification.setDate(new Date());

        // Assertions
        assertNotNull(notification);
        assertNull(notification.getId()); // Should be null since it's not persisted
        assertEquals("Another Notification", notification.getMessage());
    }

    @Test
    public void testSetGetSender() {
        // Set a mock sender
        notification.setSender(sender);
        assertEquals(sender, notification.getSender());
    }

    @Test
    public void testSetGetReceivers() {
        // Set mock receivers
        notification.setReceivers(Arrays.asList(receiver1, receiver2));
        assertTrue(notification.getReceivers().contains(receiver1));
        assertTrue(notification.getReceivers().contains(receiver2));
    }

    @Test
    public void testSetGetEvenement() {
        // Set mock evenement
        notification.setEvenement(evenement);
        assertEquals(evenement, notification.getEvenement());
    }

    @Test
    public void testAddReceiver() {
        // Add a new receiver
        notification.setReceivers(Arrays.asList(receiver1, receiver2));

        assertEquals(2, notification.getReceivers().size());
        assertTrue(notification.getReceivers().contains(receiver2));
    }

    @Test
    public void testNotificationBuilder() {
        // Test the builder pattern for Notification
        Date currentDate = new Date();
        notification = Notification.builder()
                .message("Notification using builder")
                .date(currentDate)
                .build();

        assertNotNull(notification);
        assertEquals("Notification using builder", notification.getMessage());
        assertEquals(currentDate, notification.getDate());
    }
}
