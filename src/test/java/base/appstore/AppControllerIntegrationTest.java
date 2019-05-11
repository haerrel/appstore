package base.appstore;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AppControllerIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void listAllTest() throws Exception {
    mockMvc.perform(get("/apps")).andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(content().string("[]"));
  }

  @Test
  public void createTest() throws Exception {
    MockHttpServletResponse response = mockMvc
            .perform(post("/apps")
                    .content("{\"title\":\"Test\",\"text\":\"test test\",\"tags\":\"test\"}")
                    .contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk()).andReturn().getResponse();
    char id = response.getContentAsString().charAt(6);
    mockMvc.perform(delete("/apps/" + id));
  }



  @Test
  public void findTest() throws Exception {
    MockHttpServletResponse response = mockMvc
            .perform(post("/apps")
                    .content("{\"title\":\"Test\",\"text\":\"test test\",\"tags\":\"test\"}")
                    .contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk()).andReturn().getResponse();
    char id = response.getContentAsString().charAt(6);
    mockMvc.perform(get("/apps/" + id)).andExpect(status().isOk());
    mockMvc.perform(delete("/apps/" + id));
  }

  @Test
  public void deleteTest() throws Exception {
    MockHttpServletResponse response = mockMvc
            .perform(post("/apps")
                    .content("{\"title\":\"Test\",\"text\":\"test test\",\"tags\":\"test\"}")
                    .contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk()).andReturn().getResponse();
    char id = response.getContentAsString().charAt(6);
    mockMvc.perform(delete("/apps/" + id)).andExpect(status().isOk());
  }

  @Test
  public void updateTest() throws Exception {
    MockHttpServletResponse response = mockMvc
            .perform(post("/apps")
                    .content("{\"title\":\"Test\",\"text\":\"test test\",\"tags\":\"test\"}")
                    .contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk()).andReturn().getResponse();
    char id = response.getContentAsString().charAt(6);
    mockMvc.perform(put("/apps/" + id)
            .content("{\"title\":\"TestTest\",\"text\":\"test test\",\"tags\":\"test\"}")
            .contentType("application/json")).andExpect(status().isOk());
    mockMvc.perform(delete("/app/" + id));
  }

  @Test
  public void updateTestFailed() throws Exception {
    mockMvc.perform(put("/apps/" + 0)
            .content("{\"title\":\"TestTest\",\"text\":\"test test\",\"tags\":\"test\"}")
            .contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
  }

}
