package com.egemsoft.rickandmorty.repository;

import com.egemsoft.core.entity.Character;
import com.egemsoft.rickandmorty.RickMortyApplication;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.Assert.assertNotNull;

@SpringBootTest(classes = RickMortyApplication.class)
public class CharacterRepositoryTest extends AbstractBaseRepositoryTest {

    @Autowired
    private CharacterRepository characterRepository;

    @Test
    public void getAllCharacter() {
        List<Character> characters = characterRepository.findAll();
        assertNotNull(characters);
    }

    @Test
    public void getSearchCharacter() {
        List<String> characterNames = characterRepository.getAllCharacterName();
        assertNotNull(characterNames);
    }
}
