package com.kf.touchbase.web.rest;


import com.kf.touchbase.domain.Mission;
import com.kf.touchbase.repository.MissionRepository;

import io.micronaut.context.annotation.Property;
import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.test.annotation.MicronautTest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import javax.inject.Inject;

import java.util.List;

import com.kf.touchbase.service.dto.MissionDTO;
import com.kf.touchbase.service.mapper.MissionMapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;


import com.kf.touchbase.domain.enumeration.MissionType;
/**
 * Integration tests for the {@Link MissionResource} REST controller.
 */
@MicronautTest(transactional = false)
@Property(name = "micronaut.security.enabled", value = "false")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MissionResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_SCORE_REWARD = "AAAAAAAAAA";
    private static final String UPDATED_SCORE_REWARD = "BBBBBBBBBB";

    private static final MissionType DEFAULT_MISSON_TYPE = MissionType.PERIODIC;
    private static final MissionType UPDATED_MISSON_TYPE = MissionType.WEEKLY;

    @Inject
    private MissionMapper missionMapper;
    @Inject
    private MissionRepository missionRepository;

    @Inject @Client("/")
    RxHttpClient client;

    private Mission mission;

    @BeforeEach
    public void initTest() {
        mission = createEntity();
    }

    @AfterEach
    public void cleanUpTest() {
        missionRepository.deleteAll();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Mission createEntity() {
        Mission mission = new Mission()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .scoreReward(DEFAULT_SCORE_REWARD)
            .missonType(DEFAULT_MISSON_TYPE);
        return mission;
    }


    @Test
    public void createMission() throws Exception {
        int databaseSizeBeforeCreate = missionRepository.findAll().size();

        MissionDTO missionDTO = missionMapper.toDto(mission);

        // Create the Mission
        HttpResponse<MissionDTO> response = client.exchange(HttpRequest.POST("/api/missions", missionDTO), MissionDTO.class).blockingFirst();

        assertThat(response.status().getCode()).isEqualTo(HttpStatus.CREATED.getCode());

        // Validate the Mission in the database
        List<Mission> missionList = missionRepository.findAll();
        assertThat(missionList).hasSize(databaseSizeBeforeCreate + 1);
        Mission testMission = missionList.get(missionList.size() - 1);

        assertThat(testMission.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMission.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testMission.getScoreReward()).isEqualTo(DEFAULT_SCORE_REWARD);
        assertThat(testMission.getMissonType()).isEqualTo(DEFAULT_MISSON_TYPE);
    }

    @Test
    public void createMissionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = missionRepository.findAll().size();

        // Create the Mission with an existing ID
        mission.setId(1L);
        MissionDTO missionDTO = missionMapper.toDto(mission);

        // An entity with an existing ID cannot be created, so this API call must fail
        @SuppressWarnings("unchecked")
        HttpResponse<MissionDTO> response = client.exchange(HttpRequest.POST("/api/missions", missionDTO), MissionDTO.class)
            .onErrorReturn(t -> (HttpResponse<MissionDTO>) ((HttpClientResponseException) t).getResponse()).blockingFirst();

        assertThat(response.status().getCode()).isEqualTo(HttpStatus.BAD_REQUEST.getCode());

        // Validate the Mission in the database
        List<Mission> missionList = missionRepository.findAll();
        assertThat(missionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void getAllMissions() throws Exception {
        // Initialize the database
        missionRepository.saveAndFlush(mission);

        // Get the missionList w/ all the missions
        List<MissionDTO> missions = client.retrieve(HttpRequest.GET("/api/missions?eagerload=true"), Argument.listOf(MissionDTO.class)).blockingFirst();
        MissionDTO testMission = missions.get(0);


        assertThat(testMission.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMission.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testMission.getScoreReward()).isEqualTo(DEFAULT_SCORE_REWARD);
        assertThat(testMission.getMissonType()).isEqualTo(DEFAULT_MISSON_TYPE);
    }

    @Test
    public void getMission() throws Exception {
        // Initialize the database
        missionRepository.saveAndFlush(mission);

        // Get the mission
        MissionDTO testMission = client.retrieve(HttpRequest.GET("/api/missions/" + mission.getId()), MissionDTO.class).blockingFirst();


        assertThat(testMission.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMission.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testMission.getScoreReward()).isEqualTo(DEFAULT_SCORE_REWARD);
        assertThat(testMission.getMissonType()).isEqualTo(DEFAULT_MISSON_TYPE);
    }

    @Test
    public void getNonExistingMission() throws Exception {
        // Get the mission
        @SuppressWarnings("unchecked")
        HttpResponse<MissionDTO> response = client.exchange(HttpRequest.GET("/api/missions/"+ Long.MAX_VALUE), MissionDTO.class)
            .onErrorReturn(t -> (HttpResponse<MissionDTO>) ((HttpClientResponseException) t).getResponse()).blockingFirst();

        assertThat(response.status().getCode()).isEqualTo(HttpStatus.NOT_FOUND.getCode());
    }

    @Test
    public void updateMission() throws Exception {
        // Initialize the database
        missionRepository.saveAndFlush(mission);

        int databaseSizeBeforeUpdate = missionRepository.findAll().size();

        // Update the mission
        Mission updatedMission = missionRepository.findById(mission.getId()).get();

        updatedMission
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .scoreReward(UPDATED_SCORE_REWARD)
            .missonType(UPDATED_MISSON_TYPE);
        MissionDTO updatedMissionDTO = missionMapper.toDto(updatedMission);

        @SuppressWarnings("unchecked")
        HttpResponse<MissionDTO> response = client.exchange(HttpRequest.PUT("/api/missions", updatedMissionDTO), MissionDTO.class)
            .onErrorReturn(t -> (HttpResponse<MissionDTO>) ((HttpClientResponseException) t).getResponse()).blockingFirst();

        assertThat(response.status().getCode()).isEqualTo(HttpStatus.OK.getCode());

        // Validate the Mission in the database
        List<Mission> missionList = missionRepository.findAll();
        assertThat(missionList).hasSize(databaseSizeBeforeUpdate);
        Mission testMission = missionList.get(missionList.size() - 1);

        assertThat(testMission.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMission.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testMission.getScoreReward()).isEqualTo(UPDATED_SCORE_REWARD);
        assertThat(testMission.getMissonType()).isEqualTo(UPDATED_MISSON_TYPE);
    }

    @Test
    public void updateNonExistingMission() throws Exception {
        int databaseSizeBeforeUpdate = missionRepository.findAll().size();

        // Create the Mission
        MissionDTO missionDTO = missionMapper.toDto(mission);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        @SuppressWarnings("unchecked")
        HttpResponse<MissionDTO> response = client.exchange(HttpRequest.PUT("/api/missions", missionDTO), MissionDTO.class)
            .onErrorReturn(t -> (HttpResponse<MissionDTO>) ((HttpClientResponseException) t).getResponse()).blockingFirst();

        assertThat(response.status().getCode()).isEqualTo(HttpStatus.BAD_REQUEST.getCode());

        // Validate the Mission in the database
        List<Mission> missionList = missionRepository.findAll();
        assertThat(missionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteMission() throws Exception {
        // Initialize the database with one entity
        missionRepository.saveAndFlush(mission);

        int databaseSizeBeforeDelete = missionRepository.findAll().size();

        // Delete the mission
        @SuppressWarnings("unchecked")
        HttpResponse<MissionDTO> response = client.exchange(HttpRequest.DELETE("/api/missions/"+ mission.getId()), MissionDTO.class)
            .onErrorReturn(t -> (HttpResponse<MissionDTO>) ((HttpClientResponseException) t).getResponse()).blockingFirst();

        assertThat(response.status().getCode()).isEqualTo(HttpStatus.NO_CONTENT.getCode());

            // Validate the database is now empty
        List<Mission> missionList = missionRepository.findAll();
        assertThat(missionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Mission.class);
        Mission mission1 = new Mission();
        mission1.setId(1L);
        Mission mission2 = new Mission();
        mission2.setId(mission1.getId());
        assertThat(mission1).isEqualTo(mission2);
        mission2.setId(2L);
        assertThat(mission1).isNotEqualTo(mission2);
        mission1.setId(null);
        assertThat(mission1).isNotEqualTo(mission2);
    }
}
