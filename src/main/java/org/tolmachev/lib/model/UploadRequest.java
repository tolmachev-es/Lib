package org.tolmachev.lib.model;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
public class UploadRequest {
    @Valid
    private List<Data> data;
}
