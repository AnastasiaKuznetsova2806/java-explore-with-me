package ewmmainservice.request.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ewmmainservice.request.Status;
import ewmmainservice.request.dto.ParticipationRequestDto;
import ewmmainservice.request.service.RequestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Collections;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = RequestController.class)
@AutoConfigureMockMvc
class RequestControllerTest {
    @Autowired
    private ObjectMapper mapper;
    @MockBean
    private RequestService service;
    @Autowired
    private MockMvc mvc;
    private LocalDateTime dateTime;
    private  ParticipationRequestDto requestDto;

    @BeforeEach
    void setParam() {
        dateTime = LocalDateTime.now().withNano(0);
        requestDto = new ParticipationRequestDto(dateTime, 1L, 1L, 1L, Status.PENDING);
    }
    
    @Test
    void createRequest() throws Exception {
        createRequest(requestDto);

        mvc.perform(post("/users/{userId}/requests", 1L)
                        .content(mapper.writeValueAsString(requestDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("eventId", String.valueOf(1L))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.created", is(requestDto.getCreated().toString())))
                .andExpect(jsonPath("$.event", is(requestDto.getEvent()), Long.class))
                .andExpect(jsonPath("$.id", is(requestDto.getId()), Long.class))
                .andExpect(jsonPath("$.requester", is(requestDto.getRequester()), Long.class))
                .andExpect(jsonPath("$.status", is(requestDto.getStatus().toString())));
    }

    @Test
    void updateRequest() throws Exception {
        createRequest(requestDto);
        ParticipationRequestDto requestUpdateDto = new ParticipationRequestDto(
                dateTime, 1L, 1L, 1L, Status.CANCELED
        );

        when(service.updateRequest(anyLong(), anyLong()))
                .thenReturn(requestUpdateDto);

        mvc.perform(patch("/users/{userId}/requests/{requestId}/cancel", 1L, 1L)
                        .content(mapper.writeValueAsString(requestDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.created", is(requestDto.getCreated().toString())))
                .andExpect(jsonPath("$.event", is(requestDto.getEvent()), Long.class))
                .andExpect(jsonPath("$.id", is(requestDto.getId()), Long.class))
                .andExpect(jsonPath("$.requester", is(requestDto.getRequester()), Long.class))
                .andExpect(jsonPath("$.status", is(Status.CANCELED.toString())));
    }

    @Test
    void findAllRequestsByUserId() throws Exception {
        createRequest(requestDto);

        when(service.findAllRequestsByUserId(anyLong()))
                .thenReturn(Collections.singletonList(requestDto));

        mvc.perform(get("/users/{userId}/requests", 1L)
                        .content(mapper.writeValueAsString(requestDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].created", is(requestDto.getCreated().toString())))
                .andExpect(jsonPath("$[0].event", is(requestDto.getEvent()), Long.class))
                .andExpect(jsonPath("$[0].id", is(requestDto.getId()), Long.class))
                .andExpect(jsonPath("$[0].requester", is(requestDto.getRequester()), Long.class))
                .andExpect(jsonPath("$[0].status", is(requestDto.getStatus().toString())));
    }

    private void createRequest(ParticipationRequestDto requestDto) {
        when(service.createRequest(anyLong(), anyLong()))
                .thenReturn(requestDto);
    }
}