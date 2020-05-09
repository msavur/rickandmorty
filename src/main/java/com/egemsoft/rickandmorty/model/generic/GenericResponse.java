package com.egemsoft.rickandmorty.model.generic;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenericResponse<T> implements Serializable {

    private static final long serialVersionUID = -6788499702493166125L;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private transient PageableInfo info;

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private transient T result;

    public static <T> GenericResponse<T> empty() {
        return new GenericResponse<>();
    }

    public GenericResponse(T result) {
        this.result = result;
    }

}
