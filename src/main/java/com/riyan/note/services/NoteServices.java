package com.riyan.note.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.riyan.note.models.Note;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface NoteServices {
    Note saveNote(Note note);

    List<Note> getAllNotes();

    Optional<Note> getNoteById(long id);

    Note updateNote(Note note);

    void deleteNote(long id);

    Note saveNoteWithImage(String note, MultipartFile file) throws IOException;

    List<Note> getNoteByUser(String createBy);
}
