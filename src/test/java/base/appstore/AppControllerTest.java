package base.appstore;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AppControllerTest {
    @Autowired
    private MockMvc mockMvc;


}


/*
@RunWith(SpringRunner.class)
@WebMvcTest(AppController.class)
@AutoConfigureMockMvc
public class AppControllerTest {


  TODO java.lang.IllegalStateException: Failed to load ApplicationContext

  @MockBean
  private AppRepository appRepository;

  @MockBean
  private RatingRepository ratingRepository;

  @Autowired
  private MockMvc mockMvc;

  private MockHttpServletResponse getResponse(String URL) throws Exception {
     return mockMvc.perform(get(URL)).andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andReturn().getResponse();

  }

  @Test
  public void listAllTest() throws Exception {
    mockMvc.perform(get("/apps")).andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(content().string("[]"));
  }

  @Test
  public void listAllTestSearchParam() throws Exception {
    List<App> apps = Arrays.asList(new App(), new App(), new App(), new App(), new App());
    apps.get(0).setTitle("WhatsApp");
    apps.get(1).setTitle("ICQ");
    apps.get(2).setTitle("WolframAlpha");
    apps.get(3).setTitle("WolframAlpha2");
    apps.get(4).setTitle("Facebook");

    when(appRepository.findAll()).thenReturn(apps);

    MockHttpServletResponse response = getResponse("/apps?search=W");
    JSONArray actual = new JSONArray(response.getContentAsString());

    Assert.assertEquals(3, actual.length());
    int[] expected = new int[]{0, 2, 3};
    for (int i = 0; i < actual.length(); i++) {
      Assert.assertEquals(actual.getJSONObject(i).get("title"), apps.get(expected[i]).getTitle());
    }
  }

  @Test
  public void listAllTestFilterParamCheapest() throws Exception {
    List<App> apps = Arrays.asList(new App(), new App(), new App(), new App());
    apps.get(0).setPrice(10);
    apps.get(1).setPrice(3);
    apps.get(2).setPrice(0);
    apps.get(3).setPrice(9);

    when(appRepository.findAll()).thenReturn(apps);

    MockHttpServletResponse response = getResponse("/apps?filter=cheapest");
    JSONArray actual = new JSONArray(response.getContentAsString());

    Assert.assertEquals(4, actual.length());
    int[] expected = new int[]{2, 1, 3, 0};
    for (int i = 0; i < actual.length(); i++) {
      Assert.assertEquals(actual.getJSONObject(i).get("price"), apps.get(expected[i]).getPrice());
    }
  }

  @Test
  public void listAllTestFilterParamNewest() throws Exception {
    List<App> apps = Arrays.asList(new App(), new App(), new App(), new App());
    apps.get(0).setDatePublished("2019-01-01");
    apps.get(1).setDatePublished("2014-04-04");
    apps.get(2).setDatePublished("2016-03-25");
    apps.get(3).setDatePublished("2016-02-26");

    when(appRepository.findAll()).thenReturn(apps);

    MockHttpServletResponse response = getResponse("/apps?filter=newest");
    JSONArray actual = new JSONArray(response.getContentAsString());

    Assert.assertEquals(4, actual.length());
    int[] expected = new int[]{1, 3, 2, 0};
    for (int i = 0; i < actual.length(); i++) {
      Assert.assertEquals(actual.getJSONObject(i).get("datePublished"), apps.get(expected[i]).getDatePublished());
    }
  }

  @Test
  public void listAllTestFilterParamFamous() throws Exception {
//    propagateApps();
    MockHttpServletResponse response = getResponse("/apps?filter=famous");
    //TODO not implemented yet
//    JSONArray json = new JSONArray(response.getContentAsString());
//    Assert.assertEquals(4, json.length());
//    List<String> expected = List.of("Third App", "Second App", "First2 App", "First App");
  }

  @Test
  public void listAllTestLimitParam() throws Exception {
    List<App> apps = Arrays.asList(new App(), new App(), new App(), new App());
    when(appRepository.findAll()).thenReturn(apps);

    MockHttpServletResponse response = getResponse("/apps?limit=2");

    JSONArray json = new JSONArray(response.getContentAsString());
    Assert.assertEquals(2, json.length());
  }

  @Test
  public void listAllTestTagParam() throws Exception {
    List<App> apps = Arrays.asList(
      new App("Populärer Instant Messenger", "IM,WhatsUp", "WhatsApp", 0, "2012-04-01"),
      new App("Soziales Netzwerk", "Datenkracke,IM", "Facebook Messenger", 0, "2013-05-02"),
      new App("Voice Chat Anwendung", "Realtime", "Google Hangouts", 0, "2015-08-12"),
      new App("PDF Annotierer", "TeuerAberToll", "PDF Annotator", 10, "2018-01-01")
    );
    when(appRepository.findAll()).thenReturn(apps);

    MockHttpServletResponse response = getResponse("/apps?tag=IM");

    JSONArray json = new JSONArray(response.getContentAsString());
    Assert.assertEquals(2, json.length());
    assertThat("WhatsApp", Matchers.either(Matchers.is(json.getJSONObject(0).get("title"))).or(Matchers.is(json.getJSONObject(1).get("title"))));
    assertThat("Facebook Messenger", Matchers.either(Matchers.is(json.getJSONObject(0).get("title"))).or(Matchers.is(json.getJSONObject(1).get("title"))));
  }

  @Test
  public void listAllTestTagParamWithMultipleValues() throws Exception {
    List<App> apps = Arrays.asList(
      new App("Populärer Instant Messenger", "IM,WhatsUp", "WhatsApp", 0, "2012-04-01"),
      new App("Soziales Netzwerk", "Datenkracke,IM", "Facebook Messenger", 0, "2013-05-02"),
      new App("Voice Chat Anwendung", "Realtime", "Google Hangouts", 0, "2015-08-12"),
      new App("PDF Annotierer", "TeuerAberToll", "PDF Annotator", 10, "2018-01-01")
    );
    when(appRepository.findAll()).thenReturn(apps);

    MockHttpServletResponse response = getResponse("/apps?tag=IM,Datenkracke");

    JSONArray json = new JSONArray(response.getContentAsString());
    Assert.assertEquals(1, json.length());
    Assert.assertEquals("Facebook Messenger", json.getJSONObject(0).get("title"));
  }

}
*/