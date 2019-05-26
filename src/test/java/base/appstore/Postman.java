package base.appstore;

import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class Postman {

    private final MockMvc mockMvc;
    private MockHttpServletRequestBuilder builder;

    public Postman(MockHttpServletRequestBuilder builder, MockMvc mockMvc) {
        this.builder = builder;
        this.mockMvc = mockMvc;
    }

    public static Postman get(String urlTemplate, MockMvc mockMvc, Object... uriVars) {
        return new Postman(MockMvcRequestBuilders.get(urlTemplate), mockMvc);
    }

    public static Postman post(String urlTemplate, MockMvc mockMvc, Object... uriVars) {
        return new Postman(MockMvcRequestBuilders.post(urlTemplate), mockMvc);
    }

    public static Postman delete(String urlTemplate, MockMvc mockMvc, Object... uriVars) {
        return new Postman(MockMvcRequestBuilders.delete(urlTemplate), mockMvc);
    }

    public static Postman put(String urlTemplate, MockMvc mockMvc, Object... uriVars) {
        return new Postman(MockMvcRequestBuilders.put(urlTemplate), mockMvc);
    }

    public MockHttpServletRequestBuilder as(Role role) throws Exception {
        return this.builder.header("Authorization", "Bearer " + getToken(role));
    }

    private String getToken(Role role) throws Exception {
        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.post("/login")
                        .content(String.format("{\"username\": \"%s\",\"password\": \"%s\"}", role.getUsername(), role.getPassword()))
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk()).andReturn().getResponse();
        JacksonJsonParser jsonParser = new JacksonJsonParser();
        return (jsonParser.parseMap(response.getContentAsString()).get("token").toString());
    }


}
