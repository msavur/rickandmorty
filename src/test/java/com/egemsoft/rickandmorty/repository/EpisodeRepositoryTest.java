package com.egemsoft.rickandmorty.repository;

import com.egemsoft.core.entity.Episode;
import com.egemsoft.rickandmorty.RickMortyApplication;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;

@SpringBootTest(classes = RickMortyApplication.class)
public class EpisodeRepositoryTest extends AbstractBaseRepositoryTest {

    @Autowired
    private EpisodeRepository episodeRepository;

    @Test
    public void getAllEpisode() {
        List<Episode> characters = episodeRepository.findAll();
        assertNotNull(characters);
    }

    @Test
    public void getEpisode() {
        Optional<Episode> optionalEpisode = episodeRepository.findById(1L);
        assertNotNull(optionalEpisode.get());
    }
}
