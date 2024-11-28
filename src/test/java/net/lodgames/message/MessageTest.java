package net.lodgames.message;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.lodgames.message.controller.MessageController;
import net.lodgames.message.param.MessageAddParam;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MessageTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ApplicationContext context;
    @Autowired
    private MessageController messageController;
    @Autowired
    private ObjectMapper ObjectMapper;

    @BeforeEach
    public void setUp() throws Exception {

    }

    @DisplayName("메세지 전송 테스트")
    @Test
    void sendMessage() throws Exception {
        // given 요청 데이터 준비
        MessageAddParam messageAddParam = new MessageAddParam();
        messageAddParam.setSenderId(2L);
        messageAddParam.setContent("안녕하신가?");
        // when
        mockMvc.perform(post("/api/v1/message/sendMessage")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ObjectMapper.writeValueAsString(messageAddParam)))
                .andExpect(status().isOk()) // 상태 200 확인
                .andExpect(content().string("메세지 전송이 성공했습니다"));
        // then

    }

}
