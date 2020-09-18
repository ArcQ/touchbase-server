package com.kf.touchbase.web.rest;


import com.kf.touchbase.domain.BaseMember;
import com.kf.touchbase.repository.BaseMemberRepository;

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

import com.kf.touchbase.service.dto.BaseMemberDTO;
import com.kf.touchbase.service.mapper.BaseMemberMapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;


import com.kf.touchbase.domain.enumeration.Role;
/**
 * Integration tests for the {@Link BaseMemberResource} REST controller.
 */
@MicronautTest(transactional = false)
@Property(name = "micronaut.security.enabled", value = "false")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BaseMemberResourceIT {

    private static final ZonedDateTime DEFAULT_CREATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_UPDATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Role DEFAULT_ROLE = Role.MEMBER;
    private static final Role UPDATED_ROLE = Role.ADMIN;

    @Inject
    private BaseMemberMapper baseMemberMapper;
    @Inject
    private BaseMemberRepository baseMemberRepository;

    @Inject @Client("/")
    RxHttpClient client;

    private BaseMember baseMember;

    @BeforeEach
    public void initTest() {
        baseMember = createEntity();
    }

    @AfterEach
    public void cleanUpTest() {
        baseMemberRepository.deleteAll();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BaseMember createEntity() {
        BaseMember baseMember = new BaseMember()
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT)
            .role(DEFAULT_ROLE);
        return baseMember;
    }


    @Test
    public void createBaseMember() throws Exception {
        int databaseSizeBeforeCreate = baseMemberRepository.findAll().size();

        BaseMemberDTO baseMemberDTO = baseMemberMapper.toDto(baseMember);

        // Create the BaseMember
        HttpResponse<BaseMemberDTO> response = client.exchange(HttpRequest.POST("/api/base-members", baseMemberDTO), BaseMemberDTO.class).blockingFirst();

        assertThat(response.status().getCode()).isEqualTo(HttpStatus.CREATED.getCode());

        // Validate the BaseMember in the database
        List<BaseMember> baseMemberList = baseMemberRepository.findAll();
        assertThat(baseMemberList).hasSize(databaseSizeBeforeCreate + 1);
        BaseMember testBaseMember = baseMemberList.get(baseMemberList.size() - 1);

        assertThat(testBaseMember.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testBaseMember.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testBaseMember.getRole()).isEqualTo(DEFAULT_ROLE);
    }

    @Test
    public void createBaseMemberWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = baseMemberRepository.findAll().size();

        // Create the BaseMember with an existing ID
        baseMember.setId(1L);
        BaseMemberDTO baseMemberDTO = baseMemberMapper.toDto(baseMember);

        // An entity with an existing ID cannot be created, so this API call must fail
        @SuppressWarnings("unchecked")
        HttpResponse<BaseMemberDTO> response = client.exchange(HttpRequest.POST("/api/base-members", baseMemberDTO), BaseMemberDTO.class)
            .onErrorReturn(t -> (HttpResponse<BaseMemberDTO>) ((HttpClientResponseException) t).getResponse()).blockingFirst();

        assertThat(response.status().getCode()).isEqualTo(HttpStatus.BAD_REQUEST.getCode());

        // Validate the BaseMember in the database
        List<BaseMember> baseMemberList = baseMemberRepository.findAll();
        assertThat(baseMemberList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void getAllBaseMembers() throws Exception {
        // Initialize the database
        baseMemberRepository.saveAndFlush(baseMember);

        // Get the baseMemberList w/ all the baseMembers
        List<BaseMemberDTO> baseMembers = client.retrieve(HttpRequest.GET("/api/base-members?eagerload=true"), Argument.listOf(BaseMemberDTO.class)).blockingFirst();
        BaseMemberDTO testBaseMember = baseMembers.get(0);


        assertThat(testBaseMember.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testBaseMember.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testBaseMember.getRole()).isEqualTo(DEFAULT_ROLE);
    }

    @Test
    public void getBaseMember() throws Exception {
        // Initialize the database
        baseMemberRepository.saveAndFlush(baseMember);

        // Get the baseMember
        BaseMemberDTO testBaseMember = client.retrieve(HttpRequest.GET("/api/base-members/" + baseMember.getId()), BaseMemberDTO.class).blockingFirst();


        assertThat(testBaseMember.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testBaseMember.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testBaseMember.getRole()).isEqualTo(DEFAULT_ROLE);
    }

    @Test
    public void getNonExistingBaseMember() throws Exception {
        // Get the baseMember
        @SuppressWarnings("unchecked")
        HttpResponse<BaseMemberDTO> response = client.exchange(HttpRequest.GET("/api/base-members/"+ Long.MAX_VALUE), BaseMemberDTO.class)
            .onErrorReturn(t -> (HttpResponse<BaseMemberDTO>) ((HttpClientResponseException) t).getResponse()).blockingFirst();

        assertThat(response.status().getCode()).isEqualTo(HttpStatus.NOT_FOUND.getCode());
    }

    @Test
    public void updateBaseMember() throws Exception {
        // Initialize the database
        baseMemberRepository.saveAndFlush(baseMember);

        int databaseSizeBeforeUpdate = baseMemberRepository.findAll().size();

        // Update the baseMember
        BaseMember updatedBaseMember = baseMemberRepository.findById(baseMember.getId()).get();

        updatedBaseMember
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .role(UPDATED_ROLE);
        BaseMemberDTO updatedBaseMemberDTO = baseMemberMapper.toDto(updatedBaseMember);

        @SuppressWarnings("unchecked")
        HttpResponse<BaseMemberDTO> response = client.exchange(HttpRequest.PUT("/api/base-members", updatedBaseMemberDTO), BaseMemberDTO.class)
            .onErrorReturn(t -> (HttpResponse<BaseMemberDTO>) ((HttpClientResponseException) t).getResponse()).blockingFirst();

        assertThat(response.status().getCode()).isEqualTo(HttpStatus.OK.getCode());

        // Validate the BaseMember in the database
        List<BaseMember> baseMemberList = baseMemberRepository.findAll();
        assertThat(baseMemberList).hasSize(databaseSizeBeforeUpdate);
        BaseMember testBaseMember = baseMemberList.get(baseMemberList.size() - 1);

        assertThat(testBaseMember.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testBaseMember.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testBaseMember.getRole()).isEqualTo(UPDATED_ROLE);
    }

    @Test
    public void updateNonExistingBaseMember() throws Exception {
        int databaseSizeBeforeUpdate = baseMemberRepository.findAll().size();

        // Create the BaseMember
        BaseMemberDTO baseMemberDTO = baseMemberMapper.toDto(baseMember);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        @SuppressWarnings("unchecked")
        HttpResponse<BaseMemberDTO> response = client.exchange(HttpRequest.PUT("/api/base-members", baseMemberDTO), BaseMemberDTO.class)
            .onErrorReturn(t -> (HttpResponse<BaseMemberDTO>) ((HttpClientResponseException) t).getResponse()).blockingFirst();

        assertThat(response.status().getCode()).isEqualTo(HttpStatus.BAD_REQUEST.getCode());

        // Validate the BaseMember in the database
        List<BaseMember> baseMemberList = baseMemberRepository.findAll();
        assertThat(baseMemberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteBaseMember() throws Exception {
        // Initialize the database with one entity
        baseMemberRepository.saveAndFlush(baseMember);

        int databaseSizeBeforeDelete = baseMemberRepository.findAll().size();

        // Delete the baseMember
        @SuppressWarnings("unchecked")
        HttpResponse<BaseMemberDTO> response = client.exchange(HttpRequest.DELETE("/api/base-members/"+ baseMember.getId()), BaseMemberDTO.class)
            .onErrorReturn(t -> (HttpResponse<BaseMemberDTO>) ((HttpClientResponseException) t).getResponse()).blockingFirst();

        assertThat(response.status().getCode()).isEqualTo(HttpStatus.NO_CONTENT.getCode());

            // Validate the database is now empty
        List<BaseMember> baseMemberList = baseMemberRepository.findAll();
        assertThat(baseMemberList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BaseMember.class);
        BaseMember baseMember1 = new BaseMember();
        baseMember1.setId(1L);
        BaseMember baseMember2 = new BaseMember();
        baseMember2.setId(baseMember1.getId());
        assertThat(baseMember1).isEqualTo(baseMember2);
        baseMember2.setId(2L);
        assertThat(baseMember1).isNotEqualTo(baseMember2);
        baseMember1.setId(null);
        assertThat(baseMember1).isNotEqualTo(baseMember2);
    }
}
