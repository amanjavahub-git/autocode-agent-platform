package com.autocode.platform.agent;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class RequirementReaderAgentImpl implements RequirementReaderAgent {

    public String readRequirement(String sourcePath) {
        try {
            ClassPathResource resource = new ClassPathResource("requirements/employee-requirement.txt");
            Path path = resource.getFile().toPath();
            return Files.readString(path);
        } catch (Exception e) {
            throw new RuntimeException("Failed to read requirement file", e);
        }
    }
}
