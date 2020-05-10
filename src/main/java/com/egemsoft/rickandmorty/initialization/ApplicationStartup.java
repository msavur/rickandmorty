package com.egemsoft.rickandmorty.initialization;


import com.egemsoft.rickandmorty.convert.impl.RemoteEpisodesConverter;
import com.egemsoft.rickandmorty.convert.impl.endpoint.ApiEndpoint;
import com.egemsoft.rickandmorty.entity.Episode;
import com.egemsoft.rickandmorty.model.dto.EpisodeDto;
import com.egemsoft.rickandmorty.model.response.GetAllEpisode;
import com.egemsoft.rickandmorty.repository.EpisodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.ArrayList;
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
        List<EpisodeDto> remoteEpisodes = new ArrayList<>();
        GetAllEpisode getAllEpisode = restTemplate.getForObject(ApiEndpoint.PAGEABLE_EPISODE_URL + 1, GetAllEpisode.class);
        int pageSize = getAllEpisode.getInfo().getPages();
        recursionMethod(pageSize,  1,  remoteEpisodes);
        RemoteEpisodesConverter remoteEpisodesConverter = new RemoteEpisodesConverter();
        List<Episode> episodes = remoteEpisodesConverter.convert(remoteEpisodes);

        // createEpisodes(episodes);
        System.out.println("test");
    }

    private void createEpisodes(List<Episode> episodes) {

        episodeRepository.save(episodes.get(0));
        // Lists.partition(new ArrayList<>(episodes), 1).forEach(episodeRepository::save);
    }


    public int recursionMethod(int pageSize, int pageCount, List<EpisodeDto> remoteEpisodes) {
        RestTemplate restTemplate = new RestTemplate();
        if (pageSize == pageCount) {
            return pageSize;
        }
        GetAllEpisode getAllEpisode = restTemplate.getForObject(ApiEndpoint.PAGEABLE_EPISODE_URL + pageCount, GetAllEpisode.class);
        remoteEpisodes.addAll(getAllEpisode.getResults());
        return recursionMethod(pageSize, pageCount + 1, remoteEpisodes);
    }


    public int de(int test = 0){

    }

}