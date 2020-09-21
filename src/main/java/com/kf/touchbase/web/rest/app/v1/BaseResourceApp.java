package com.kf.touchbase.web.rest.app.v1;

import com.kf.touchbase.security.AuthoritiesConstants;
import com.kf.touchbase.security.SecurityUtils;
import com.kf.touchbase.service.app.BaseServiceApp;
import com.kf.touchbase.service.dto.BaseDTO;
import com.kf.touchbase.util.HeaderUtil;
import com.kf.touchbase.web.rest.BaseResource;
import com.kf.touchbase.web.rest.errors.BadRequestAlertException;
import io.micronaut.context.annotation.Value;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import io.micronaut.security.annotation.Secured;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Controller("/app/v1")
@Secured(AuthoritiesConstants.USER)
public class BaseResourceApp {

    private final Logger log = LoggerFactory.getLogger(BaseResource.class);

    private static final String ENTITY_NAME = "base";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BaseServiceApp baseServiceApp;

    public BaseResourceApp(BaseServiceApp baseServiceApp) {
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
    @Status(HttpStatus.CREATED)
    public HttpResponse<BaseDTO> createBase(@Body BaseDTO baseDTO) throws URISyntaxException {
        log.debug("App REST request to create Base : {}", baseDTO);
        var authKey = SecurityUtils.getAuthKey();

        if (baseDTO.getId() != null) {
            throw new BadRequestAlertException("A new base cannot already have an ID", ENTITY_NAME, "idexists");
        }

        BaseDTO result = baseServiceApp.createBase(authKey, baseDTO);

        URI location = new URI("/app/v1/bases/" + result.getId());
        return HttpResponse.created(result).headers(headers -> {
            headers.location(location);
            HeaderUtil.createEntityCreationAlert(headers, applicationName, true, ENTITY_NAME, result.getId().toString());
        });
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
        log.debug("REST request to update Base : {}", baseDTO);
        if (baseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        var authKey = SecurityUtils.getAuthKey();
        BaseDTO result = baseServiceApp.updateBase(authKey, baseDTO);
        return HttpResponse.ok(result).headers(headers ->
            HeaderUtil.createEntityUpdateAlert(headers, applicationName, true, ENTITY_NAME, baseDTO.getId().toString()));
    }

    /**
     * {@code GET  /bases} : get all the bases.
     *
     * @param pageable the pagination information.
     * @return the {@link HttpResponse} with status {@code 200 (OK)} and the list of bases in body.
     */
    @Get("/bases")
    @Produces(MediaType.APPLICATION_JSON)
    public List<BaseDTO> getOwnBases() {
        var authKey = SecurityUtils.getAuthKey();
        return baseServiceApp.getOwnBases(authKey);
    }

    /**
     * {@code GET  /bases/:id} : get the "id" base.
     *
     * @param id the id of the baseDTO to retrieve.
     * @return the {@link HttpResponse} with status {@code 200 (OK)} and with body the baseDTO, or with status {@code 404 (Not Found)}.
     */
    @Get("/bases/{id}")
    @ExecuteOn(TaskExecutors.IO)
    public BaseDTO getBase(@PathVariable Long id) {
        log.debug("REST request to get Base : {}", id);
        var authKey = SecurityUtils.getAuthKey();
        return baseServiceApp.getOwnBase(authKey, id);
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
        log.debug("REST request to delete Base : {}", id);
        var authKey = SecurityUtils.getAuthKey();
        baseServiceApp.makeBaseInactive(authKey, id);
        return HttpResponse.noContent().headers(headers -> HeaderUtil.createEntityDeletionAlert(headers, applicationName, true, ENTITY_NAME, id.toString()));
    }
}
