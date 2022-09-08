package com.riyan.note.repositories;


import static org.assertj.core.api.Assertions.assertThat;

import com.riyan.note.models.Note;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Date;

@DataJpaTest
public class NoteRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    NoteRepository repository;

    @Test
    public void should_find_note_if_repository_is_empty() {
        Iterable<Note> tutorials = repository.findAll();
        assertThat(tutorials).isEmpty();
    }

    @Test
    public void should_store_a_notes() {
        Note note = repository.save(new Note
                (1L, "TEST3", "desc", null, "", null, null, new Date(), null, ""));
        assertThat(note).hasFieldOrPropertyWithValue("id", 1L);
        assertThat(note).hasFieldOrPropertyWithValue("title", "TEST3");
        assertThat(note).hasFieldOrPropertyWithValue("description", "desc");
    }

    @Test
    public void should_find_all_notes() {
        Note TEXT_NOTES_TEST = new Note(1,"","",null,"",null,null,new Date(),null,"");
        entityManager.persist(TEXT_NOTES_TEST);
        Note IMAGE_NOTES_TEST = new Note(2,"","",null,"",null,null,new Date(),null,"");
        entityManager.persist(IMAGE_NOTES_TEST);
        Note MULTI_OPTION_NOTES_TEST = new Note(3,"TEST3","",null,"",null,null,new Date(),null,"");
        entityManager.persist(MULTI_OPTION_NOTES_TEST);
        Iterable<Note> tutorials = repository.findAll();
        assertThat(tutorials).hasSize(3).contains(MULTI_OPTION_NOTES_TEST, TEXT_NOTES_TEST, MULTI_OPTION_NOTES_TEST);
    }

    @Test
    public void should_find_note_by_id() {
        Note TEXT_NOTES_TEST = new Note(1,"","",null,"",null,null,new Date(),null,"");
        Note IMAGE_NOTES_TEST = new Note(2,"","",null,"",null,null,new Date(),null,"");
        Note MULTI_OPTION_NOTES_TEST = new Note(3,"TEST3","",null,"",null,null,new Date(),null,"");
        entityManager.persist(TEXT_NOTES_TEST);
        entityManager.persist(IMAGE_NOTES_TEST);
        entityManager.persist(MULTI_OPTION_NOTES_TEST);
        Note foundNote = repository.findById(1l).get();
        assertThat(foundNote).isEqualTo(TEXT_NOTES_TEST);
    }

    @Test
    public void should_find_published_notes() {
        Note TEXT_NOTES_TEST = new Note(1,"","",null,"",null,null,new Date(),null,"");
        entityManager.persist(TEXT_NOTES_TEST);
        Note IMAGE_NOTES_TEST = new Note(2,"","",null,"",null,null,new Date(),null,"RIYAN");
        entityManager.persist(IMAGE_NOTES_TEST);
        Note MULTI_OPTION_NOTES_TEST = new Note(3,"TEST3","",null,"",null,null,new Date(),null,"riyan");
        entityManager.persist(MULTI_OPTION_NOTES_TEST);
        Iterable tutorials = repository.findNotesByUser("riyan");
        assertThat(tutorials).hasSize(2).contains(IMAGE_NOTES_TEST, MULTI_OPTION_NOTES_TEST);
    }
}
