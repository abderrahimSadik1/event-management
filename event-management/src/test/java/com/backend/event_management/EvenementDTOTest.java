package com.backend.event_management;

import org.junit.jupiter.api.Test;

import com.backend.event_management.dto.EvenementDTO;

import java.sql.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EvenementDTOTest {

    @Test
    void testConstructorAndGetters() {
        // Arrange
        Long id = 1L;
        String nom = "Sample Event";
        String description = "This is a sample event";
        Date date = Date.valueOf("2024-12-29");
        String lieu = "Sample Location";
        String imageBase64 = "base64encodedImage";
        Long createurId = 2L;
        List<Long> participantIds = List.of(3L, 4L, 5L);

        // Act
        EvenementDTO evenementDTO = new EvenementDTO(id, nom, description, date, lieu, imageBase64, createurId,
                participantIds);

        // Assert
        assertEquals(id, evenementDTO.getId());
        assertEquals(nom, evenementDTO.getNom());
        assertEquals(description, evenementDTO.getDescription());
        assertEquals(date, evenementDTO.getDate());
        assertEquals(lieu, evenementDTO.getLieu());
        assertEquals(imageBase64, evenementDTO.getImageBase64());
        assertEquals(createurId, evenementDTO.getCreateurId());
        assertEquals(participantIds, evenementDTO.getParticipantIds());
    }

    @Test
    void testBuilder() {
        // Arrange and Act
        EvenementDTO evenementDTO = EvenementDTO.builder()
                .id(1L)
                .nom("Sample Event")
                .description("This is a sample event")
                .date(Date.valueOf("2024-12-29"))
                .lieu("Sample Location")
                .imageBase64("base64encodedImage")
                .createurId(2L)
                .participantIds(List.of(3L, 4L, 5L))
                .build();

        // Assert
        assertEquals(1L, evenementDTO.getId());
        assertEquals("Sample Event", evenementDTO.getNom());
        assertEquals("This is a sample event", evenementDTO.getDescription());
        assertEquals(Date.valueOf("2024-12-29"), evenementDTO.getDate());
        assertEquals("Sample Location", evenementDTO.getLieu());
        assertEquals("base64encodedImage", evenementDTO.getImageBase64());
        assertEquals(2L, evenementDTO.getCreateurId());
        assertEquals(List.of(3L, 4L, 5L), evenementDTO.getParticipantIds());
    }

    @Test
    void testSetters() {
        // Arrange
        EvenementDTO evenementDTO = new EvenementDTO();

        // Act
        evenementDTO.setId(1L);
        evenementDTO.setNom("Updated Event");
        evenementDTO.setDescription("Updated description");
        evenementDTO.setDate(Date.valueOf("2024-12-30"));
        evenementDTO.setLieu("Updated Location");
        evenementDTO.setImageBase64("updatedBase64Image");
        evenementDTO.setCreateurId(3L);
        evenementDTO.setParticipantIds(List.of(6L, 7L, 8L));

        // Assert
        assertEquals(1L, evenementDTO.getId());
        assertEquals("Updated Event", evenementDTO.getNom());
        assertEquals("Updated description", evenementDTO.getDescription());
        assertEquals(Date.valueOf("2024-12-30"), evenementDTO.getDate());
        assertEquals("Updated Location", evenementDTO.getLieu());
        assertEquals("updatedBase64Image", evenementDTO.getImageBase64());
        assertEquals(3L, evenementDTO.getCreateurId());
        assertEquals(List.of(6L, 7L, 8L), evenementDTO.getParticipantIds());
    }
}
