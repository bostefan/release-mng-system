package com.holycode.neon;

import com.holycode.neon.exceptions.ReleaseNotFoundException;
import com.holycode.neon.models.ReleaseDTO;
import com.holycode.neon.models.ReleaseSearchFilter;
import com.holycode.neon.models.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/releases")
public class ReleaseController {

    private static Logger log = LoggerFactory.getLogger(ReleaseService.class);

    @Autowired
    private ReleaseService releaseService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ReleaseDTO> list(@ModelAttribute ReleaseSearchFilter releaseSearchFilter){
        return releaseService.getReleases(releaseSearchFilter);
//        return releaseService.getAllReleases();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getRelease(@PathVariable String id){
        try {
            return new ResponseEntity<>(releaseService.getRelease(id), HttpStatus.OK);
        } catch (ReleaseNotFoundException e) {
            log.error(e.getLocalizedMessage());
        }
        return new ResponseEntity<>(new Response("Not found", "No release with id " + id), HttpStatus.NOT_FOUND);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@Valid @RequestBody ReleaseDTO release){

        boolean created = releaseService.create(release);
        ResponseEntity<?> result;
        if(created) {
            result = new ResponseEntity<>(new Response("Created", "Successfully created release named " + release.getName()), HttpStatus.OK);
        } else {
            result = new ResponseEntity<>(new Response("Not created", "There was a problem during creation of release named " + release.getName()), HttpStatus.NOT_FOUND);
        }
        return result;

    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@Valid @RequestBody ReleaseDTO release){
        boolean updated = releaseService.update(release);
        ResponseEntity<?> result;
        if(updated) {
            result = new ResponseEntity<>(new Response("Updated", "Successfully updated release " + release.getId()), HttpStatus.OK);
        } else {
            result = new ResponseEntity<>(new Response("Not updated", "There was a problem during update of release " + release.getId()), HttpStatus.BAD_REQUEST);
        }
        return result;
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> delete(@PathVariable String id){
        boolean deleted = releaseService.delete(id);
        ResponseEntity<?> result;
        if(deleted) {
            result = new ResponseEntity<>(new Response("Deleted", "Successfully deleted release " + id), HttpStatus.ACCEPTED);
        } else {
            result = new ResponseEntity<>(new Response("Not deleted", "There was a problem during deletion of release " + id), HttpStatus.NOT_FOUND);
        }
        return result;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    public ReleaseService getReleaseService() {
        return releaseService;
    }

    public void setReleaseService(ReleaseService releaseService) {
        this.releaseService = releaseService;
    }
}
