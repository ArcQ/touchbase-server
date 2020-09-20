package com.kf.touchbase.web.rest.app.v1;

import com.kf.touchbase.service.app.BaseServiceApp;
import com.kf.touchbase.service.dto.BaseDTO;
import com.kf.touchbase.web.rest.BaseResource;
import io.micronaut.context.annotation.Value;
import io.micronaut.data.model.Pageable;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@Controller("/api/app/v1")
public class BaseResourceApp {

    private final Logger log = LoggerFactory.getLogger(BaseResource.class);

    private static final String ENTITY_NAME = "base";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BaseResource baseResource;

    private final BaseServiceApp baseServiceApp;

    public BaseResourceApp(BaseResource baseResource,  BaseServiceApp baseServiceApp) {
        this.baseResource = baseResource;
        this.baseServiceApp = baseServiceApp;
    }

    /**
     * {@code POST  /bases} : Create a new base.
     *
     * @param baseDTO the baseDTO to create.
     * @return the {@link HttpResponse} with status {@code 201 (Created)} and with body the new baseDTO, or with status {@code 400 (Bad Request)} if the base has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @Post("/bases")
    @ExecuteOn(TaskExecutors.IO)
    public HttpResponse<BaseDTO> createBase(@Body BaseDTO baseDTO) throws URISyntaxException {
        log.debug("App REST request to create Base : {}", baseDTO);
        return baseResource.createBase(baseDTO);
    }

    /**
     * {@code PUT  /bases} : Updates an existing base.
     *
     * @param baseDTO the baseDTO to update.
     * @return the {@link HttpResponse} with status {@code 200 (OK)} and with body the updated baseDTO,
     * or with status {@code 400 (Bad Request)} if the baseDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the baseDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @Put("/bases")
    @ExecuteOn(TaskExecutors.IO)
    public HttpResponse<BaseDTO> updateBase(@Body BaseDTO baseDTO) throws URISyntaxException {
        log.debug("App REST request to update Base : {}", baseDTO);
        return baseResource.updateBase(baseDTO);
    }

    /**
     * {@code GET  /bases} : get all the bases.
     *
     * @param pageable the pagination information.
     * @return the {@link HttpResponse} with status {@code 200 (OK)} and the list of bases in body.
     */
    @Get("/bases")
    @ExecuteOn(TaskExecutors.IO)
    public HttpResponse<List<BaseDTO>> getAllBases(HttpRequest request, Pageable pageable) {
        log.debug("App REST request to get all Bases");
        return baseResource.getAllBases(request, pageable);
    }

    /**
     * {@code GET  /bases/:id} : get the "id" base.
     *
     * @param id the id of the baseDTO to retrieve.
     * @return the {@link HttpResponse} with status {@code 200 (OK)} and with body the baseDTO, or with status {@code 404 (Not Found)}.
     */
    @Get("/bases/{id}")
    @ExecuteOn(TaskExecutors.IO)
    public Optional<BaseDTO> getBase(@PathVariable Long id) {
        log.debug("REST request to get Base : {}", id);
        return baseResource.getBase(id);
    }

    /**
     * {@code DELETE  /bases/:id} : delete the "id" base.
     *
     * @param id the id of the baseDTO to delete.
     * @return the {@link HttpResponse} with status {@code 204 (NO_CONTENT)}.
     */
    @Delete("/bases/{id}")
    @ExecuteOn(TaskExecutors.IO)
    public HttpResponse deleteBase(@PathVariable Long id) {
        log.debug("REST request to get Base : {}", id);
        return baseResource.deleteBase(id);
    }
}
