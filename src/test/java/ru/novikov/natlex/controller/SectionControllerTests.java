package ru.novikov.natlex.controller;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/query/section-before-test.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/query/section-after-test.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class SectionControllerTests {
    @Autowired
    private MockMvc mvc;

    @Test
    void findAllSectionsTest() throws Exception {
        String result = mvc.perform(get("/sections")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        JSONAssert.assertEquals(
                "[{ id : 1," +
                        "name: \"Section 1\"," +
                        "geologicalClasses:[]}," +
                        "{id : 2," +
                        "name: \"Section 2\"," +
                        "geologicalClasses:[] }]", result, JSONCompareMode.STRICT);
    }

    @Test
    public void findSectionByIdTest() throws Exception {
        String result = mvc.perform(get("/sections/{id}", 1))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        JSONAssert.assertEquals(
                "{ id : 1," +
                        "name: \"Section 1\"," +
                        "geologicalClasses:[] }",
                result, JSONCompareMode.STRICT);

    }

    @Test
    public void createSectionTest() throws Exception {
        String result = mvc.perform(post("/sections")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Section 3\"," +
                                "\"geologicalClasses\": []}"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        JSONAssert.assertEquals(
                "{ id : 3," +
                        "name: \"Section 3\"," +
                        "geologicalClasses:[] }",
                result, JSONCompareMode.STRICT);
    }

    @Test
    public void updateSectionTest() throws Exception {
        mvc.perform(put("/sections/{id}", 2)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Section 200\"," +
                                "\"geologicalClasses\":[]}"))
                .andExpect(status().isOk());

        String result = mvc.perform(get("/sections/{id}", 2))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        JSONAssert.assertEquals(
                "{ id : 2," +
                        "name: \"Section 200\"," +
                        "geologicalClasses:[] }",
                result, JSONCompareMode.STRICT);
    }

    @Test
    public void deleteSectionTest() throws Exception {
        mvc.perform(delete("/sections/{id}", 2))
                .andExpect(status().isOk());

        String result = mvc.perform(get("/sections")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        JSONAssert.assertEquals(
                "[{ id : 1," +
                        "name: \"Section 1\"," +
                        "geologicalClasses:[] }]",
                result, JSONCompareMode.STRICT);
    }
}
