package com.egemsoft.rickandmorty.initialization.common;


import com.egemsoft.rickandmorty.convert.impl.endpoint.ApiEndpoint;
import com.egemsoft.rickandmorty.model.dto.CharacterDto;
import com.egemsoft.rickandmorty.model.response.GetAllCharacter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class CommonRestRequest {

    private final static int COUNT_PAGE = 2;

    public static List<CharacterDto> getAllCharacter() {
        RestTemplate restTemplate = new RestTemplate();
        List<CharacterDto> remoteCharacterDtos = new ArrayList<>();
        GetAllCharacter getAllCharacter = null;
        try {
            getAllCharacter = restTemplate.getForObject(ApiEndpoint.PAGEABLE_CHARACTER_URL + 1, GetAllCharacter.class);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return remoteCharacterDtos;
        }
        int pageSize = getAllCharacter.getInfo().getPages();
        remoteCharacterDtos.addAll(getAllCharacter.getResults());
        recursionCharacter(pageSize, COUNT_PAGE, remoteCharacterDtos);

        return remoteCharacterDtos;
    }

    public static int recursionCharacter(int pageSize, int pageCount, List<CharacterDto> remoteCharacterDtos) {
        RestTemplate restTemplate = new RestTemplate();
        if (pageSize >= pageCount) {
            GetAllCharacter getAllCharacter = restTemplate.getForObject(ApiEndpoint.PAGEABLE_CHARACTER_URL + pageCount, GetAllCharacter.class);
            remoteCharacterDtos.addAll(getAllCharacter.getResults());
            return recursionCharacter(pageSize, pageCount + 1, remoteCharacterDtos);
        }
        return pageSize;
    }
}