package pl.edu.lab4.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import pl.edu.lab4.entity.Animal;
import pl.edu.lab4.entity.AnimalCondition;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test") // jeśli masz application-test.properties
class AnimalDAOTest {

    @Autowired
    private AnimalDAO animalDAO;

    @Test
    void testSaveAndFindById() {
        Animal a = new Animal("Burek", "Pies", AnimalCondition.ZDROWE, 3, 200.0);
        animalDAO.save(a);
        assertNotNull(a); // ID powinno być nadane
        Animal fromDb = animalDAO.findById(a.getId());
        assertEquals("Burek", fromDb.getName());
    }

    @Test
    void testFindAll() {
        List<Animal> animals = animalDAO.findAll();
        // Sprawdź czy zwraca jakąś listę
    }
}
