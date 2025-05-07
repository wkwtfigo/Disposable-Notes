package com.example.onetime_note;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface NoteRepository extends JpaRepository<Note, UUID> {
    @Transactional(readOnly = true)
    Optional<Note> findByToken(String token);
}
