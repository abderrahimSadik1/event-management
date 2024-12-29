package com.backend.event_management.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EvenementDTO {
    private Long id;
    private String nom;
    private String description;
    private Date date;
    private String lieu;
    private String imageBase64;
    private Long createurId; // ID of the creator
    private List<Long> participantIds; // IDs of participants
}
