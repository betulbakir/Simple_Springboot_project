package com.example.demo;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


public class DemoApplicationControllerTest {
    private final DemoApplicationController demoApplicationController = new DemoApplicationController();
    private final String fileName = "sample.json";

    @BeforeEach
    public void setUp() throws IOException {

        Path path = Paths.get(fileName);
        Files.deleteIfExists(path);
    }

    @Test
    public void uploadJsonFileTest() throws IOException {

        Map<String, Integer> requestBody = new HashMap<>();
        requestBody.put("valueX", 8);
        requestBody.put("valueY", 5);

        demoApplicationController.uploadJsonFile(requestBody);

        File file = new File(fileName);
        assertTrue(file.exists());
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(file);
        assertEquals(8, rootNode.get("valueX").asInt());
        assertEquals(5, rootNode.get("valueY").asInt());
    }

    @Test
    public void updateJsonFileTest() throws IOException {

        String initialJsonContent = "{\"valueX\":\"8\", \"valueY\":\"5\"}";
        Path path = Paths.get("sample.json");
        Files.writeString(path, initialJsonContent);

        String updatedJsonContent = "{\"valueX\":\"3\", \"valueY\":\"4\"}";
        JsonNode json = new ObjectMapper().readTree(updatedJsonContent);
        demoApplicationController.updateJsonFile(json);

        String actualJsonContent = Files.readString(path);

        JsonNode actualJson = new ObjectMapper().readTree(actualJsonContent);
        assertEquals("3", actualJson.get("valueX").asText());
        assertEquals("4", actualJson.get("valueY").asText());
    }

    @Test
    public void addResultJsonFileTest() throws IOException {

        Map<String, Integer> requestBody = new HashMap<>();
        requestBody.put("valueX", 8);
        requestBody.put("valueY", 5);
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File(fileName), requestBody);

        demoApplicationController.addResultJsonFile(requestBody);

        JsonNode updatedJsonNode = mapper.readTree(new File(fileName));

        assertEquals(13, updatedJsonNode.get("result").asInt());

    }

    @Test
    public void deleteJsonFileTest() throws IOException {

        File file = new File(fileName);
        file.createNewFile();

        demoApplicationController.deleteJsonFile(fileName);

        assertFalse(file.exists());
    }

}