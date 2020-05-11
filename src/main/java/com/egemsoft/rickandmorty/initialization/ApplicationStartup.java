package com.egemsoft.rickandmorty.initialization;


import com.egemsoft.rickandmorty.initialization.batchservice.CharacterBatchService;
import com.egemsoft.rickandmorty.initialization.batchservice.CharacterTypeBatchService;
import com.egemsoft.rickandmorty.initialization.batchservice.EpisodeBatchService;
import com.egemsoft.rickandmorty.initialization.batchservice.ImageBatchService;
import com.egemsoft.rickandmorty.initialization.batchservice.KindBatchService;
import com.egemsoft.rickandmorty.initialization.batchservice.LocationBatchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {

    private final CharacterBatchService characterBatchService;
    private final EpisodeBatchService episodeBatchService;
    private final ImageBatchService imageBatchService;
    private final KindBatchService kindBatchService;
    private final LocationBatchService locationBatchService;
    private final CharacterTypeBatchService characterTypeBatchService;

    @Transactional
    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
      //  episodeBatchService.execute();
       // kindBatchService.execute();
        characterTypeBatchService.execute();
        characterBatchService.execute();
       // imageBatchService.execute();
       // locationBatchService.execute();
    }
}