package base.appstore.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AppControllerNonAuthenticatedTest {

  @Autowired
  private MockMvc mockMvc;


  @Test
  public void tryGet() throws Exception {
    mockMvc.perform(get("/apps")).andExpect(status().isUnauthorized());
  }

  @Test
  public void tryPost() throws Exception {
    mockMvc.perform(post("/apps").content("{\"title\":\"Test\",\"text\":\"test test\",\"tags\":\"test\"}")
            .contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(status().isUnauthorized());
  }

  @Test
  public void tryPut() throws Exception {
    mockMvc.perform(put("/apps/1").content("{\"title\":\"Test\",\"text\":\"test test\",\"tags\":\"test\"}")
            .contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(status().isUnauthorized());
  }

  @Test
  public void deleteTest() throws Exception {
    mockMvc.perform(delete("/apps/1")).andExpect(status().isUnauthorized());
  }
}

