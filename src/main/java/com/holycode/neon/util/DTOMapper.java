package com.holycode.neon.util;

import com.holycode.neon.models.Release;
import com.holycode.neon.models.ReleaseDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DTOMapper {

    @Autowired
    ModelMapper modelMapper;

    public List<ReleaseDTO> mapAllToDTO(List<Release> releases){
        return releases.stream()
                .map(entity -> modelMapper.map(entity, ReleaseDTO.class))
                .collect(Collectors.toList());
    }

    public List<Release> mapAllToEntity(List<ReleaseDTO> releases){
        return releases.stream()
                .map(dto -> modelMapper.map(dto, Release.class))
                .collect(Collectors.toList());
    }

    public ReleaseDTO mapToDTO(Release release){
        return modelMapper.map(release, ReleaseDTO.class);
    }

    public Release mapToEntity(ReleaseDTO release){
        return modelMapper.map(release, Release.class);
    }
}
