package base.appstore;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.json.JSONArray;
import org.json.JSONException;
import org.junit.Assert;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.regex.Pattern;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AppControllerTest {

  @Autowired
  private MockMvc mockMvc;

  private List<String> getApps() {
    return List.of(
        "{\"text\":\"Erste und neueste App\",\"tags\":\"neu,fancy\",\"title\":\"First App\",\"price\":1,\"datePublished\":\"2019-04-28\"}",
        "{\"text\":\"Eine weitere App\",\"tags\":\"neu,fancy\",\"title\":\"First2 App\",\"price\":5,\"datePublished\":\"2019-04-29\"}",
        "{\"text\":\"Zweite App\",\"tags\":\"2,fancy\",\"title\":\"Second App\",\"price\":2,\"datePublished\":\"2019-04-30\"}",
        "{\"text\":\"Dritte App\",\"tags\":\"3,oldschool\",\"title\":\"Third App\",\"price\":3,\"datePublished\":\"2019-04-31\"}"
    );
  }

  private void propagateApps() throws Exception {
    for (String app : getApps()) {
      mockMvc.perform(post("/apps")
              .content(app)
              .contentType(MediaType.APPLICATION_JSON_UTF8))
              .andExpect(status().isOk());
    }
  }

  private MockHttpServletResponse getResponse(String URL) throws Exception {
     return mockMvc.perform(get(URL)).andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andReturn().getResponse();

  }

  private void verifyByTitle(JSONArray json, String... expected) throws JSONException {
    for (int i = 0; i < json.length(); i++) {
      Assert.assertEquals(expected[i], json.getJSONObject(i).get("title"));
    }

  }

  @Test
  public void listAllTest() throws Exception {
    mockMvc.perform(get("/apps")).andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(content().string("[]"));
  }

  @Test
  public void listAllTestSearchParam() throws Exception {
    propagateApps();
    MockHttpServletResponse response = getResponse("/apps?search=First");

    JSONArray json = new JSONArray(response.getContentAsString());
    Assert.assertEquals(2, json.length());
    verifyByTitle(json, "First App", "First2 App");
  }

  @Test
  public void listAllTestFilterParamCheapest() throws Exception {
    propagateApps();
    MockHttpServletResponse response = getResponse("/apps?filter=cheapest");

    JSONArray json = new JSONArray(response.getContentAsString());
    Assert.assertEquals(4, json.length());
    verifyByTitle(json, "First App", "Second App", "Third App", "First2 App");
  }

  @Test
  public void listAllTestFilterParamNewest() throws Exception {
    propagateApps();
    MockHttpServletResponse response = getResponse("/apps?filter=newest");

    JSONArray json = new JSONArray(response.getContentAsString());
    Assert.assertEquals(4, json.length());
    verifyByTitle(json, "Third App", "Second App", "First2 App", "First App");
  }

  @Test
  public void listAllTestFilterParamFamous() throws Exception {
    propagateApps();
    MockHttpServletResponse response = getResponse("/apps?filter=famous");
    //TODO not implemented yet
//    JSONArray json = new JSONArray(response.getContentAsString());
//    Assert.assertEquals(4, json.length());
//    List<String> expected = List.of("Third App", "Second App", "First2 App", "First App");
  }

  @Test
  public void listAllTestLimitParam() throws Exception {
    propagateApps();
    MockHttpServletResponse response = getResponse("/apps?limit=2");

    JSONArray json = new JSONArray(response.getContentAsString());
    Assert.assertEquals(2, json.length());
  }

  @Test
  public void listAllTestTagParam() throws Exception {
    propagateApps();
    MockHttpServletResponse response = getResponse("/apps?tag=2");

    JSONArray json = new JSONArray(response.getContentAsString());
    Assert.assertEquals(2, json.length());
    verifyByTitle(json, "Second App");
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
