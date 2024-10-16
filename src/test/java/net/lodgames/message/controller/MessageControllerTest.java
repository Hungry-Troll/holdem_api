package net.lodgames.message.controller;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class MessageControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ApplicationContext context;


    @Before
    public void setUp() throws Exception {

    }

    @Test
    void sendMessage() {

    }

    @Test
    void readMessage() {
    }

    @Test
    void deleteMessage() {
    }

    @Test
    void deleteAllMessage() {
    }

    @Test
    void updateMessage() {
    }

    @Test
    void checkSendAllMessage() {
    }
}