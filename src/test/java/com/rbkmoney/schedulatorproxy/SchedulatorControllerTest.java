package com.rbkmoney.schedulatorproxy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rbkmoney.damsel.schedule.SchedulatorSrv;
import com.rbkmoney.schedulatorproxy.dto.DeregisterJobDto;
import com.rbkmoney.schedulatorproxy.dto.RegisterJobDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SchedulatorControllerTest {

    @MockBean
    private SchedulatorSrv.Iface schedulatorClient;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void registerJobTest() throws Exception {
        RegisterJobDto registerJobDto = RegisterJobDto.builder()
                .jobId("testJobId")
                .servicePath("testServicePath")
                .schedulerId(64)
                .build();
        String json = objectMapper.writer().withDefaultPrettyPrinter().writeValueAsString(registerJobDto);
        mvc.perform(post("/schedulator/proxy/register")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void registerJobValidationTest() throws Exception {
        RegisterJobDto registerJobDto = RegisterJobDto.builder()
                .jobId("testJobId")
                .build();
        String json = objectMapper.writer().withDefaultPrettyPrinter().writeValueAsString(registerJobDto);
        mvc.perform(post("/schedulator/proxy/register")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json))
                .andDo(print())
                .andExpect(status().is4xxClientError());

    }

    @Test
    public void deregisterJobTest() throws Exception {
        DeregisterJobDto deregisterJobDto = new DeregisterJobDto("testJobId");
        String json = objectMapper.writer().withDefaultPrettyPrinter().writeValueAsString(deregisterJobDto);
        mvc.perform(post("/schedulator/proxy/deregister")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void deregisterJobTestValidationTest() throws Exception {
        DeregisterJobDto deregisterJobDto = new DeregisterJobDto("");
        String json = objectMapper.writer().withDefaultPrettyPrinter().writeValueAsString(deregisterJobDto);
        mvc.perform(post("/schedulator/proxy/deregister")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }


}
