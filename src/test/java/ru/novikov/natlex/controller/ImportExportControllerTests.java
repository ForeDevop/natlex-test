package ru.novikov.natlex.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
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
@Sql(value = {"/query/import-export-before-test.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/query/import-export-after-test.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class ImportExportControllerTests {
    @Autowired
    private MockMvc mvc;

    @Test
    public void importFileTest() throws Exception {
        Resource resource = new ClassPathResource("/file/import-test.xlsx");
        MockMultipartFile file = new MockMultipartFile("file", null,
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                resource.getInputStream());

        String result = mvc.perform(multipart("/import").file(file))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        Assertions.assertThat(Long.parseLong(result) > 0);
    }

    @Test
    public void exportFileTest() throws Exception {
        String result = mvc.perform(get("/export"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        Assertions.assertThat(Long.parseLong(result) > 0);
    }

    @Test
    public void getImportStatusByIdTest() throws Exception {
        String result = mvc.perform(get("/import/{id}", 1))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        JSONAssert.assertEquals(
                "{ id : 1," +
                        "status: \"DONE\" }",
                result, JSONCompareMode.STRICT);

    }

    @Test
    public void getExportStatusByIdTest() throws Exception {
        String result = mvc.perform(get("/import/{id}", 2))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        JSONAssert.assertEquals(
                "{ id : 2," +
                        "status: \"IN PROGRESS\" }",
                result, JSONCompareMode.STRICT);
    }

    @Test
    public void getExportedFileTest() throws Exception {
        mvc.perform(get("/export/{id}/file", 1))
                .andExpect(status().isOk());
    }
}
