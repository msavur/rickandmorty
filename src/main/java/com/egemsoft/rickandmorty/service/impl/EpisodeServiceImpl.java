package com.egemsoft.rickandmorty.service.impl;

import com.egemsoft.rickandmorty.convert.impl.EpisodeConverter;
import com.egemsoft.rickandmorty.convert.impl.EpisodesConverter;
import com.egemsoft.rickandmorty.convert.impl.endpoint.ApiEndpoint;
import com.egemsoft.rickandmorty.entity.Episode;
import com.egemsoft.rickandmorty.model.generic.GenericResponse;
import com.egemsoft.rickandmorty.model.generic.PageableInfo;
import com.egemsoft.rickandmorty.model.response.EpisodeResponse;
import com.egemsoft.rickandmorty.repository.EpisodeRepository;
import com.egemsoft.rickandmorty.service.EpisodeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class EpisodeServiceImpl implements EpisodeService {

    private final EpisodeRepository episodeRepository;

    @Override
    public GenericResponse<EpisodeResponse> getAllEpisode(Pageable pageable) {
        Page<Episode> episodePage = episodeRepository.findByOrderById(pageable);
        EpisodesConverter episodeConverter = new EpisodesConverter();
        List<EpisodeResponse> response = episodeConverter.convert(episodePage);
        PageableInfo pageableInfo = getPageableInfo(episodePage);
        return new GenericResponse(pageableInfo, response);
    }

    @Override
    public GenericResponse<EpisodeResponse> getEpisode(Long id) {
        Optional<Episode> optionalEpisode = episodeRepository.findById(id);
        if (!optionalEpisode.isPresent()) {
            return GenericResponse.empty();
        }
        EpisodeConverter episodeConverter = new EpisodeConverter();
        EpisodeResponse convert = episodeConverter.convert(optionalEpisode.get());
        return new GenericResponse(convert);
    }

    private PageableInfo getPageableInfo(Page<Episode> episodePage) {
        PageableInfo pageableInfo = new PageableInfo();
        pageableInfo.setCount(episodePage.getTotalElements());
        pageableInfo.setNext(ApiEndpoint.PAGEABLE_EPISODE_URL + episodePage.getNumber() + 1);
        pageableInfo.setPages(episodePage.getSize());
        pageableInfo.setPrev("");
        return pageableInfo;
    }
}
