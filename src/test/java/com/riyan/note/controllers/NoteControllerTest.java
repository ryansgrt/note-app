package com.riyan.note.controllers;


import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.*;

import com.riyan.note.models.CheckBox;
import com.riyan.note.models.Note;
import com.riyan.note.repositories.NoteRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import com.fasterxml.jackson.databind.ObjectMapper;
@WebMvcTest(NoteController.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class NoteControllerTest {

    @Autowired
    ObjectMapper mapper;

    @MockBean
    NoteRepository noteRepository;

    @Autowired
    private MockMvc mockMvc;



    @Test
    void shouldReturnNotFounNote() throws Exception {
        long id = 1L;
        when(noteRepository.findById(id)).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/note/{id}", id))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void shouldDeleteNote() throws Exception {
        long id = 1L;
        doNothing().when(noteRepository).deleteById(id);
        mockMvc.perform(delete("/api/note/{id}", id))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

}
