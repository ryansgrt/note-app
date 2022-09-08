package com.riyan.note.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.riyan.note.models.CheckBox;
import com.riyan.note.models.Note;
import com.riyan.note.repositories.CheckBoxRepository;
import com.riyan.note.repositories.NoteRepository;
import com.riyan.note.services.NoteServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.riyan.note.models.NoteType.*;

@Service
public class NoteServicesImpl implements NoteServices {

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    CheckBoxRepository checkBoxRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Override
    public Note saveNote(Note note) {
        return noteRepository.save(note);
    }

    @Override
    public List<Note> getAllNotes() {
        return noteRepository.findAll();
    }

    @Override
    public Optional<Note> getNoteById(long id) {
        return noteRepository.findById(id);
    }

    @Override
    public Note updateNote(Note note) {
        return noteRepository.save(note);
    }

    @Override
    public void deleteNote(long id) {
        noteRepository.deleteById(id);
    }

    @Override
    public Note saveNoteWithImage(String requestBody, MultipartFile file) throws IOException {
        Note note = saveNote(objectMapper.readValue(requestBody, Note.class));

        note.setCreatedAt(new Date());

        if (note.getCheckBoxes() == null && file == null) {
            note.setNoteType(TEXT_NOTES);
        } else if (file != null) {
            note.setImage(Base64.getEncoder().encode(file.getBytes()));
            note.setImageName(file.getOriginalFilename());
            note.setNoteType(IMAGE_NOTES);
        } else if (note.getCheckBoxes() != null) {
            note.setNoteType(MULTI_OPTION_NOTES);
            for (CheckBox checkBox : note.getCheckBoxes()) {
                checkBox.setNote(note);
                checkBox.setDescription(checkBox.getDescription());
                checkBoxRepository.save(checkBox);
            }
        }
        return noteRepository.save(note);
    }

    @Override
    public List<Note> getNoteByUser(String createBy) {
        return noteRepository.findNotesByUser(createBy);
    }
}
