package com.egemsoft.rickandmorty.initialization;


import com.egemsoft.rickandmorty.convert.impl.RemoteEpisodesConverter;
import com.egemsoft.rickandmorty.entity.Episode;
import com.egemsoft.rickandmorty.model.response.GetAllEpisode;
import com.egemsoft.rickandmorty.repository.EpisodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {

    private final EpisodeRepository episodeRepository;

    @Transactional
    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        insertDate();
    }

    private void insertDate() {
        RestTemplate restTemplate = new RestTemplate();
        String uri = "https://rickandmortyapi.com/api/episode";
        List<GetAllEpisode> remoteEpisodes = Arrays.asList(restTemplate.getForObject(uri, GetAllEpisode.class));

        RemoteEpisodesConverter remoteEpisodesConverter = new RemoteEpisodesConverter();
        List<Episode> episodes = remoteEpisodesConverter.convert(remoteEpisodes);

        createEpisodes(episodes);
        System.out.println("test");
    }

    private void createEpisodes(List<Episode> episodes) {

        episodeRepository.save(episodes.get(0));
        // Lists.partition(new ArrayList<>(episodes), 1).forEach(episodeRepository::save);
    }
}