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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/query/geoclass-before-test.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/query/geoclass-after-test.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class GeoClassControllerTests {
    @Autowired
    private MockMvc mvc;

    @Test
    void findAllGeoClassesTest() throws Exception {
        String result = mvc.perform(get("/geoclasses")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        JSONAssert.assertEquals(
                "[{ id : 1," +
                        "name: \"Geo Class 11\"," +
                        "code: \"GC11\"}," +
                        "{id : 2," +
                        "name: \"Geo Class 12\"," +
                        "code: \"GC12\" }," +
                        "{id : 3," +
                        "name: \"Geo Class 21\"," +
                        "code: \"GC21\" }]", result, JSONCompareMode.STRICT);
    }

    @Test
    public void findGeoClassByIdTest() throws Exception {
        String result = mvc.perform(get("/geoclasses/{id}", 1))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        JSONAssert.assertEquals(
                "{ id : 1," +
                        "name: \"Geo Class 11\"," +
                        "code: \"GC11\" }",
                result, JSONCompareMode.STRICT);

    }

    @Test
    public void createGeoClassTest() throws Exception {
        String result = mvc.perform(post("/geoclasses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Geo Class 22\"," +
                                "\"code\": \"GC22\"}"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        JSONAssert.assertEquals(
                "{ id : 4," +
                        "name: \"Geo Class 22\"," +
                        "code: \"GC22\" }",
                result, JSONCompareMode.STRICT);
    }

    @Test
    public void updateGeoClassTest() throws Exception {
        mvc.perform(put("/geoclasses/{id}", 2)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Geo Class 8080\"," +
                                "\"code\":\"GC8080\"}"))
                .andExpect(status().isOk());

        String result = mvc.perform(get("/geoclasses/{id}", 2))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        JSONAssert.assertEquals(
                "{ id : 2," +
                        "name: \"Geo Class 8080\"," +
                        "code: \"GC8080\" }",
                result, JSONCompareMode.STRICT);
    }

    @Test
    public void deleteGeoClassTest() throws Exception {
        mvc.perform(delete("/geoclasses/{id}", 3))
                .andExpect(status().isOk());

        String result = mvc.perform(get("/geoclasses")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        JSONAssert.assertEquals(
                "[{ id : 1," +
                        "name: \"Geo Class 11\"," +
                        "code: \"GC11\"}," +
                        "{id : 2," +
                        "name: \"Geo Class 12\"," +
                        "code: \"GC12\" }]", result, JSONCompareMode.STRICT);
    }
}
