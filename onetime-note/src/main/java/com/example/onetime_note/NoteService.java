package com.example.onetime_note;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NoteService {
    private final NoteRepository noteRepository;

    public String createNote(String content, Duration ttl) {
        String token = UUID.randomUUID().toString();
        Note note = new Note();
        note.setToken(token);
        note.setContent(content);
        note.setCreatedAt(LocalDateTime.now());
        note.setExpiredAt(LocalDateTime.now().plus(ttl));
        noteRepository.save(note);
        return token;
    }

    public List<Note> getAllNotesSortedByDate() {
        return noteRepository.findAll(Sort.by(Sort.Order.desc("createdAt")));
    }

    public String readNote(String token) {
        Note note = noteRepository.findByToken(token).orElseThrow(() -> new RuntimeException("Note was not found!"));

        if (note.getExpiredAt().isBefore(LocalDateTime.now())) {
            noteRepository.delete(note);
            throw new RuntimeException("Note expired");
        }

        String content = note.getContent();
        noteRepository.delete(note);
        return content;
    }

}
