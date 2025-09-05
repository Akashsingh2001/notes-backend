package com.akashcode.notesample.repository;

import com.akashcode.notesample.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface NoteRepository extends JpaRepository<Note, Long> {
    Optional<Note> findByShareUrl(String shareUrl);
}
