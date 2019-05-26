package base.appstore.controller;

import static base.appstore.Postman.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import base.appstore.Role;
import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.junit.BeforeClass;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AppControllerAuthenticatedTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void listAllTest() throws Exception {
        mockMvc.perform(get("/apps", mockMvc).as(Role.ADMIN)).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().string(Matchers.startsWith("[")));
    }

    @Test
    public void createTest() throws Exception {
        MockHttpServletResponse response = mockMvc
                .perform(post("/apps", mockMvc).as(Role.ADMIN)
                        .content("{\"title\":\"Test\",\"text\":\"test test\",\"tags\":\"test\"}")
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk()).andReturn().getResponse();
        String id = new JSONObject(response.getContentAsString()).getString("id");
        mockMvc.perform(put("/apps/" + id, mockMvc).as(Role.ADMIN)
                .content("{\"title\":\"TestTest\",\"text\":\"test test test\",\"tags\": [{\"text\":\"test1\"}]}")
                .contentType("application/json"));
        mockMvc.perform(delete("/apps/" + id, mockMvc).as(Role.ADMIN));
    }


    @Test
    public void findTest() throws Exception {
        MockHttpServletResponse response = mockMvc
                .perform(post("/apps", mockMvc).as(Role.ADMIN)
                        .content("{\"title\":\"Test\",\"text\":\"test test\",\"tags\":\"test\"}")
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk()).andReturn().getResponse();
        String id = new JSONObject(response.getContentAsString()).getString("id");
        mockMvc.perform(get("/apps/" + id, mockMvc).as(Role.ADMIN)).andExpect(status().isOk());
        mockMvc.perform(delete("/apps/" + id, mockMvc).as(Role.ADMIN));
    }

    @Test
    public void deleteTest() throws Exception {
        MockHttpServletResponse response = mockMvc
                .perform(post("/apps", mockMvc).as(Role.ADMIN)
                        .content("{\"title\":\"Test\",\"text\":\"test test\",\"tags\":\"test\"}")
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk()).andReturn().getResponse();
        String id = new JSONObject(response.getContentAsString()).getString("id");
        mockMvc.perform(delete("/apps/" + id, mockMvc).as(Role.ADMIN)).andExpect(status().isOk());
    }

    @Test
    public void updateTest() throws Exception {
        MockHttpServletResponse response = mockMvc
                .perform(post("/apps", mockMvc).as(Role.ADMIN)
                        .content("{\"title\":\"Test\",\"text\":\"test test\",\"tags\":\"test\"}")
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk()).andReturn().getResponse();
        String id = new JSONObject(response.getContentAsString()).getString("id");
        mockMvc.perform(put("/apps/" + id, mockMvc).as(Role.ADMIN)
                .content("{\"title\":\"Test2\",\"text\":\"test test\",\"tags\":\"test\"}")
                .contentType("application/json")).andExpect(status().isOk());
        mockMvc.perform(delete("/app/" + id, mockMvc).as(Role.ADMIN));
    }

    @Test
    public void updateTestFailed() throws Exception {
        mockMvc.perform(put("/apps/" + 0, mockMvc).as(Role.ADMIN)
                .content("{\"title\":\"Test\",\"text\":\"test test\",\"tags\":\"test\"}")
                .contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
    }

}
