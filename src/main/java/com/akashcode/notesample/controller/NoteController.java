package com.akashcode.notesample.controller;

import com.akashcode.notesample.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notes")
public class NoteController {

    @Autowired
    private NoteService noteService;

    @PostMapping("/{id}/share")
    public ResponseEntity<String> share(@PathVariable Long id) {
        String shareLink = noteService.shareNote(id);
        if (shareLink != null) {
            return ResponseEntity.ok(shareLink);  // Return the shareable link
        } else {
            return ResponseEntity.notFound().build();  // 404 if no note found
        }
    }

    // Other CRUD endpoints (Create, Read, Update, Delete) go here...
}
