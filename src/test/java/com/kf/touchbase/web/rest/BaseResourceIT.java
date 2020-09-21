package com.kf.touchbase.web.rest;


import com.kf.touchbase.domain.Base;
import com.kf.touchbase.repository.BaseRepository;

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

import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import com.kf.touchbase.service.dto.BaseDTO;
import com.kf.touchbase.service.mapper.BaseMapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * Integration tests for the {@Link BaseResource} REST controller.
 */
@MicronautTest(transactional = false)
@Property(name = "micronaut.security.enabled", value = "false")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BaseResourceIT {

    private static final ZonedDateTime DEFAULT_CREATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_LAST_MODIFIED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LAST_MODIFIED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Double DEFAULT_SCORE = 1D;
    private static final Double UPDATED_SCORE = 2D;

    private static final String DEFAULT_IMAGE_URL = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_URL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    @Inject
    private BaseMapper baseMapper;
    @Inject
    private BaseRepository baseRepository;

    @Inject @Client("/")
    RxHttpClient client;

    private Base base;

    @BeforeEach
    public void initTest() {
        base = createEntity();
    }

    @AfterEach
    public void cleanUpTest() {
        baseRepository.deleteAll();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Base createEntity() {
        Base base = new Base()
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE)
            .name(DEFAULT_NAME)
            .score(DEFAULT_SCORE)
            .imageUrl(DEFAULT_IMAGE_URL)
            .isActive(DEFAULT_IS_ACTIVE);
        return base;
    }


    @Test
    public void createBase() throws Exception {
        int databaseSizeBeforeCreate = baseRepository.findAll().size();

        BaseDTO baseDTO = baseMapper.toDto(base);

        // Create the Base
        HttpResponse<BaseDTO> response = client.exchange(HttpRequest.POST("/api/bases", baseDTO), BaseDTO.class).blockingFirst();

        assertThat(response.status().getCode()).isEqualTo(HttpStatus.CREATED.getCode());

        // Validate the Base in the database
        List<Base> baseList = baseRepository.findAll();
        assertThat(baseList).hasSize(databaseSizeBeforeCreate + 1);
        Base testBase = baseList.get(baseList.size() - 1);

        assertThat(testBase.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testBase.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
        assertThat(testBase.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBase.getScore()).isEqualTo(DEFAULT_SCORE);
        assertThat(testBase.getImageUrl()).isEqualTo(DEFAULT_IMAGE_URL);
        assertThat(testBase.isIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
    }

    @Test
    public void createBaseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = baseRepository.findAll().size();

        // Create the Base with an existing ID
        base.setId(1L);
        BaseDTO baseDTO = baseMapper.toDto(base);

        // An entity with an existing ID cannot be created, so this API call must fail
        @SuppressWarnings("unchecked")
        HttpResponse<BaseDTO> response = client.exchange(HttpRequest.POST("/api/bases", baseDTO), BaseDTO.class)
            .onErrorReturn(t -> (HttpResponse<BaseDTO>) ((HttpClientResponseException) t).getResponse()).blockingFirst();

        assertThat(response.status().getCode()).isEqualTo(HttpStatus.BAD_REQUEST.getCode());

        // Validate the Base in the database
        List<Base> baseList = baseRepository.findAll();
        assertThat(baseList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = baseRepository.findAll().size();
        // set the field null
        base.setName(null);

        // Create the Base, which fails.
        BaseDTO baseDTO = baseMapper.toDto(base);

        @SuppressWarnings("unchecked")
        HttpResponse<BaseDTO> response = client.exchange(HttpRequest.POST("/api/bases", baseDTO), BaseDTO.class)
            .onErrorReturn(t -> (HttpResponse<BaseDTO>) ((HttpClientResponseException) t).getResponse()).blockingFirst();

        assertThat(response.status().getCode()).isEqualTo(HttpStatus.BAD_REQUEST.getCode());

        List<Base> baseList = baseRepository.findAll();
        assertThat(baseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkImageUrlIsRequired() throws Exception {
        int databaseSizeBeforeTest = baseRepository.findAll().size();
        // set the field null
        base.setImageUrl(null);

        // Create the Base, which fails.
        BaseDTO baseDTO = baseMapper.toDto(base);

        @SuppressWarnings("unchecked")
        HttpResponse<BaseDTO> response = client.exchange(HttpRequest.POST("/api/bases", baseDTO), BaseDTO.class)
            .onErrorReturn(t -> (HttpResponse<BaseDTO>) ((HttpClientResponseException) t).getResponse()).blockingFirst();

        assertThat(response.status().getCode()).isEqualTo(HttpStatus.BAD_REQUEST.getCode());

        List<Base> baseList = baseRepository.findAll();
        assertThat(baseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllBases() throws Exception {
        // Initialize the database
        baseRepository.saveAndFlush(base);

        // Get the baseList w/ all the bases
        List<BaseDTO> bases = client.retrieve(HttpRequest.GET("/api/bases?eagerload=true"), Argument.listOf(BaseDTO.class)).blockingFirst();
        BaseDTO testBase = bases.get(0);


        assertThat(testBase.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testBase.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
        assertThat(testBase.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBase.getScore()).isEqualTo(DEFAULT_SCORE);
        assertThat(testBase.getImageUrl()).isEqualTo(DEFAULT_IMAGE_URL);
        assertThat(testBase.isIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
    }

    @Test
    public void getBase() throws Exception {
        // Initialize the database
        baseRepository.saveAndFlush(base);

        // Get the base
        BaseDTO testBase = client.retrieve(HttpRequest.GET("/api/bases/" + base.getId()), BaseDTO.class).blockingFirst();


        assertThat(testBase.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testBase.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
        assertThat(testBase.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBase.getScore()).isEqualTo(DEFAULT_SCORE);
        assertThat(testBase.getImageUrl()).isEqualTo(DEFAULT_IMAGE_URL);
        assertThat(testBase.isIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
    }

    @Test
    public void getNonExistingBase() throws Exception {
        // Get the base
        @SuppressWarnings("unchecked")
        HttpResponse<BaseDTO> response = client.exchange(HttpRequest.GET("/api/bases/"+ Long.MAX_VALUE), BaseDTO.class)
            .onErrorReturn(t -> (HttpResponse<BaseDTO>) ((HttpClientResponseException) t).getResponse()).blockingFirst();

        assertThat(response.status().getCode()).isEqualTo(HttpStatus.NOT_FOUND.getCode());
    }

    @Test
    public void updateBase() throws Exception {
        // Initialize the database
        baseRepository.saveAndFlush(base);

        int databaseSizeBeforeUpdate = baseRepository.findAll().size();

        // Update the base
        Base updatedBase = baseRepository.findById(base.getId()).get();

        updatedBase
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE)
            .name(UPDATED_NAME)
            .score(UPDATED_SCORE)
            .imageUrl(UPDATED_IMAGE_URL)
            .isActive(UPDATED_IS_ACTIVE);
        BaseDTO updatedBaseDTO = baseMapper.toDto(updatedBase);

        @SuppressWarnings("unchecked")
        HttpResponse<BaseDTO> response = client.exchange(HttpRequest.PUT("/api/bases", updatedBaseDTO), BaseDTO.class)
            .onErrorReturn(t -> (HttpResponse<BaseDTO>) ((HttpClientResponseException) t).getResponse()).blockingFirst();

        assertThat(response.status().getCode()).isEqualTo(HttpStatus.OK.getCode());

        // Validate the Base in the database
        List<Base> baseList = baseRepository.findAll();
        assertThat(baseList).hasSize(databaseSizeBeforeUpdate);
        Base testBase = baseList.get(baseList.size() - 1);

        assertThat(testBase.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testBase.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
        assertThat(testBase.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBase.getScore()).isEqualTo(UPDATED_SCORE);
        assertThat(testBase.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
        assertThat(testBase.isIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
    }

    @Test
    public void updateNonExistingBase() throws Exception {
        int databaseSizeBeforeUpdate = baseRepository.findAll().size();

        // Create the Base
        BaseDTO baseDTO = baseMapper.toDto(base);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        @SuppressWarnings("unchecked")
        HttpResponse<BaseDTO> response = client.exchange(HttpRequest.PUT("/api/bases", baseDTO), BaseDTO.class)
            .onErrorReturn(t -> (HttpResponse<BaseDTO>) ((HttpClientResponseException) t).getResponse()).blockingFirst();

        assertThat(response.status().getCode()).isEqualTo(HttpStatus.BAD_REQUEST.getCode());

        // Validate the Base in the database
        List<Base> baseList = baseRepository.findAll();
        assertThat(baseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteBase() throws Exception {
        // Initialize the database with one entity
        baseRepository.saveAndFlush(base);

        int databaseSizeBeforeDelete = baseRepository.findAll().size();

        // Delete the base
        @SuppressWarnings("unchecked")
        HttpResponse<BaseDTO> response = client.exchange(HttpRequest.DELETE("/api/bases/"+ base.getId()), BaseDTO.class)
            .onErrorReturn(t -> (HttpResponse<BaseDTO>) ((HttpClientResponseException) t).getResponse()).blockingFirst();

        assertThat(response.status().getCode()).isEqualTo(HttpStatus.NO_CONTENT.getCode());

            // Validate the database is now empty
        List<Base> baseList = baseRepository.findAll();
        assertThat(baseList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Base.class);
        Base base1 = new Base();
        base1.setId(1L);
        Base base2 = new Base();
        base2.setId(base1.getId());
        assertThat(base1).isEqualTo(base2);
        base2.setId(2L);
        assertThat(base1).isNotEqualTo(base2);
        base1.setId(null);
        assertThat(base1).isNotEqualTo(base2);
    }
}
