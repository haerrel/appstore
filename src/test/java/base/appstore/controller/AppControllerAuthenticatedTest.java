package base.appstore.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
  

  public String doLogin() throws Exception{
    MockHttpServletResponse response = mockMvc
        .perform(post("/login")
            .content("{\"username\": \"DemoAccount\",\"password\": \"DemoPassword\"}")
            .contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(status().isOk()).andReturn().getResponse();
    JacksonJsonParser jsonParser = new JacksonJsonParser();
    return (jsonParser.parseMap(response.getContentAsString()).get("token").toString());
  }
  
  @Test
  public void listAllTest() throws Exception {
    String token = doLogin();
    mockMvc.perform(get("/apps").header("Authorization", "Bearer " + token)).andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(content().string("[]"));
  }

  @Test
  public void createTest() throws Exception {
    String token = doLogin();
    MockHttpServletResponse response = mockMvc
        .perform(post("/apps").header("Authorization", "Bearer " + token)
            .content("{\"title\":\"Test\",\"text\":\"test test\",\"tags\":\"test\"}")
            .contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(status().isOk()).andReturn().getResponse();
    char id = response.getContentAsString().charAt(6);
    mockMvc.perform(delete("/apps/" + id).header("Authorization", "Bearer " + token));
  }

  

  @Test
  public void findTest() throws Exception {
    String token = doLogin();
    MockHttpServletResponse response = mockMvc
        .perform(post("/apps").header("Authorization", "Bearer " + token)
            .content("{\"title\":\"Test\",\"text\":\"test test\",\"tags\":\"test\"}")
            .contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(status().isOk()).andReturn().getResponse();
    char id = response.getContentAsString().charAt(6);
    mockMvc.perform(get("/apps/" + id).header("Authorization", "Bearer " + token)).andExpect(status().isOk());
    mockMvc.perform(delete("/apps/" + id).header("Authorization", "Bearer " + token));
  }

  @Test
  public void deleteTest() throws Exception {
    String token = doLogin();
    MockHttpServletResponse response = mockMvc
        .perform(post("/apps").header("Authorization", "Bearer " + token)
            .content("{\"title\":\"Test\",\"text\":\"test test\",\"tags\":\"test\"}")
            .contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(status().isOk()).andReturn().getResponse();
    char id = response.getContentAsString().charAt(6);
    mockMvc.perform(delete("/apps/" + id).header("Authorization", "Bearer " + token)).andExpect(status().isOk());
  }

  @Test
  public void updateTest() throws Exception {
    String token = doLogin();
    MockHttpServletResponse response = mockMvc
        .perform(post("/apps").header("Authorization", "Bearer " + token)
            .content("{\"title\":\"Test\",\"text\":\"test test\",\"tags\":\"test\"}")
            .contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(status().isOk()).andReturn().getResponse();
    char id = response.getContentAsString().charAt(6);
    mockMvc.perform(put("/apps/" + id).header("Authorization", "Bearer " + token)
        .content("{\"title\":\"TestTest\",\"text\":\"test test\",\"tags\":\"test\"}")
        .contentType("application/json")).andExpect(status().isOk());
    mockMvc.perform(delete("/app/" + id));
  }

  @Test
  public void updateTestFailed() throws Exception {
    String token = doLogin();
    mockMvc.perform(put("/apps/" + 0).header("Authorization", "Bearer " + token)
        .content("{\"title\":\"TestTest\",\"text\":\"test test\",\"tags\":\"test\"}")
        .contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
  }

}
