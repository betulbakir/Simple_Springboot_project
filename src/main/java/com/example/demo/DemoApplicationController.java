package com.example.demo;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

@RestController
@RequestMapping
public class DemoApplicationController {

    private final String fileName = "sample.json";

    @PostMapping("/upload")
    public ResponseEntity<String> uploadJsonFile(@RequestBody Map<String, Integer> requestBody) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File(fileName);
        if (!file.exists()) {
            file.createNewFile();
        }

        objectMapper.writeValue(file, requestBody);

        return ResponseEntity.ok("File uploaded successfully.");
    }

    @PutMapping("/update")
    public String updateJsonFile(@RequestBody JsonNode json) throws IOException {

        Path path = Paths.get("sample.json");
        String jsonString = Files.readString(path);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(jsonString);

        ((ObjectNode) rootNode).put("valueX", json.get("valueX").asText());
        ((ObjectNode) rootNode).put("valueY", json.get("valueY").asText());

        jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode);

        Files.writeString(path, jsonString);

        return "JSON file updated successfully";
    }

    @GetMapping("/addResult")
    public ResponseEntity<String> addResultJsonFile(@RequestBody Map<String, Integer> requestBody) {

        try {

            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(new File(fileName));

            int valueX = rootNode.get("valueX").asInt();
            int valueY = rootNode.get("valueY").asInt();

            requestBody.put("result", valueX + valueY);
            mapper.writeValue(new File(fileName), requestBody);

            return ResponseEntity.ok("Result written to JSON file");

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while writing to the JSON file");
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteJsonFile(String fileName) {

        File file = new File(this.fileName);
        if (file.delete()) {
            return ResponseEntity.ok("JSON file deleted");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while deleting the JSON file");
        }
    }

}
