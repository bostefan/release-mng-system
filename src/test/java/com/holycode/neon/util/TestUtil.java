package com.holycode.neon.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.holycode.neon.models.ReleaseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class TestUtil {

    private static Logger log = LoggerFactory.getLogger(TestUtil.class);

    public static List<ReleaseDTO> filterByStatus(List<ReleaseDTO> releases, String... status) {
        return releases.stream().filter(release -> {
            for (String s : status) {
                if (release.getStatus().equals(s)) {
                    return true;
                }
            }
            return false;
        }).collect(Collectors.toList());
    }

    public static List<ReleaseDTO> filterCreatedAtAfter(List<ReleaseDTO> releases, Date createdAt) {
        return releases.stream().filter(release -> release.getCreatedAt().after(createdAt)).collect(Collectors.toList());
    }

    public static List<ReleaseDTO> filterReleasedAtAfter(List<ReleaseDTO> releases, Date releasedAtAfter) {
        return releases.stream().filter(release -> release.getReleaseDate().after(releasedAtAfter)).collect(Collectors.toList());
    }

    public static List<ReleaseDTO> filterReleasedAtAfterAndStatusIn(List<ReleaseDTO> releases, Date releasedAtAfter, String... status) {
        return filterByStatus(filterReleasedAtAfter(releases, releasedAtAfter), status);
    }

    public static List<ReleaseDTO> loadTestReleaseDTOs() {
        List<ReleaseDTO> releases = new ArrayList<>();
        String rawJson;
        try {
            rawJson = new String(Files.readAllBytes(Paths.get(TestUtil.class.getResource("/TestReleases.json").toURI())));
            releases = new ObjectMapper().readValue(rawJson, new TypeReference<List<ReleaseDTO>>(){});
        } catch (URISyntaxException | IOException e) {
            log.error(e.getLocalizedMessage());
        }

        return releases;
    }

    public static String convertToJson(Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Date convertFromString(String dateString){
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date;
        try {
            date = formatter.parse(dateString);
        } catch (ParseException e) {
            throw new RuntimeException("Error while parsing date string");
        }
        return date;
    }

}
