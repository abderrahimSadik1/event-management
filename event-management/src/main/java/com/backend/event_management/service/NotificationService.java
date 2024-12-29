package com.backend.event_management.service;

import com.backend.event_management.model.Evenement;
import com.backend.event_management.model.Notification;
import com.backend.event_management.model.Utilisateur;
import com.backend.event_management.repository.EvenementRepository;
import com.backend.event_management.repository.NotificationRepository;
import com.backend.event_management.repository.UtilisateurRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private EvenementRepository evenementRepository;

    // Send notification to all receivers of an event
    public void sendNotificationToAll(String message, Long senderId, Long evenementId) {
        // Find the sender
        Utilisateur sender = utilisateurRepository.findById(senderId)
                .orElseThrow(() -> new RuntimeException("Sender not found"));

        // Find the event and its associated receivers
        Evenement evenement = evenementRepository.findById(evenementId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        List<Utilisateur> receivers = evenement.getParticipants(); // Assuming the event has a 'participants' field

        if (receivers.isEmpty()) {
            throw new RuntimeException("No receivers found for this event");
        }

        // Create and save the notification
        Notification notification = Notification.builder()
                .message(message)
                .date(new Date())
                .Sender(sender)
                .Receivers(receivers)
                .evenement(evenement)
                .build();

        notificationRepository.save(notification);
    }

    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    public Notification getNotificationById(Long id) {
        return notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found"));
    }

    public void deleteNotification(Long id) {
        notificationRepository.deleteById(id);
    }
}