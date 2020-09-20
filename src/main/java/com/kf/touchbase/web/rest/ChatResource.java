package com.kf.touchbase.web.rest;

import com.kf.touchbase.security.AuthoritiesConstants;
import com.kf.touchbase.service.ChatService;
import com.kf.touchbase.service.dto.ChatDTO;
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
 * REST controller for managing {@link com.kf.touchbase.domain.Chat}.
 */
@Controller("/api")
@Secured(AuthoritiesConstants.ADMIN)
public class ChatResource {

    private final Logger log = LoggerFactory.getLogger(ChatResource.class);

    private static final String ENTITY_NAME = "chat";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ChatService chatService;

    public ChatResource(ChatService chatService) {
        this.chatService = chatService;
    }

    /**
     * {@code POST  /chats} : Create a new chat.
     *
     * @param chatDTO the chatDTO to create.
     * @return the {@link HttpResponse} with status {@code 201 (Created)} and with body the new chatDTO, or with status {@code 400 (Bad Request)} if the chat has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @Post("/chats")
    @ExecuteOn(TaskExecutors.IO)
    public HttpResponse<ChatDTO> createChat(@Body ChatDTO chatDTO) throws URISyntaxException {
        log.debug("REST request to save Chat : {}", chatDTO);
        if (chatDTO.getId() != null) {
            throw new BadRequestAlertException("A new chat cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ChatDTO result = chatService.save(chatDTO);
        URI location = new URI("/api/chats/" + result.getId());
        return HttpResponse.created(result).headers(headers -> {
            headers.location(location);
            HeaderUtil.createEntityCreationAlert(headers, applicationName, true, ENTITY_NAME, result.getId().toString());
        });
    }

    /**
     * {@code PUT  /chats} : Updates an existing chat.
     *
     * @param chatDTO the chatDTO to update.
     * @return the {@link HttpResponse} with status {@code 200 (OK)} and with body the updated chatDTO,
     * or with status {@code 400 (Bad Request)} if the chatDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the chatDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @Put("/chats")
    @ExecuteOn(TaskExecutors.IO)
    public HttpResponse<ChatDTO> updateChat(@Body ChatDTO chatDTO) throws URISyntaxException {
        log.debug("REST request to update Chat : {}", chatDTO);
        if (chatDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ChatDTO result = chatService.save(chatDTO);
        return HttpResponse.ok(result).headers(headers ->
            HeaderUtil.createEntityUpdateAlert(headers, applicationName, true, ENTITY_NAME, chatDTO.getId().toString()));
    }

    /**
     * {@code GET  /chats} : get all the chats.
     *
     * @return the {@link HttpResponse} with status {@code 200 (OK)} and the list of chats in body.
     */
    @Get("/chats")
    @ExecuteOn(TaskExecutors.IO)
    public Iterable<ChatDTO> getAllChats(HttpRequest request) {
        log.debug("REST request to get all Chats");
        return chatService.findAll();
    }

    /**
     * {@code GET  /chats/:id} : get the "id" chat.
     *
     * @param id the id of the chatDTO to retrieve.
     * @return the {@link HttpResponse} with status {@code 200 (OK)} and with body the chatDTO, or with status {@code 404 (Not Found)}.
     */
    @Get("/chats/{id}")
    @ExecuteOn(TaskExecutors.IO)
    public Optional<ChatDTO> getChat(@PathVariable Long id) {
        log.debug("REST request to get Chat : {}", id);

        return chatService.findOne(id);
    }

    /**
     * {@code DELETE  /chats/:id} : delete the "id" chat.
     *
     * @param id the id of the chatDTO to delete.
     * @return the {@link HttpResponse} with status {@code 204 (NO_CONTENT)}.
     */
    @Delete("/chats/{id}")
    @ExecuteOn(TaskExecutors.IO)
    public HttpResponse deleteChat(@PathVariable Long id) {
        log.debug("REST request to delete Chat : {}", id);
        chatService.delete(id);
        return HttpResponse.noContent().headers(headers -> HeaderUtil.createEntityDeletionAlert(headers, applicationName, true, ENTITY_NAME, id.toString()));
    }
}
