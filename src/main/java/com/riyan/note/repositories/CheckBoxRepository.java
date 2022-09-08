package com.riyan.note.repositories;

import com.riyan.note.models.CheckBox;
import com.riyan.note.models.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckBoxRepository extends JpaRepository<CheckBox, Long> {
}
