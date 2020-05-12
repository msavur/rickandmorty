package com.egemsoft.rickandmorty.repository;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@ContextConfiguration
@Transactional
public abstract class AbstractBaseRepositoryTest {
    public AbstractBaseRepositoryTest() {
    }
}

