package com.kf.touchbase.web.rest;

import com.kf.touchbase.security.AuthoritiesConstants;
import com.kf.touchbase.service.BaseJoinService;
import com.kf.touchbase.service.dto.BaseJoinDTO;
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
 * REST controller for managing {@link com.kf.touchbase.domain.BaseJoin}.
 */
@Controller("/api")
@Secured(AuthoritiesConstants.ADMIN)
public class BaseJoinResource {

    private final Logger log = LoggerFactory.getLogger(BaseJoinResource.class);

    private static final String ENTITY_NAME = "baseJoin";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BaseJoinService baseJoinService;

    public BaseJoinResource(BaseJoinService baseJoinService) {
        this.baseJoinService = baseJoinService;
    }

    /**
     * {@code POST  /base-joins} : Create a new baseJoin.
     *
     * @param baseJoinDTO the baseJoinDTO to create.
     * @return the {@link HttpResponse} with status {@code 201 (Created)} and with body the new baseJoinDTO, or with status {@code 400 (Bad Request)} if the baseJoin has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @Post("/base-joins")
    @ExecuteOn(TaskExecutors.IO)
    public HttpResponse<BaseJoinDTO> createBaseJoin(@Body BaseJoinDTO baseJoinDTO) throws URISyntaxException {
        log.debug("REST request to save BaseJoin : {}", baseJoinDTO);
        if (baseJoinDTO.getId() != null) {
            throw new BadRequestAlertException("A new baseJoin cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BaseJoinDTO result = baseJoinService.save(baseJoinDTO);
        URI location = new URI("/api/base-joins/" + result.getId());
        return HttpResponse.created(result).headers(headers -> {
            headers.location(location);
            HeaderUtil.createEntityCreationAlert(headers, applicationName, true, ENTITY_NAME, result.getId().toString());
        });
    }

    /**
     * {@code PUT  /base-joins} : Updates an existing baseJoin.
     *
     * @param baseJoinDTO the baseJoinDTO to update.
     * @return the {@link HttpResponse} with status {@code 200 (OK)} and with body the updated baseJoinDTO,
     * or with status {@code 400 (Bad Request)} if the baseJoinDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the baseJoinDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @Put("/base-joins")
    @ExecuteOn(TaskExecutors.IO)
    public HttpResponse<BaseJoinDTO> updateBaseJoin(@Body BaseJoinDTO baseJoinDTO) throws URISyntaxException {
        log.debug("REST request to update BaseJoin : {}", baseJoinDTO);
        if (baseJoinDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BaseJoinDTO result = baseJoinService.save(baseJoinDTO);
        return HttpResponse.ok(result).headers(headers ->
            HeaderUtil.createEntityUpdateAlert(headers, applicationName, true, ENTITY_NAME, baseJoinDTO.getId().toString()));
    }

    /**
     * {@code GET  /base-joins} : get all the baseJoins.
     *
     * @return the {@link HttpResponse} with status {@code 200 (OK)} and the list of baseJoins in body.
     */
    @Get("/base-joins")
    @ExecuteOn(TaskExecutors.IO)
    public Iterable<BaseJoinDTO> getAllBaseJoins(HttpRequest request) {
        log.debug("REST request to get all BaseJoins");
        return baseJoinService.findAll();
    }

    /**
     * {@code GET  /base-joins/:id} : get the "id" baseJoin.
     *
     * @param id the id of the baseJoinDTO to retrieve.
     * @return the {@link HttpResponse} with status {@code 200 (OK)} and with body the baseJoinDTO, or with status {@code 404 (Not Found)}.
     */
    @Get("/base-joins/{id}")
    @ExecuteOn(TaskExecutors.IO)
    public Optional<BaseJoinDTO> getBaseJoin(@PathVariable Long id) {
        log.debug("REST request to get BaseJoin : {}", id);

        return baseJoinService.findOne(id);
    }

    /**
     * {@code DELETE  /base-joins/:id} : delete the "id" baseJoin.
     *
     * @param id the id of the baseJoinDTO to delete.
     * @return the {@link HttpResponse} with status {@code 204 (NO_CONTENT)}.
     */
    @Delete("/base-joins/{id}")
    @ExecuteOn(TaskExecutors.IO)
    public HttpResponse deleteBaseJoin(@PathVariable Long id) {
        log.debug("REST request to delete BaseJoin : {}", id);
        baseJoinService.delete(id);
        return HttpResponse.noContent().headers(headers -> HeaderUtil.createEntityDeletionAlert(headers, applicationName, true, ENTITY_NAME, id.toString()));
    }
}
