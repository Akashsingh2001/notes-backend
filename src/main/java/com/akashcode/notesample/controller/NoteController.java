package com.akashcode.notesample.controller;

import com.akashcode.notesample.model.Note;
import com.akashcode.notesample.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/notes")
public class NoteController {

    @Autowired
    private NoteService noteService;

    // Create Note → 201 Created
    @PostMapping
    public ResponseEntity<Note> create(@RequestBody Note note, UriComponentsBuilder uriBuilder) {
        Note savedNote = noteService.createNote(note);

        var location = uriBuilder.path("/notes/{id}")
                .buildAndExpand(savedNote.getId())
                .toUri();

        return ResponseEntity.created(location).body(savedNote);
    }

    // Get All Notes
    @GetMapping
    public ResponseEntity<List<Note>> getAll() {
        return ResponseEntity.ok(noteService.getAllNotes());
    }

    // Get Note by ID
    @GetMapping("/{id}")
    public ResponseEntity<Note> getById(@PathVariable Long id) {
        return noteService.getNoteById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Update Note
    @PutMapping("/{id}")
    public ResponseEntity<Note> update(@PathVariable Long id, @RequestBody Note updatedNote) {
        return noteService.updateNote(id, updatedNote)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete Note
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        noteService.deleteNote(id);
        return ResponseEntity.noContent().build();
    }

    // Share Note
    @PostMapping("/{id}/share")
    public ResponseEntity<String> share(@PathVariable Long id) {
        return noteService.shareNote(id)
                .map(link -> ResponseEntity.ok("http://localhost:3000/notes/share/" + link))
                .orElse(ResponseEntity.notFound().build());
    }

    // Get Note by Share URL
    @GetMapping("/share/{shareUrl}")
    public ResponseEntity<Note> getShared(@PathVariable String shareUrl) {
        return noteService.getNoteByShareUrl(shareUrl)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
