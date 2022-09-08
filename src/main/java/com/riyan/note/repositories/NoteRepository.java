package com.riyan.note.repositories;

import com.riyan.note.models.Note;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

    @Query("SELECT n FROM Note n WHERE LOWER(n.createdBy)=LOWER (?1)")
    List<Note> findNotesByUser(String createdBy);
}
