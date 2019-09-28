package com.holycode.neon;

import com.holycode.neon.controller.ReleaseController;
import com.holycode.neon.models.ReleaseDTO;
import com.holycode.neon.models.ReleaseSearchFilter;
import com.holycode.neon.models.ReleaseStatus;
import com.holycode.neon.repository.ReleaseRepository;
import com.holycode.neon.service.ReleaseService;
import com.holycode.neon.util.TestUtil;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ReleaseController.class)
@AutoConfigureMockMvc
public class ReleaseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReleaseService releaseService;

    @MockBean
    private ReleaseRepository releaseRepo;

    private List<ReleaseDTO> testData;

    @Before
    public void setUp() {
        testData = TestUtil.loadTestReleaseDTOs();
    }

    @Test
    public void root_KO() throws Exception {
        this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    public void allReleases_OK() throws Exception {
        Mockito.when(this.releaseService.getReleases(any(ReleaseSearchFilter.class))).thenReturn(testData);
        this.mockMvc.perform(
                get("/releases"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(testData.size()));

        verify(releaseService, times(1)).getReleases(any(ReleaseSearchFilter.class));
        verifyNoMoreInteractions(releaseService);
    }

    @Test
    public void allReleasesWithSlash_OK() throws Exception {
        Mockito.when(this.releaseService.getReleases(any(ReleaseSearchFilter.class))).thenReturn(testData);
        this.mockMvc.perform(
                get("/releases/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(testData.size()));
        verify(releaseService, times(1)).getReleases(any(ReleaseSearchFilter.class));
        verifyNoMoreInteractions(releaseService);
    }

    @Test
    public void allReleasesWithStatusCreated_OK() throws Exception {
        List<ReleaseDTO> createdOnly = TestUtil.filterByStatus(testData, "Created");
        ReleaseSearchFilter searchFilterCreated = new ReleaseSearchFilter(Lists.list("Created"));
        Mockito.when(releaseService.getReleases(eq(searchFilterCreated))).thenReturn(createdOnly);
        this.mockMvc.perform(
                get("/releases")
                        .param("statusIn", "Created"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(createdOnly.size()));
        verify(releaseService, times(1)).getReleases(eq(searchFilterCreated));
        verifyNoMoreInteractions(releaseService);
    }

    @Test
    public void allReleasesCreatedAfter_OK() throws Exception {
        String testDateString = "2014-01-01";
        Date testDate = TestUtil.convertFromString(testDateString);
        List<ReleaseDTO> createdAfter = TestUtil.filterCreatedAtAfter(testData, testDate);
        ReleaseSearchFilter searchFilterCreated = new ReleaseSearchFilter();
        searchFilterCreated.setCreatedAtFrom(testDate);
        Mockito.when(releaseService.getReleases(eq(searchFilterCreated))).thenReturn(createdAfter);
        this.mockMvc.perform(
                get("/releases")
                        .param("createdAtFrom", testDateString))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(createdAfter.size()));
        verify(releaseService, times(1)).getReleases(eq(searchFilterCreated));
        verifyNoMoreInteractions(releaseService);
    }

    @Test
    public void allReleasesReleasedAfter_OK() throws Exception {
        String testDateString = "2016-01-01";
        Date testDate = TestUtil.convertFromString(testDateString);
        List<ReleaseDTO> releasedAfter = TestUtil.filterReleasedAtAfter(testData, testDate);
        ReleaseSearchFilter searchFilterCreated = new ReleaseSearchFilter();
        searchFilterCreated.setReleaseDateFrom(testDate);
        Mockito.when(releaseService.getReleases(eq(searchFilterCreated))).thenReturn(releasedAfter);
        this.mockMvc.perform(
                get("/releases")
                        .param("releaseDateFrom", testDateString))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(releasedAfter.size()));
        verify(releaseService, times(1)).getReleases(eq(searchFilterCreated));
        verifyNoMoreInteractions(releaseService);
    }

    @Test
    public void allReleasesReleasedAfterAnInStatuses_OK() throws Exception {
        String testDateString = "2016-01-01";
        Date testDate = TestUtil.convertFromString(testDateString);
        List<ReleaseDTO> releasedAfterInStatus = TestUtil.filterReleasedAtAfterAndStatusIn(testData, testDate, "In PROD", "On DEV");
        ReleaseSearchFilter searchFilterCreated = new ReleaseSearchFilter(Lists.list("In PROD", "On DEV"));
        searchFilterCreated.setReleaseDateFrom(testDate);
        Mockito.when(releaseService.getReleases(eq(searchFilterCreated))).thenReturn(releasedAfterInStatus);
        this.mockMvc.perform(
                get("/releases")
                        .param("releaseDateFrom", testDateString)
                        .param("statusIn","In PROD", "On DEV"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(releasedAfterInStatus.size()));
        verify(releaseService, times(1)).getReleases(eq(searchFilterCreated));
        verifyNoMoreInteractions(releaseService);
    }

    @Test
    public void addNewRelease_OK() throws Exception {
        ReleaseDTO newRelease = new ReleaseDTO(1000L, "New Release", "New Release description", ReleaseStatus.CREATED.displayName(),
                null, new Date(), null);
        Mockito.when(releaseService.create(any(ReleaseDTO.class))).thenReturn(true);
        this.mockMvc.perform(
                post("/releases")
                        .content(TestUtil.convertToJson(
                                newRelease))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(ReleaseStatus.CREATED.displayName()));
        verify(releaseService, times(1)).create(any(ReleaseDTO.class));
        verifyNoMoreInteractions(releaseService);
    }

    @Test
    public void addNewReleaseBadMediaType_KO() throws Exception {
        ReleaseDTO releaseWithBadMediaType = new ReleaseDTO(1000L, "New Release", "New Release description", ReleaseStatus.CREATED.displayName(),
                null, new Date(), null);
        this.mockMvc.perform(
                post("/releases")
                        .content(TestUtil.convertToJson(
                                releaseWithBadMediaType))
                        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isUnsupportedMediaType());
        verify(releaseService, times(0)).create(any(ReleaseDTO.class));
    }

    @Test
    public void addNewReleaseBadStatus_KO() throws Exception {
        ReleaseDTO releaseWithBadStatus = new ReleaseDTO(1000L, "New Release", "New Release description", "NEW",
                null, new Date(), null);
        this.mockMvc.perform(
                post("/releases")
                        .content(TestUtil.convertToJson(
                                releaseWithBadStatus))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value("Validation failed"));
        verify(releaseService, times(0)).create(any(ReleaseDTO.class));
    }

    @Test
    public void addNewReleaseBadCreateDate_KO() throws Exception {
        ReleaseDTO releaseWithBadCreateDate = new ReleaseDTO(1000L, "New Release", "New Release description", ReleaseStatus.CREATED.displayName(),
                null, null, null);
        this.mockMvc.perform(
                post("/releases")
                        .content(TestUtil.convertToJson(
                                releaseWithBadCreateDate))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value("Validation failed"));
        verify(releaseService, times(0)).create(any(ReleaseDTO.class));
    }

    @Test
    public void addNewReleaseNoName_KO() throws Exception {
        ReleaseDTO releaseWithNoName = new ReleaseDTO(1000L, "", "New Release description", ReleaseStatus.CREATED.displayName(),
                null, null, null);
        this.mockMvc.perform(
                post("/releases")
                        .content(TestUtil.convertToJson(
                                releaseWithNoName))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value("Validation failed"));
        verify(releaseService, times(0)).create(any(ReleaseDTO.class));
    }

    @Test
    public void updateReleaseName_OK() throws Exception {
        ReleaseDTO releaseNameChanged = new ReleaseDTO(1000L, "Release Name Changed", "New Release description", ReleaseStatus.CREATED.displayName(),
                null, new Date(), null);
        Mockito.when(releaseService.update(any(ReleaseDTO.class))).thenReturn(true);
        this.mockMvc.perform(
                put("/releases/{id}", 1)
                        .content(TestUtil.convertToJson(
                                releaseNameChanged))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("Updated"));
        verify(releaseService, times(1)).update(any(ReleaseDTO.class));
        verifyNoMoreInteractions(releaseService);
    }

    @Test
    public void updateReleaseNameBadMediaType_KO() throws Exception {
        ReleaseDTO releaseNameChanged = new ReleaseDTO(1000L, "New Release", "New Release description", ReleaseStatus.CREATED.displayName(),
                null, new Date(), null);
        this.mockMvc.perform(
                put("/releases/{id}", 1)
                        .content(TestUtil.convertToJson(
                                releaseNameChanged))
                        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isUnsupportedMediaType());
        verify(releaseService, times(0)).update(any(ReleaseDTO.class));
    }

    @Test
    public void updateReleaseBadName_KO() throws Exception {
        ReleaseDTO releaseWithBadName = new ReleaseDTO(1000L, "", "New Release description", ReleaseStatus.CREATED.displayName(),
                new Date(), new Date(), new Date());
        this.mockMvc.perform(
                put("/releases/{id}", 1)
                        .content(TestUtil.convertToJson(
                                releaseWithBadName))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value("Validation failed"));
        verify(releaseService, times(0)).update(any(ReleaseDTO.class));
    }

    @Test
    public void deleteRelease_OK() throws Exception {
        Mockito.when(releaseService.delete(anyString())).thenReturn(true);
        this.mockMvc.perform(
                delete("/releases/{id}", 1))
                .andDo(print())
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.status").value("Deleted"));
        verify(releaseService, times(1)).delete(anyString());
        verifyNoMoreInteractions(releaseService);
    }

    @Test
    public void deleteReleaseNotExisting_OK() throws Exception {
        Mockito.when(releaseService.delete(anyString())).thenReturn(false);
        this.mockMvc.perform(
                delete("/releases/{id}", 1))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value("Not deleted"));
        verify(releaseService, times(1)).delete(anyString());
        verifyNoMoreInteractions(releaseService);
    }

}
