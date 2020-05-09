package com.egemsoft.rickandmorty.convert;

public interface BaseConverter<I, O> {

    O convert(I input);
}
