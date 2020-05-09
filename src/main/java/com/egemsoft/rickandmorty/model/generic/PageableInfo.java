package com.egemsoft.rickandmorty.model.generic;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class PageableInfo implements Serializable {

    private static final long serialVersionUID = -8692268943799702275L;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private transient String next;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private transient String prev;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private transient Integer pages;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private transient Long count;

}
