package com.kf.touchbase.web.rest;


import com.kf.touchbase.domain.Chat;
import com.kf.touchbase.repository.ChatRepository;

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

import com.kf.touchbase.service.dto.ChatDTO;
import com.kf.touchbase.service.mapper.ChatMapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * Integration tests for the {@Link ChatResource} REST controller.
 */
@MicronautTest(transactional = false)
@Property(name = "micronaut.security.enabled", value = "false")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ChatResourceIT {

    private static final ZonedDateTime DEFAULT_CREATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_LAST_MODIFIED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LAST_MODIFIED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_CHATPI_CHAT_ID = "AAAAAAAAAA";
    private static final String UPDATED_CHATPI_CHAT_ID = "BBBBBBBBBB";

    @Inject
    private ChatMapper chatMapper;
    @Inject
    private ChatRepository chatRepository;

    @Inject @Client("/")
    RxHttpClient client;

    private Chat chat;

    @BeforeEach
    public void initTest() {
        chat = createEntity();
    }

    @AfterEach
    public void cleanUpTest() {
        chatRepository.deleteAll();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Chat createEntity() {
        Chat chat = new Chat()
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE)
            .chatpiChatId(DEFAULT_CHATPI_CHAT_ID);
        return chat;
    }


    @Test
    public void createChat() throws Exception {
        int databaseSizeBeforeCreate = chatRepository.findAll().size();

        ChatDTO chatDTO = chatMapper.toDto(chat);

        // Create the Chat
        HttpResponse<ChatDTO> response = client.exchange(HttpRequest.POST("/api/chats", chatDTO), ChatDTO.class).blockingFirst();

        assertThat(response.status().getCode()).isEqualTo(HttpStatus.CREATED.getCode());

        // Validate the Chat in the database
        List<Chat> chatList = chatRepository.findAll();
        assertThat(chatList).hasSize(databaseSizeBeforeCreate + 1);
        Chat testChat = chatList.get(chatList.size() - 1);

        assertThat(testChat.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testChat.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
        assertThat(testChat.getChatpiChatId()).isEqualTo(DEFAULT_CHATPI_CHAT_ID);
    }

    @Test
    public void createChatWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = chatRepository.findAll().size();

        // Create the Chat with an existing ID
        chat.setId(1L);
        ChatDTO chatDTO = chatMapper.toDto(chat);

        // An entity with an existing ID cannot be created, so this API call must fail
        @SuppressWarnings("unchecked")
        HttpResponse<ChatDTO> response = client.exchange(HttpRequest.POST("/api/chats", chatDTO), ChatDTO.class)
            .onErrorReturn(t -> (HttpResponse<ChatDTO>) ((HttpClientResponseException) t).getResponse()).blockingFirst();

        assertThat(response.status().getCode()).isEqualTo(HttpStatus.BAD_REQUEST.getCode());

        // Validate the Chat in the database
        List<Chat> chatList = chatRepository.findAll();
        assertThat(chatList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void getAllChats() throws Exception {
        // Initialize the database
        chatRepository.saveAndFlush(chat);

        // Get the chatList w/ all the chats
        List<ChatDTO> chats = client.retrieve(HttpRequest.GET("/api/chats?eagerload=true"), Argument.listOf(ChatDTO.class)).blockingFirst();
        ChatDTO testChat = chats.get(0);


        assertThat(testChat.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testChat.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
        assertThat(testChat.getChatpiChatId()).isEqualTo(DEFAULT_CHATPI_CHAT_ID);
    }

    @Test
    public void getChat() throws Exception {
        // Initialize the database
        chatRepository.saveAndFlush(chat);

        // Get the chat
        ChatDTO testChat = client.retrieve(HttpRequest.GET("/api/chats/" + chat.getId()), ChatDTO.class).blockingFirst();


        assertThat(testChat.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testChat.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
        assertThat(testChat.getChatpiChatId()).isEqualTo(DEFAULT_CHATPI_CHAT_ID);
    }

    @Test
    public void getNonExistingChat() throws Exception {
        // Get the chat
        @SuppressWarnings("unchecked")
        HttpResponse<ChatDTO> response = client.exchange(HttpRequest.GET("/api/chats/"+ Long.MAX_VALUE), ChatDTO.class)
            .onErrorReturn(t -> (HttpResponse<ChatDTO>) ((HttpClientResponseException) t).getResponse()).blockingFirst();

        assertThat(response.status().getCode()).isEqualTo(HttpStatus.NOT_FOUND.getCode());
    }

    @Test
    public void updateChat() throws Exception {
        // Initialize the database
        chatRepository.saveAndFlush(chat);

        int databaseSizeBeforeUpdate = chatRepository.findAll().size();

        // Update the chat
        Chat updatedChat = chatRepository.findById(chat.getId()).get();

        updatedChat
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE)
            .chatpiChatId(UPDATED_CHATPI_CHAT_ID);
        ChatDTO updatedChatDTO = chatMapper.toDto(updatedChat);

        @SuppressWarnings("unchecked")
        HttpResponse<ChatDTO> response = client.exchange(HttpRequest.PUT("/api/chats", updatedChatDTO), ChatDTO.class)
            .onErrorReturn(t -> (HttpResponse<ChatDTO>) ((HttpClientResponseException) t).getResponse()).blockingFirst();

        assertThat(response.status().getCode()).isEqualTo(HttpStatus.OK.getCode());

        // Validate the Chat in the database
        List<Chat> chatList = chatRepository.findAll();
        assertThat(chatList).hasSize(databaseSizeBeforeUpdate);
        Chat testChat = chatList.get(chatList.size() - 1);

        assertThat(testChat.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testChat.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
        assertThat(testChat.getChatpiChatId()).isEqualTo(UPDATED_CHATPI_CHAT_ID);
    }

    @Test
    public void updateNonExistingChat() throws Exception {
        int databaseSizeBeforeUpdate = chatRepository.findAll().size();

        // Create the Chat
        ChatDTO chatDTO = chatMapper.toDto(chat);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        @SuppressWarnings("unchecked")
        HttpResponse<ChatDTO> response = client.exchange(HttpRequest.PUT("/api/chats", chatDTO), ChatDTO.class)
            .onErrorReturn(t -> (HttpResponse<ChatDTO>) ((HttpClientResponseException) t).getResponse()).blockingFirst();

        assertThat(response.status().getCode()).isEqualTo(HttpStatus.BAD_REQUEST.getCode());

        // Validate the Chat in the database
        List<Chat> chatList = chatRepository.findAll();
        assertThat(chatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteChat() throws Exception {
        // Initialize the database with one entity
        chatRepository.saveAndFlush(chat);

        int databaseSizeBeforeDelete = chatRepository.findAll().size();

        // Delete the chat
        @SuppressWarnings("unchecked")
        HttpResponse<ChatDTO> response = client.exchange(HttpRequest.DELETE("/api/chats/"+ chat.getId()), ChatDTO.class)
            .onErrorReturn(t -> (HttpResponse<ChatDTO>) ((HttpClientResponseException) t).getResponse()).blockingFirst();

        assertThat(response.status().getCode()).isEqualTo(HttpStatus.NO_CONTENT.getCode());

            // Validate the database is now empty
        List<Chat> chatList = chatRepository.findAll();
        assertThat(chatList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Chat.class);
        Chat chat1 = new Chat();
        chat1.setId(1L);
        Chat chat2 = new Chat();
        chat2.setId(chat1.getId());
        assertThat(chat1).isEqualTo(chat2);
        chat2.setId(2L);
        assertThat(chat1).isNotEqualTo(chat2);
        chat1.setId(null);
        assertThat(chat1).isNotEqualTo(chat2);
    }
}
