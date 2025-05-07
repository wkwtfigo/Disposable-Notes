package com.example.onetime_note;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
    @Index(name = "idx_note_token", columnList = "token"),
    @Index(name = "idx_note_expired_at", columnList = "expiredAt")
})
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String token;

    @Column(columnDefinition = "TEXT")
    private String content;

    @NotNull(message = "Creation date cannot be null")
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @NotNull(message = "Expiration date cannot be null")
    @Column(updatable = false)
    private LocalDateTime expiredAt;

    @AssertTrue(message = "Expiration date must be after creation date")
    public boolean isExpiredAfterCreated() {
        return expiredAt == null || createdAt == null || expiredAt.isAfter(createdAt);
    }

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }
}
