package com.diploma.Backend.rest.service.mapper.impl;

import com.diploma.Backend.rest.dto.InputStreamResourceDTO;
import com.diploma.Backend.rest.service.mapper.IInputStreamResourceMapper;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class InputStreamResourceMapperImpl implements IInputStreamResourceMapper {

    @Override
    public InputStreamResourceDTO reportToInputStreamResourceDTO(InputStreamResource inputStreamResource, String filename) {
        if (inputStreamResource == null) {
            return null;
        } else {
            return InputStreamResourceDTO.builder()
                .fileName(StringUtils.hasText(filename)?filename:"")
                .inputStreamResource(inputStreamResource)
                .build();
        }
    }

}
