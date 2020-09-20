package com.kf.touchbase.web.rest;

import com.kf.touchbase.security.AuthoritiesConstants;
import com.kf.touchbase.service.MissionService;
import com.kf.touchbase.service.dto.MissionDTO;
import com.kf.touchbase.util.HeaderUtil;
import com.kf.touchbase.web.rest.errors.BadRequestAlertException;
import io.micronaut.context.annotation.Value;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import io.micronaut.security.annotation.Secured;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

/**
 * REST controller for managing {@link com.kf.touchbase.domain.Mission}.
 */
@Controller("/api")
@Secured(AuthoritiesConstants.ADMIN)
public class MissionResource {

    private final Logger log = LoggerFactory.getLogger(MissionResource.class);

    private static final String ENTITY_NAME = "mission";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MissionService missionService;

    public MissionResource(MissionService missionService) {
        this.missionService = missionService;
    }

    /**
     * {@code POST  /missions} : Create a new mission.
     *
     * @param missionDTO the missionDTO to create.
     * @return the {@link HttpResponse} with status {@code 201 (Created)} and with body the new missionDTO, or with status {@code 400 (Bad Request)} if the mission has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @Post("/missions")
    @ExecuteOn(TaskExecutors.IO)
    public HttpResponse<MissionDTO> createMission(@Body MissionDTO missionDTO) throws URISyntaxException {
        log.debug("REST request to save Mission : {}", missionDTO);
        if (missionDTO.getId() != null) {
            throw new BadRequestAlertException("A new mission cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MissionDTO result = missionService.save(missionDTO);
        URI location = new URI("/api/missions/" + result.getId());
        return HttpResponse.created(result).headers(headers -> {
            headers.location(location);
            HeaderUtil.createEntityCreationAlert(headers, applicationName, true, ENTITY_NAME, result.getId().toString());
        });
    }

    /**
     * {@code PUT  /missions} : Updates an existing mission.
     *
     * @param missionDTO the missionDTO to update.
     * @return the {@link HttpResponse} with status {@code 200 (OK)} and with body the updated missionDTO,
     * or with status {@code 400 (Bad Request)} if the missionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the missionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @Put("/missions")
    @ExecuteOn(TaskExecutors.IO)
    public HttpResponse<MissionDTO> updateMission(@Body MissionDTO missionDTO) throws URISyntaxException {
        log.debug("REST request to update Mission : {}", missionDTO);
        if (missionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MissionDTO result = missionService.save(missionDTO);
        return HttpResponse.ok(result).headers(headers ->
            HeaderUtil.createEntityUpdateAlert(headers, applicationName, true, ENTITY_NAME, missionDTO.getId().toString()));
    }

    /**
     * {@code GET  /missions} : get all the missions.
     *
     * @return the {@link HttpResponse} with status {@code 200 (OK)} and the list of missions in body.
     */
    @Get("/missions")
    @ExecuteOn(TaskExecutors.IO)
    public Iterable<MissionDTO> getAllMissions(HttpRequest request) {
        log.debug("REST request to get all Missions");
        return missionService.findAll();
    }

    /**
     * {@code GET  /missions/:id} : get the "id" mission.
     *
     * @param id the id of the missionDTO to retrieve.
     * @return the {@link HttpResponse} with status {@code 200 (OK)} and with body the missionDTO, or with status {@code 404 (Not Found)}.
     */
    @Get("/missions/{id}")
    @ExecuteOn(TaskExecutors.IO)
    public Optional<MissionDTO> getMission(@PathVariable Long id) {
        log.debug("REST request to get Mission : {}", id);

        return missionService.findOne(id);
    }

    /**
     * {@code DELETE  /missions/:id} : delete the "id" mission.
     *
     * @param id the id of the missionDTO to delete.
     * @return the {@link HttpResponse} with status {@code 204 (NO_CONTENT)}.
     */
    @Delete("/missions/{id}")
    @ExecuteOn(TaskExecutors.IO)
    public HttpResponse deleteMission(@PathVariable Long id) {
        log.debug("REST request to delete Mission : {}", id);
        missionService.delete(id);
        return HttpResponse.noContent().headers(headers -> HeaderUtil.createEntityDeletionAlert(headers, applicationName, true, ENTITY_NAME, id.toString()));
    }
}
