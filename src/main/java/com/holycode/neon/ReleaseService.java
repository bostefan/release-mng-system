package com.holycode.neon;

import com.holycode.neon.exceptions.ReleaseNotFoundException;
import com.holycode.neon.models.Release;
import com.holycode.neon.models.ReleaseDTO;
import com.holycode.neon.util.DTOMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReleaseService {

    private static Logger log = LoggerFactory.getLogger(ReleaseService.class);

    @Autowired
    private ReleaseRepository releaseRepo;

    @Autowired
    private DTOMapper dtoMapper;

    public List<ReleaseDTO> getReleases() {
        return dtoMapper.mapAllToDTO(releaseRepo.findAll());
    }

    public ReleaseDTO getRelease(String id) throws ReleaseNotFoundException {
        long lid = Long.valueOf(id);
        Release release = releaseRepo.findById(lid).orElseThrow(() -> new ReleaseNotFoundException());
        return dtoMapper.mapToDTO(release);
    }

    public boolean create(ReleaseDTO releaseDTO){
        Release release = releaseRepo.save(dtoMapper.mapToEntity(releaseDTO));
        return release != null;
    }

    public boolean update(ReleaseDTO releaseDTO){
        Release release = releaseRepo.save(dtoMapper.mapToEntity(releaseDTO));
        return release != null;
    }

    public boolean delete(String id){
        boolean deleted = false;
        try {
            long lid = Long.valueOf(id);
            Release release = releaseRepo.findById(lid).orElseThrow(() -> new ReleaseNotFoundException());
            releaseRepo.delete(release);
            deleted = true;
        } catch (Exception e) {
            log.error("Could not remove entity: " + e.getLocalizedMessage());
        }
        return deleted;
    }

    public ReleaseRepository getReleaseRepo() {
        return releaseRepo;
    }

    public void setReleaseRepo(ReleaseRepository releaseRepo) {
        this.releaseRepo = releaseRepo;
    }

}
