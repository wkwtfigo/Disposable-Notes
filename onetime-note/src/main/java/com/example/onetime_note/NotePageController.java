package com.example.onetime_note;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class NotePageController {
    private final NoteService noteService;

    @GetMapping("/")
    public String homePage(Model model) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM, yyyy");
        String currentDate = LocalDate.now().format(formatter);
        
        List<Note> notes = noteService.getAllNotesSortedByDate();
        
        model.addAttribute("currentDate", currentDate);
        model.addAttribute("notes", notes);
        return "default";
    }

    @GetMapping("/create")
    public String createPage() {
        return "create-note";
    }

    @GetMapping("/notes/{token}")
    public String viewNote(@PathVariable String token, Model model) {
        String content = noteService.readNote(token);
        model.addAttribute("noteContent", content);
        return "view-note";
    }
}
