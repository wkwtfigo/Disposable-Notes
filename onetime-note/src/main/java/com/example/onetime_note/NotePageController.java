package com.example.onetime_note;

@Controller
@RequiredArgsConstructor
public class NotePageController {
    private final NoteService noteService;

    @GetMapping("/")
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
