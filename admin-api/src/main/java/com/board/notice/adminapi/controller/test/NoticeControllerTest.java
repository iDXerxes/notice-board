package com.board.notice.adminapi.controller.test;

import com.board.notice.adminapi.controller.NoticeController;
import com.board.notice.adminapi.service.notice.NoticeService;
import com.board.notice.domain.common.PageModel;
import com.board.notice.dto.notice.NoticeDto;
import com.board.notice.dto.notice.request.SearchNoticeRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(NoticeController.class) // Focuses on testing only the web layer for NoticeController
class NoticeControllerTest {

    @Autowired
    private MockMvc mockMvc; // Used to perform HTTP requests

    @Autowired
    private ObjectMapper objectMapper; // For converting objects to JSON and vice versa

    @MockBean
    private NoticeService noticeService; // Mocks the service layer

    @BeforeEach
    void setup() {
        // Register JavaTimeModule to handle LocalDateTime serialization/deserialization
        // This is good practice for tests involving dates/times in JSON
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    @DisplayName("공지 등록 성공 테스트 - 파일 없음")
    void createNotice_success_noFile() throws Exception {
        // Given
        NoticeDto noticeDto = NoticeDto.builder()
                .title("테스트 공지사항")
                .content("이것은 테스트 공지사항입니다.")
                .startDate(LocalDateTime.of(2025, 1, 1, 9, 0)) // Fixed date for reproducibility
                .endDate(LocalDateTime.of(2025, 1, 8, 17, 0))   // Fixed date
                                .build();

        String noticeDtoJson = objectMapper.writeValueAsString(noticeDto);
        MockMultipartFile noticeDtoPart = new MockMultipartFile(
                "noticeDto", // This must match @RequestPart name
                "",          // Original filename is empty for JSON part
                MediaType.APPLICATION_JSON_VALUE, // Content type for this part
                noticeDtoJson.getBytes(StandardCharsets.UTF_8) // Convert JSON string to bytes
        );

        // When/Then
        mockMvc.perform(MockMvcRequestBuilders.multipart("/notice/create")
                        .file(noticeDtoPart) // Add the JSON part
                        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)) // Overall content type
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.message").value("OK"));

        // Verify that the service method was called with a null fileList
        verify(noticeService, times(1)).createNotice(any(NoticeDto.class), isNull());
    }

    @Test
    @DisplayName("공지 등록 성공 테스트 - 파일 포함")
    void createNotice_success_withFile() throws Exception {
        // Given
        NoticeDto noticeDto = NoticeDto.builder()
                .title("테스트 공지사항 with 파일")
                .content("이것은 테스트 공지사항 with 파일입니다.")
                .startDate(LocalDateTime.of(2025, 2, 1, 9, 0))
                .endDate(LocalDateTime.of(2025, 2, 8, 17, 0))
                .build();

        String noticeDtoJson = objectMapper.writeValueAsString(noticeDto);
        MockMultipartFile noticeDtoPart = new MockMultipartFile(
                "noticeDto",
                "",
                MediaType.APPLICATION_JSON_VALUE,
                noticeDtoJson.getBytes(StandardCharsets.UTF_8)
        );

        MockMultipartFile mockFile = new MockMultipartFile(
                "file", // This must match @RequestPart(value = "file", ...)
                "test_file.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "file content for new notice".getBytes(StandardCharsets.UTF_8)
        );

        // When/Then
        mockMvc.perform(MockMvcRequestBuilders.multipart("/notice/create")
                        .file(mockFile)
                        .file(noticeDtoPart) // Add the JSON part
                        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.message").value("OK"));

        // Verify that the service method was called with a list of files
        verify(noticeService, times(1)).createNotice(any(NoticeDto.class), anyList());
    }

    @Test
    @DisplayName("공지 목록 조회 성공 테스트")
    void getNoticeList_success() throws Exception {
        // Given
        SearchNoticeRequest searchNoticeRequest = new SearchNoticeRequest();
        searchNoticeRequest.setKeyword("테스트");
        Pageable pageable = PageRequest.of(0, 10);

        NoticeDto notice1 = NoticeDto.builder().ntcId(1L).title("테스트 공지1").content("내용1").build();
        NoticeDto notice2 = NoticeDto.builder().ntcId(2L).title("테스트 공지2").content("내용2").build();
        List<NoticeDto> noticeList = Arrays.asList(notice1, notice2);
        PageModel<NoticeDto> pageModel = new PageModel<>(new PageImpl<>(noticeList, pageable, 2));

        given(noticeService.getNotices(any(SearchNoticeRequest.class), any(Pageable.class)))
                .willReturn(pageModel);

        // When/Then
        mockMvc.perform(MockMvcRequestBuilders.get("/notice/list")
                        .param("searchKeyword", "테스트")
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON)) // This is good practice even for GET
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.message").value("OK"))
                .andExpect(jsonPath("$.data.content[0].title").value("테스트 공지1"))
                .andExpect(jsonPath("$.data.totalElements").value(2));

        // Verify that the service method was called
        verify(noticeService, times(1)).getNotices(any(SearchNoticeRequest.class), any(Pageable.class));
    }

    @Test
    @DisplayName("공지 알림 조회 성공 테스트")
    void alertNotice_success() throws Exception {
        // Given
        NoticeDto alertNotice1 = NoticeDto.builder().ntcId(10L).title("긴급 공지").content("내용").build();
        List<NoticeDto> alertList = Collections.singletonList(alertNotice1);

        given(noticeService.getAlertNotices()).willReturn(alertList);

        // When/Then
        mockMvc.perform(MockMvcRequestBuilders.get("/notice/alert")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.message").value("OK"))
                .andExpect(jsonPath("$.data[0].title").value("긴급 공지"))
                .andExpect(jsonPath("$.data.length()").value(1));

        // Verify that the service method was called
        verify(noticeService, times(1)).getAlertNotices();
    }

    @Test
    @DisplayName("단일 공지 조회 성공 테스트")
    void getNotice_success() throws Exception {
        // Given
        Long ntcId = 1L;
        NoticeDto noticeDto = NoticeDto.builder()
                .ntcId(ntcId)
                .title("단일 공지")
                .content("단일 공지 내용")
                .build();

        given(noticeService.getNotice(ntcId)).willReturn(noticeDto);

        // When/Then
        mockMvc.perform(MockMvcRequestBuilders.get("/notice/{ntcId}", ntcId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.message").value("OK"))
                .andExpect(jsonPath("$.data.ntcId").value(ntcId))
                .andExpect(jsonPath("$.data.title").value("단일 공지"));

        // Verify that the service method was called
        verify(noticeService, times(1)).getNotice(ntcId);
    }

    @Test
    @DisplayName("공지 수정 성공 테스트 - 파일 없음")
    void updateNotice_success_noFile() throws Exception {
        // Given
        NoticeDto noticeDto = NoticeDto.builder()
                .ntcId(1L)
                .title("수정된 공지사항")
                .content("내용 수정")
                .startDate(LocalDateTime.of(2025, 3, 1, 9, 0))
                .endDate(LocalDateTime.of(2025, 3, 8, 17, 0))
                .build();

        String noticeDtoJson = objectMapper.writeValueAsString(noticeDto);
        MockMultipartFile noticeDtoPart = new MockMultipartFile(
                "noticeDto",
                "",
                MediaType.APPLICATION_JSON_VALUE,
                noticeDtoJson.getBytes(StandardCharsets.UTF_8)
        );

        // When/Then
        mockMvc.perform(MockMvcRequestBuilders.multipart("/notice/update")
                        .file(noticeDtoPart)
                        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.message").value("OK"));

        // Verify that the service method was called with a null fileList
        verify(noticeService, times(1)).updateNotice(any(NoticeDto.class), isNull());
    }

    @Test
    @DisplayName("공지 수정 성공 테스트 - 파일 포함")
    void updateNotice_success_withFile() throws Exception {
        // Given
        NoticeDto noticeDto = NoticeDto.builder()
                .ntcId(1L)
                .title("수정된 공지사항 with 파일")
                .content("내용 수정 with 파일")
                .startDate(LocalDateTime.of(2025, 4, 1, 9, 0))
                .endDate(LocalDateTime.of(2025, 4, 8, 17, 0))
                .build();

        String noticeDtoJson = objectMapper.writeValueAsString(noticeDto);
        MockMultipartFile noticeDtoPart = new MockMultipartFile(
                "noticeDto",
                "",
                MediaType.APPLICATION_JSON_VALUE,
                noticeDtoJson.getBytes(StandardCharsets.UTF_8)
        );

        MockMultipartFile mockFile = new MockMultipartFile(
                "file",
                "updated_file.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "updated file content".getBytes(StandardCharsets.UTF_8)
        );

        // When/Then
        mockMvc.perform(MockMvcRequestBuilders.multipart("/notice/update")
                        .file(mockFile)
                        .file(noticeDtoPart)
                        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.message").value("OK"));

        // Verify that the service method was called with a list of files
        verify(noticeService, times(1)).updateNotice(any(NoticeDto.class), anyList());
    }


    @Test
    @DisplayName("공지 삭제 성공 테스트")
    void deleteNotice_success() throws Exception {
        // Given
        Long ntcId = 1L;
        doNothing().when(noticeService).deleteNotice(ntcId); // Mock the void method

        // When/Then
        mockMvc.perform(MockMvcRequestBuilders.post("/notice/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ntcId))) // Send Long as JSON body
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.message").value("OK"));

        // Verify that the service method was called
        verify(noticeService, times(1)).deleteNotice(ntcId);
    }
}
