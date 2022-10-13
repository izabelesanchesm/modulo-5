package com.meli.obterdiploma.service;

import com.meli.obterdiploma.model.StudentDTO;
import com.meli.obterdiploma.model.SubjectDTO;
import com.meli.obterdiploma.repository.IStudentDAO;
import com.meli.obterdiploma.repository.IStudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private IStudentDAO dao;

    @Mock
    private IStudentRepository repository;

    @InjectMocks
    private StudentService service;

    private StudentDTO studentDTO;

    @BeforeEach
    void setup(){
        List<SubjectDTO> listSubject = new ArrayList<>();
        listSubject.add(new SubjectDTO("Matemática",5.0));
        listSubject.add(new SubjectDTO("História",9.0));
        studentDTO = new StudentDTO(null, "Estudante 1", null, null, listSubject);
    }

    @Test
    void create() {
        Mockito.when(dao.save(ArgumentMatchers.any(StudentDTO.class)))
                .thenReturn(null);
    }

    @Test
    void read() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void getAll() {
    }
}