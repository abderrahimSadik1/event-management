package com.backend.event_management;

import com.backend.event_management.model.Evenement;
import com.backend.event_management.model.Notification;
import com.backend.event_management.model.Utilisateur;
import com.backend.event_management.repository.EvenementRepository;
import com.backend.event_management.repository.NotificationRepository;
import com.backend.event_management.repository.UtilisateurRepository;
import com.backend.event_management.service.NotificationService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class NotificationServiceTest {

    @InjectMocks
    private NotificationService notificationService;

    @Mock
    private NotificationRepository notificationRepository;

    @Mock
    private UtilisateurRepository utilisateurRepository;

    @Mock
    private EvenementRepository evenementRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void sendNotificationToAll_shouldSendNotificationSuccessfully() {
        // Arrange
        Utilisateur sender = new Utilisateur();
        sender.setId(1L);
        sender.setNom("Sender");

        Utilisateur receiver = new Utilisateur();
        receiver.setId(2L);
        receiver.setNom("Receiver");

        Evenement evenement = new Evenement();
        evenement.setId(1L);
        evenement.setNom("Test Event");
        List<Utilisateur> participants = new ArrayList<>();
        participants.add(receiver);
        evenement.setParticipants(participants);

        when(utilisateurRepository.findById(1L)).thenReturn(Optional.of(sender));
        when(evenementRepository.findById(1L)).thenReturn(Optional.of(evenement));

        Notification notification = Notification.builder()
                .message("Test message")
                .date(new Date())
                .Sender(sender)
                .Receivers(participants)
                .evenement(evenement)
                .build();

        when(notificationRepository.save(any(Notification.class))).thenReturn(notification);

        // Act
        notificationService.sendNotificationToAll("Test message", 1L, 1L);

        // Assert
        verify(notificationRepository, times(1)).save(any(Notification.class));
    }

    @Test
    void sendNotificationToAll_shouldThrowExceptionWhenSenderNotFound() {
        // Arrange
        when(utilisateurRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> notificationService.sendNotificationToAll("Test message", 1L, 1L));
        assertEquals("Sender not found", exception.getMessage());
    }

    @Test
    void sendNotificationToAll_shouldThrowExceptionWhenEventNotFound() {
        // Arrange
        Utilisateur sender = new Utilisateur();
        sender.setId(1L);
        sender.setNom("Sender");

        when(utilisateurRepository.findById(1L)).thenReturn(Optional.of(sender));
        when(evenementRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> notificationService.sendNotificationToAll("Test message", 1L, 1L));
        assertEquals("Event not found", exception.getMessage());
    }

    @Test
    void sendNotificationToAll_shouldThrowExceptionWhenNoReceiversFound() {
        // Arrange
        Utilisateur sender = new Utilisateur();
        sender.setId(1L);
        sender.setNom("Sender");

        Evenement evenement = new Evenement();
        evenement.setId(1L);
        evenement.setNom("Test Event");
        evenement.setParticipants(new ArrayList<>()); // Empty list of participants

        when(utilisateurRepository.findById(1L)).thenReturn(Optional.of(sender));
        when(evenementRepository.findById(1L)).thenReturn(Optional.of(evenement));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> notificationService.sendNotificationToAll("Test message", 1L, 1L));
        assertEquals("No receivers found for this event", exception.getMessage());
    }
}
