package com.riyan.note.controllers;

import com.riyan.note.models.Note;
import com.riyan.note.repositories.NoteRepository;
import com.riyan.note.services.NoteServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/note")
public class NoteController {
    @Autowired
    NoteServices noteServices;

    @Autowired
    NoteRepository noteRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createNote(@RequestParam(value = "file", required = false) MultipartFile file, @RequestParam String formData) throws IOException {
        noteServices.saveNoteWithImage(formData, file);
        return ResponseEntity.status(HttpStatus.CREATED).body("note is created successfully");
    }

    @GetMapping
    public List<Note> getNotes() {
        return noteServices.getAllNotes();
    }

    @GetMapping("{id}")
    public ResponseEntity<Note> getNote(@PathVariable("id") Long noteId) {
        return noteServices.getNoteById(noteId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("user/{username}")
    public List<Note> getNoteByUser(@PathVariable("username") String username) {
        return noteServices.getNoteByUser(username);
    }

    @PutMapping("{id}")
    public ResponseEntity<Note> updateNote(@PathVariable("id") long noteId,
                                           @RequestBody Note noteRequest) {
        return noteServices.getNoteById(noteId)
                .map(savedNote -> {

                    savedNote.setTitle(noteRequest.getTitle());
                    savedNote.setDescription(noteRequest.getDescription());
                    savedNote.setNoteType(noteRequest.getNoteType());
                    savedNote.setCheckBoxes(noteRequest.getCheckBoxes());
                    savedNote.setImage(noteRequest.getImage());
                    savedNote.setUpdatedAt(new Date());
                    Note updateNote = noteServices.updateNote(savedNote);
                    return new ResponseEntity<>(updateNote, HttpStatus.OK);

                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteNote(@PathVariable("id") long noteId) {
        noteServices.deleteNote(noteId);
        return new ResponseEntity<>("Note deleted successfully!.", HttpStatus.OK);
    }
}
