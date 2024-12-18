package pl.edu.lab4.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pl.edu.lab4.dto.AnimalDTO;
import pl.edu.lab4.entity.Animal;
import pl.edu.lab4.entity.AnimalCondition;
import pl.edu.lab4.exception.EntityNotFoundException;
import pl.edu.lab4.service.AnimalService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AnimalController.class)
class AnimalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AnimalService animalService;

    @Test
    void testAddAnimal() throws Exception {
        Animal savedAnimal = new Animal("Burek", "Pies", AnimalCondition.ZDROWE, 3, 200.0);
        Mockito.when(animalService.addAnimal(Mockito.any())).thenReturn(savedAnimal);

        mockMvc.perform(post("/api/animal")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                       "name": "Burek",
                       "species": "Pies",
                       "condition": "ZDROWE",
                       "age": 3,
                       "price": 200.0,
                       "shelterId": 1
                    }
                    """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Burek"));
    }

    @Test
    void testGetAnimal_NotFound() throws Exception {
        Mockito.when(animalService.getAnimalById(99L)).thenReturn(null);

        mockMvc.perform(get("/api/animal/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetAnimal() throws Exception {
        Animal animal = new Animal("Misia", "Kot", AnimalCondition.CHORE, 2, 150.0);
        Mockito.when(animalService.getAnimalById(1L)).thenReturn(animal);

        mockMvc.perform(get("/api/animal/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Misia"));
    }
}
