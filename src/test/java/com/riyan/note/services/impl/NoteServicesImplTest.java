package com.riyan.note.services.impl;

import com.riyan.note.models.CheckBox;

import static com.riyan.note.models.NoteType.TEXT_NOTES;
import static org.assertj.core.api.Assertions.assertThat;

import com.riyan.note.models.Note;
import com.riyan.note.repositories.NoteRepository;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.*;

@ExtendWith(MockitoExtension.class)
public class NoteServicesImplTest {


    @Mock
    NoteRepository noteRepository;

    @InjectMocks
    private NoteServicesImpl noteServices;

    private Note note;

    private CheckBox checkBox;


    @BeforeEach
    public void setup() {
        //NoteRepository = Mockito.mock(NoteRepository.class);
        //NoteService = new NoteServiceImpl(noteRepository);
        checkBox = CheckBox.builder().id(1L).description("note").note(note).build();
        Set<CheckBox> checkBoxes = new HashSet<CheckBox>();
        checkBoxes.add(checkBox);


        note = Note.builder()
                .id(1L)
                .title("title")
                .description("description note")
                .createdAt(new Date())
                .noteType(TEXT_NOTES)
                .imageName("image name")
                .updatedAt(new Date())
                .checkBoxes(checkBoxes)
                .createdBy("riyan")
                .image("image".getBytes())
                .build();
    }


    @AfterEach
    public void destroyAll() {
        noteRepository.deleteAll();
    }

    @Test
    public void findAll_success() {
        List<Note> allNotes = noteRepository.findAll();
        assertThat(allNotes.size()).isGreaterThanOrEqualTo(0);
    }

    @DisplayName("JUnit test for deleteNote method")
    @Test
    public void givenNoteId_whenDeleteNote_thenNothing() {
        // given - precondition or setup
        long noteId = 1L;
        willDoNothing().given(noteRepository).deleteById(noteId);
        // when -  action or the behaviour that we are going test
        noteServices.deleteNote(noteId);
        // then - verify the output
        verify(noteRepository, times(1)).deleteById(noteId);
    }

    @DisplayName("JUnit test for getAllNotes method")
    @Test
    public void givenNotesList_whenGetAllNotes_thenReturnNotesList() {
        // given - precondition or setup

        Note note1 = Note.builder()
                .id(2L)
                .title("title")
                .description("description note")
                .createdBy("riyan")
                .build();

        given(noteRepository.findAll()).willReturn(Collections.unmodifiableList(Arrays.asList(note, note1)));


        // when -  action or the behaviour that we are going test
        List<Note> noteList = noteServices.getAllNotes();

        // then - verify the output
        assertThat(noteList).isNotNull();
        assertThat(noteList.size()).isEqualTo(2);
    }

    @DisplayName("JUnit test for getAllNotes method")
    @Test
    public void givenNotesList_whenGetAllByUser_thenReturnNoteUser() {
        // given - precondition or setup

        Note note1 = Note.builder()
                .id(2L)
                .title("title")
                .description("description note")
                .createdBy("riyan")
                .build();

        given(noteRepository.findNotesByUser("riyan")).willReturn(Collections.unmodifiableList(Arrays.asList(note, note1)));


        // when -  action or the behaviour that we are going test
        List<Note> noteList = noteServices.getNoteByUser("riyan");

        // then - verify the output
        assertThat(noteList).isNotNull();
        assertThat(noteList.size()).isEqualTo(2);
    }

    @DisplayName("JUnit test for getNoteById method")
    @Test
    public void givenNoteId_whenGetNoteById_thenReturnNoteObject() {
        // given
        given(noteRepository.findById(1L)).willReturn(Optional.of(note));

        // when
        Note savedNote = noteServices.getNoteById(note.getId()).get();

        // then
        assertThat(savedNote).isNotNull();

    }

    @DisplayName("JUnit test for updateNote method")
    @Test
    public void givenNoteObject_whenUpdateNote_thenReturnUpdatedNote() {
        // given - precondition or setup
        given(noteRepository.save(note)).willReturn(note);
        note.setTitle("title edit");
        note.setDescription("description edit");
        // when -  action or the behaviour that we are going test
        Note updatedNote = noteServices.updateNote(note);

        // then - verify the output
        assertThat(updatedNote.getTitle()).isEqualTo("title edit");
        assertThat(updatedNote.getDescription()).isEqualTo("description edit");
    }

    @DisplayName("JUnit test for saveNote method")
    @Test
    public void givenNoteObject_whenSaveNote_thenReturnEmployeeObject(){
        // given - precondition or setup

        given(noteRepository.save(note)).willReturn(note);


        // when -  action or the behaviour that we are going test
        Note savedEmployee = noteServices.saveNote(note);

        // then - verify the output
        assertThat(savedEmployee).isNotNull();
    }
}
