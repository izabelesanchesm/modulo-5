package com.meli.obterdiploma.service;

import com.meli.obterdiploma.exception.StudentNotFoundException;
import com.meli.obterdiploma.model.StudentDTO;
import com.meli.obterdiploma.model.SubjectDTO;
import com.meli.obterdiploma.repository.IStudentDAO;
import com.meli.obterdiploma.repository.IStudentRepository;
import com.meli.obterdiploma.repository.StudentDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ObterDiplomaServiceTest {

    @InjectMocks
    private ObterDiplomaService service;

    @Mock
    private IStudentDAO dao;

    private StudentDTO studentDTO;

    @BeforeEach
    void setup(){
        List<SubjectDTO> list = new ArrayList<>();
        list.add(new SubjectDTO("Biologia", 7.0));
        list.add(new SubjectDTO("Matemática", 9.0));
        list.add(new SubjectDTO("Química", 8.0));
        studentDTO = new StudentDTO(1L,"Estudante 1", null, null, list);
    }

    @Test
    void analyzeScores_throwStudentNotFoundException_whenStudentNotFound() {
        Long idStudentNotFound = 999L;

        BDDMockito.given(dao.findById(ArgumentMatchers.anyLong()))
                .willThrow(new StudentNotFoundException(idStudentNotFound));

        assertThrows(StudentNotFoundException.class, () -> {
           service.analyzeScores(idStudentNotFound);
        });
    }

    @Test
    void analyzeScores_returnNewStudentDTO_whenStudentCorrect(){
        Mockito.when(dao.findById(ArgumentMatchers.anyLong()))
                .thenReturn(studentDTO);

        StudentDTO retornoDTO = service.analyzeScores(studentDTO.getId());

        assertThat(retornoDTO.getStudentName()).isEqualTo(studentDTO.getStudentName());
    }

    @Test
    void analyzeScores_returnAverageCorrect_whenValidValues(){
        Mockito.when(dao.findById(ArgumentMatchers.anyLong()))
                .thenReturn(studentDTO);

        StudentDTO retornoDTO = service.analyzeScores(studentDTO.getId());

        assertThat(retornoDTO.getAverageScore()).isEqualTo(8.0);
    }

    @Test
    void analyzeScores_returnLegendParabens_whenAverageAboveNine(){
        List<SubjectDTO> list = new ArrayList<>();
        list.add(new SubjectDTO("Biologia", 10.0));
        list.add(new SubjectDTO("Matemática", 9.0));
        list.add(new SubjectDTO("Química", 10.0));
        studentDTO = new StudentDTO(1L,"Estudante 1", null, null, list);

        Mockito.when(dao.findById(ArgumentMatchers.anyLong()))
                .thenReturn(studentDTO);

        StudentDTO retornoDTO = service.analyzeScores(studentDTO.getId());

        assertThat(retornoDTO.getMessage()).contains("Parabéns!");
    }

    @Test
    void analyzeScores_returnLegendVocePodeMelhorar_whenAverageEqualBelowNine(){
        List<SubjectDTO> list = new ArrayList<>();
        list.add(new SubjectDTO("Biologia", 0.0));
        list.add(new SubjectDTO("Matemática", 9.0));
        list.add(new SubjectDTO("Química", 10.0));
        studentDTO = new StudentDTO(1L,"Estudante 1", null, null, list);

        Mockito.when(dao.findById(ArgumentMatchers.anyLong()))
                .thenReturn(studentDTO);

        StudentDTO retornoDTO = service.analyzeScores(studentDTO.getId());

        assertThat(retornoDTO.getMessage()).contains("Você pode melhorar.");
    }
}