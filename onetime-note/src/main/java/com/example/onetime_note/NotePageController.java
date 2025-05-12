package com.example.onetime_note;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @PostMapping("/create")
    public String createNote(@RequestParam("content") String content,
                             @RequestParam("ttl") String ttl,
                             RedirectAttributes redirectAttributes) {
        try {
            Duration ttlDuration = Duration.ofMinutes(Long.parseLong(ttl));
            String token = noteService.createNote(content, ttlDuration);
            redirectAttributes.addFlashAttribute("message", "Note was successfully added!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error creating a message: " + e.getMessage());
        }
        return "redirect:/";
    }

    @GetMapping("/notes/{token}")
    public String viewNote(@PathVariable("token") String token, Model model) {
        String content = noteService.readNote(token);
        model.addAttribute("noteContent", content);
        return "view-note";
    }
}
