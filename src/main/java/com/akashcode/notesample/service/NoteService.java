package com.akashcode.notesample.service;

import com.akashcode.notesample.model.Note;
import com.akashcode.notesample.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class NoteService {

    @Autowired
    private NoteRepository noteRepository;

    // Create Note
    public Note createNote(Note note) {
        note.setShareUrl(null); // default private
        return noteRepository.save(note);
    }

    // Get All Notes
    public List<Note> getAllNotes() {
        return noteRepository.findAll();
    }

    // Get Note by ID
    public Optional<Note> getNoteById(Long id) {
        return noteRepository.findById(id);
    }

    // Update Note
    public Optional<Note> updateNote(Long id, Note updatedNote) {
        return noteRepository.findById(id).map(note -> {
            note.setTitle(updatedNote.getTitle());
            note.setContent(updatedNote.getContent());
            return noteRepository.save(note); // updatedAt auto-updated
        });
    }

    // Delete Note
    public void deleteNote(Long id) {
        noteRepository.deleteById(id);
    }

    // Share Note (generate UUID once)
    public Optional<String> shareNote(Long id) {
        return noteRepository.findById(id).map(note -> {
            if (note.getShareUrl() == null) {
                note.setShareUrl(UUID.randomUUID().toString());
                noteRepository.save(note); // updatedAt auto-updated
            }
            return note.getShareUrl();
        });
    }

    // Get Note by Share URL
    public Optional<Note> getNoteByShareUrl(String shareUrl) {
        return noteRepository.findByShareUrl(shareUrl);
    }
}
