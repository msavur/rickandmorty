package com.egemsoft.rickandmorty.convert.impl.endpoint;

public final class ApiEndpoint {

    public static final String BASE_URL = "https://rickandmortyapi.com/api";

    public static final String CHARACTER_URL = BASE_URL + "/character";

    public static final String EPISODE_URL = BASE_URL + "/episode";

    public static final String REPORT_ENDPOINT_URL = BASE_URL + "/report";

    public static final String PAGEABLE_EPISODE_URL = EPISODE_URL + "?page=";

    public static final String PAGEABLE_CHARACTER_URL = CHARACTER_URL + "?page=";

    public static final String PAGEABLE_REPORT_ENDPOINT_URL = REPORT_ENDPOINT_URL + "?page=";


    private ApiEndpoint() {
    }
}
