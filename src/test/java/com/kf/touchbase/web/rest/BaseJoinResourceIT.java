package com.kf.touchbase.web.rest;


import com.kf.touchbase.domain.BaseJoin;
import com.kf.touchbase.repository.BaseJoinRepository;

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

import com.kf.touchbase.service.dto.BaseJoinDTO;
import com.kf.touchbase.service.mapper.BaseJoinMapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;


import com.kf.touchbase.domain.enumeration.BaseJoinAction;
/**
 * Integration tests for the {@Link BaseJoinResource} REST controller.
 */
@MicronautTest(transactional = false)
@Property(name = "micronaut.security.enabled", value = "false")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BaseJoinResourceIT {

    private static final BaseJoinAction DEFAULT_BASE_JOIN = BaseJoinAction.REQUEST;
    private static final BaseJoinAction UPDATED_BASE_JOIN = BaseJoinAction.INVITE;

    @Inject
    private BaseJoinMapper baseJoinMapper;
    @Inject
    private BaseJoinRepository baseJoinRepository;

    @Inject @Client("/")
    RxHttpClient client;

    private BaseJoin baseJoin;

    @BeforeEach
    public void initTest() {
        baseJoin = createEntity();
    }

    @AfterEach
    public void cleanUpTest() {
        baseJoinRepository.deleteAll();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BaseJoin createEntity() {
        BaseJoin baseJoin = new BaseJoin()
            .baseJoin(DEFAULT_BASE_JOIN);
        return baseJoin;
    }


    @Test
    public void createBaseJoin() throws Exception {
        int databaseSizeBeforeCreate = baseJoinRepository.findAll().size();

        BaseJoinDTO baseJoinDTO = baseJoinMapper.toDto(baseJoin);

        // Create the BaseJoin
        HttpResponse<BaseJoinDTO> response = client.exchange(HttpRequest.POST("/api/base-joins", baseJoinDTO), BaseJoinDTO.class).blockingFirst();

        assertThat(response.status().getCode()).isEqualTo(HttpStatus.CREATED.getCode());

        // Validate the BaseJoin in the database
        List<BaseJoin> baseJoinList = baseJoinRepository.findAll();
        assertThat(baseJoinList).hasSize(databaseSizeBeforeCreate + 1);
        BaseJoin testBaseJoin = baseJoinList.get(baseJoinList.size() - 1);

        assertThat(testBaseJoin.getBaseJoin()).isEqualTo(DEFAULT_BASE_JOIN);
    }

    @Test
    public void createBaseJoinWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = baseJoinRepository.findAll().size();

        // Create the BaseJoin with an existing ID
        baseJoin.setId(1L);
        BaseJoinDTO baseJoinDTO = baseJoinMapper.toDto(baseJoin);

        // An entity with an existing ID cannot be created, so this API call must fail
        @SuppressWarnings("unchecked")
        HttpResponse<BaseJoinDTO> response = client.exchange(HttpRequest.POST("/api/base-joins", baseJoinDTO), BaseJoinDTO.class)
            .onErrorReturn(t -> (HttpResponse<BaseJoinDTO>) ((HttpClientResponseException) t).getResponse()).blockingFirst();

        assertThat(response.status().getCode()).isEqualTo(HttpStatus.BAD_REQUEST.getCode());

        // Validate the BaseJoin in the database
        List<BaseJoin> baseJoinList = baseJoinRepository.findAll();
        assertThat(baseJoinList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void getAllBaseJoins() throws Exception {
        // Initialize the database
        baseJoinRepository.saveAndFlush(baseJoin);

        // Get the baseJoinList w/ all the baseJoins
        List<BaseJoinDTO> baseJoins = client.retrieve(HttpRequest.GET("/api/base-joins?eagerload=true"), Argument.listOf(BaseJoinDTO.class)).blockingFirst();
        BaseJoinDTO testBaseJoin = baseJoins.get(0);


        assertThat(testBaseJoin.getBaseJoin()).isEqualTo(DEFAULT_BASE_JOIN);
    }

    @Test
    public void getBaseJoin() throws Exception {
        // Initialize the database
        baseJoinRepository.saveAndFlush(baseJoin);

        // Get the baseJoin
        BaseJoinDTO testBaseJoin = client.retrieve(HttpRequest.GET("/api/base-joins/" + baseJoin.getId()), BaseJoinDTO.class).blockingFirst();


        assertThat(testBaseJoin.getBaseJoin()).isEqualTo(DEFAULT_BASE_JOIN);
    }

    @Test
    public void getNonExistingBaseJoin() throws Exception {
        // Get the baseJoin
        @SuppressWarnings("unchecked")
        HttpResponse<BaseJoinDTO> response = client.exchange(HttpRequest.GET("/api/base-joins/"+ Long.MAX_VALUE), BaseJoinDTO.class)
            .onErrorReturn(t -> (HttpResponse<BaseJoinDTO>) ((HttpClientResponseException) t).getResponse()).blockingFirst();

        assertThat(response.status().getCode()).isEqualTo(HttpStatus.NOT_FOUND.getCode());
    }

    @Test
    public void updateBaseJoin() throws Exception {
        // Initialize the database
        baseJoinRepository.saveAndFlush(baseJoin);

        int databaseSizeBeforeUpdate = baseJoinRepository.findAll().size();

        // Update the baseJoin
        BaseJoin updatedBaseJoin = baseJoinRepository.findById(baseJoin.getId()).get();

        updatedBaseJoin
            .baseJoin(UPDATED_BASE_JOIN);
        BaseJoinDTO updatedBaseJoinDTO = baseJoinMapper.toDto(updatedBaseJoin);

        @SuppressWarnings("unchecked")
        HttpResponse<BaseJoinDTO> response = client.exchange(HttpRequest.PUT("/api/base-joins", updatedBaseJoinDTO), BaseJoinDTO.class)
            .onErrorReturn(t -> (HttpResponse<BaseJoinDTO>) ((HttpClientResponseException) t).getResponse()).blockingFirst();

        assertThat(response.status().getCode()).isEqualTo(HttpStatus.OK.getCode());

        // Validate the BaseJoin in the database
        List<BaseJoin> baseJoinList = baseJoinRepository.findAll();
        assertThat(baseJoinList).hasSize(databaseSizeBeforeUpdate);
        BaseJoin testBaseJoin = baseJoinList.get(baseJoinList.size() - 1);

        assertThat(testBaseJoin.getBaseJoin()).isEqualTo(UPDATED_BASE_JOIN);
    }

    @Test
    public void updateNonExistingBaseJoin() throws Exception {
        int databaseSizeBeforeUpdate = baseJoinRepository.findAll().size();

        // Create the BaseJoin
        BaseJoinDTO baseJoinDTO = baseJoinMapper.toDto(baseJoin);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        @SuppressWarnings("unchecked")
        HttpResponse<BaseJoinDTO> response = client.exchange(HttpRequest.PUT("/api/base-joins", baseJoinDTO), BaseJoinDTO.class)
            .onErrorReturn(t -> (HttpResponse<BaseJoinDTO>) ((HttpClientResponseException) t).getResponse()).blockingFirst();

        assertThat(response.status().getCode()).isEqualTo(HttpStatus.BAD_REQUEST.getCode());

        // Validate the BaseJoin in the database
        List<BaseJoin> baseJoinList = baseJoinRepository.findAll();
        assertThat(baseJoinList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteBaseJoin() throws Exception {
        // Initialize the database with one entity
        baseJoinRepository.saveAndFlush(baseJoin);

        int databaseSizeBeforeDelete = baseJoinRepository.findAll().size();

        // Delete the baseJoin
        @SuppressWarnings("unchecked")
        HttpResponse<BaseJoinDTO> response = client.exchange(HttpRequest.DELETE("/api/base-joins/"+ baseJoin.getId()), BaseJoinDTO.class)
            .onErrorReturn(t -> (HttpResponse<BaseJoinDTO>) ((HttpClientResponseException) t).getResponse()).blockingFirst();

        assertThat(response.status().getCode()).isEqualTo(HttpStatus.NO_CONTENT.getCode());

            // Validate the database is now empty
        List<BaseJoin> baseJoinList = baseJoinRepository.findAll();
        assertThat(baseJoinList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BaseJoin.class);
        BaseJoin baseJoin1 = new BaseJoin();
        baseJoin1.setId(1L);
        BaseJoin baseJoin2 = new BaseJoin();
        baseJoin2.setId(baseJoin1.getId());
        assertThat(baseJoin1).isEqualTo(baseJoin2);
        baseJoin2.setId(2L);
        assertThat(baseJoin1).isNotEqualTo(baseJoin2);
        baseJoin1.setId(null);
        assertThat(baseJoin1).isNotEqualTo(baseJoin2);
    }
}
