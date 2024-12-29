package com.backend.event_management;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.backend.event_management.model.Commentaire;
import com.backend.event_management.model.Evenement;
import com.backend.event_management.model.Utilisateur;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

public class CommentaireTest {

    @Mock
    private Utilisateur utilisateur;

    @Mock
    private Evenement evenement;

    @InjectMocks
    private Commentaire commentaire;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCommentaireCreation() {
        // Initialize date
        Date currentDate = new Date();

        // Create a new Commentaire instance using builder
        commentaire = Commentaire.builder()
                .contenu("This is a test comment")
                .date(currentDate)
                .utilisateur(utilisateur)
                .evenement(evenement)
                .build();

        // Assertions to verify the Commentaire instance is created correctly
        assertNotNull(commentaire);
        assertEquals("This is a test comment", commentaire.getContenu());
        assertEquals(currentDate, commentaire.getDate());
        assertEquals(utilisateur, commentaire.getUtilisateur());
        assertEquals(evenement, commentaire.getEvenement());
    }

    @Test
    public void testCommentaireWithNoArgsConstructor() {
        // Create Commentaire instance using no-args constructor
        commentaire = new Commentaire();
        commentaire.setContenu("Another comment");
        commentaire.setDate(new Date());

        // Assertions
        assertNotNull(commentaire);
        assertNull(commentaire.getId()); // Should be null since it's not persisted
        assertEquals("Another comment", commentaire.getContenu());
    }

    @Test
    public void testSetGetUtilisateur() {
        // Set a mock utilisateur
        commentaire.setUtilisateur(utilisateur);
        assertEquals(utilisateur, commentaire.getUtilisateur());
    }

    @Test
    public void testSetGetEvenement() {
        // Set a mock evenement
        commentaire.setEvenement(evenement);
        assertEquals(evenement, commentaire.getEvenement());
    }

    @Test
    public void testNoContent() {
        // Test a Commentaire with no content
        commentaire = Commentaire.builder().contenu("").build();
        assertEquals("", commentaire.getContenu());
    }

    @Test
    public void testCommentaireBuilder() {
        // Test the builder pattern for Commentaire
        commentaire = Commentaire.builder()
                .contenu("Comment using builder")
                .date(new Date())
                .build();

        assertNotNull(commentaire);
        assertEquals("Comment using builder", commentaire.getContenu());
    }

}
