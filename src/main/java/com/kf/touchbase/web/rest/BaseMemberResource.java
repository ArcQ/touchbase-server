package com.kf.touchbase.web.rest;

import com.kf.touchbase.service.BaseMemberService;
import com.kf.touchbase.web.rest.errors.BadRequestAlertException;
import com.kf.touchbase.service.dto.BaseMemberDTO;

import com.kf.touchbase.util.HeaderUtil;
import io.micronaut.context.annotation.Value;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import io.micronaut.http.uri.UriBuilder;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.annotation.Nullable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.kf.touchbase.domain.BaseMember}.
 */
@Controller("/api")
public class BaseMemberResource {

    private final Logger log = LoggerFactory.getLogger(BaseMemberResource.class);

    private static final String ENTITY_NAME = "baseMember";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BaseMemberService baseMemberService;

    public BaseMemberResource(BaseMemberService baseMemberService) {
        this.baseMemberService = baseMemberService;
    }

    /**
     * {@code POST  /base-members} : Create a new baseMember.
     *
     * @param baseMemberDTO the baseMemberDTO to create.
     * @return the {@link HttpResponse} with status {@code 201 (Created)} and with body the new baseMemberDTO, or with status {@code 400 (Bad Request)} if the baseMember has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @Post("/base-members")
    @ExecuteOn(TaskExecutors.IO)
    public HttpResponse<BaseMemberDTO> createBaseMember(@Body BaseMemberDTO baseMemberDTO) throws URISyntaxException {
        log.debug("REST request to save BaseMember : {}", baseMemberDTO);
        if (baseMemberDTO.getId() != null) {
            throw new BadRequestAlertException("A new baseMember cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BaseMemberDTO result = baseMemberService.save(baseMemberDTO);
        URI location = new URI("/api/base-members/" + result.getId());
        return HttpResponse.created(result).headers(headers -> {
            headers.location(location);
            HeaderUtil.createEntityCreationAlert(headers, applicationName, true, ENTITY_NAME, result.getId().toString());
        });
    }

    /**
     * {@code PUT  /base-members} : Updates an existing baseMember.
     *
     * @param baseMemberDTO the baseMemberDTO to update.
     * @return the {@link HttpResponse} with status {@code 200 (OK)} and with body the updated baseMemberDTO,
     * or with status {@code 400 (Bad Request)} if the baseMemberDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the baseMemberDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @Put("/base-members")
    @ExecuteOn(TaskExecutors.IO)
    public HttpResponse<BaseMemberDTO> updateBaseMember(@Body BaseMemberDTO baseMemberDTO) throws URISyntaxException {
        log.debug("REST request to update BaseMember : {}", baseMemberDTO);
        if (baseMemberDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BaseMemberDTO result = baseMemberService.save(baseMemberDTO);
        return HttpResponse.ok(result).headers(headers ->
            HeaderUtil.createEntityUpdateAlert(headers, applicationName, true, ENTITY_NAME, baseMemberDTO.getId().toString()));
    }

    /**
     * {@code GET  /base-members} : get all the baseMembers.
     *
     * @return the {@link HttpResponse} with status {@code 200 (OK)} and the list of baseMembers in body.
     */
    @Get("/base-members")
    @ExecuteOn(TaskExecutors.IO)
    public Iterable<BaseMemberDTO> getAllBaseMembers(HttpRequest request) {
        log.debug("REST request to get all BaseMembers");
        return baseMemberService.findAll();
    }

    /**
     * {@code GET  /base-members/:id} : get the "id" baseMember.
     *
     * @param id the id of the baseMemberDTO to retrieve.
     * @return the {@link HttpResponse} with status {@code 200 (OK)} and with body the baseMemberDTO, or with status {@code 404 (Not Found)}.
     */
    @Get("/base-members/{id}")
    @ExecuteOn(TaskExecutors.IO)
    public Optional<BaseMemberDTO> getBaseMember(@PathVariable Long id) {
        log.debug("REST request to get BaseMember : {}", id);
        
        return baseMemberService.findOne(id);
    }

    /**
     * {@code DELETE  /base-members/:id} : delete the "id" baseMember.
     *
     * @param id the id of the baseMemberDTO to delete.
     * @return the {@link HttpResponse} with status {@code 204 (NO_CONTENT)}.
     */
    @Delete("/base-members/{id}")
    @ExecuteOn(TaskExecutors.IO)
    public HttpResponse deleteBaseMember(@PathVariable Long id) {
        log.debug("REST request to delete BaseMember : {}", id);
        baseMemberService.delete(id);
        return HttpResponse.noContent().headers(headers -> HeaderUtil.createEntityDeletionAlert(headers, applicationName, true, ENTITY_NAME, id.toString()));
    }
}
