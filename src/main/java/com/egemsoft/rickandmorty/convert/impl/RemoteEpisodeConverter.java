package com.egemsoft.rickandmorty.convert.impl;


import com.egemsoft.rickandmorty.convert.BaseConverter;
import com.egemsoft.rickandmorty.entity.Character;
import com.egemsoft.rickandmorty.entity.Episode;
import com.egemsoft.rickandmorty.model.dto.EpisodeDto;
import com.egemsoft.rickandmorty.repository.CharacterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RemoteEpisodeConverter implements BaseConverter<List<EpisodeDto>, List<Episode>> {

    private final CharacterRepository characterRepository;

    @Override
    public List<Episode> convert(List<EpisodeDto> remoteEpisodes) {
        Map<Long, Character> characterMap = characterRepository.findAll().stream().collect(Collectors.toMap(Character::getRemoteId, c -> c));
        List<Episode> episodes = new ArrayList<>();
        for (EpisodeDto remote : remoteEpisodes) {
            Episode episode = new Episode();
            episode.setRemoteId(remote.getId());
            episode.setEpisode(remote.getEpisode());
            //episode.setAirDate(DateUtils.convertDate(remote.getAirDate()));
            List<Character> characters = new ArrayList<>();
            getCharacter(characterMap, characters, remote.getCharacters());
            // episode.setCharacters(characters);
            episode.setName(remote.getName());
            episode.setUrl(remote.getUrl());
            episode.setCreated(remote.getCreated());
            episodes.add(episode);
        }
        return episodes;
    }

    private void getCharacter(Map<Long, Character> characterMap, List<Character> characters, List<String> characterList) {
        characterList.forEach(url -> {
            url = url.substring(url.lastIndexOf("/") + 1);
            Character character = characterMap.get(url);
            characters.add(character);
        });
    }
}
