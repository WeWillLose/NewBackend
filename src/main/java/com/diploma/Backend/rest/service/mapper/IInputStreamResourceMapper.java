package com.diploma.Backend.rest.service.mapper;

import com.diploma.Backend.rest.dto.InputStreamResourceDTO;
import org.springframework.core.io.InputStreamResource;

public interface IInputStreamResourceMapper {
    InputStreamResourceDTO reportToInputStreamResourceDTO(InputStreamResource inputStreamResource, String filename);
}
