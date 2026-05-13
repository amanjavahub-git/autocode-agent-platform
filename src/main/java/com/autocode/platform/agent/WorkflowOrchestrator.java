package com.autocode.platform.agent;

import org.springframework.stereotype.Component;

@Component
public class WorkflowOrchestrator {

    private final RequirementReaderAgent readerAgent;
    private final CodeGeneratorAgent generatorAgent;

    public WorkflowOrchestrator(RequirementReaderAgent readerAgent, CodeGeneratorAgent generatorAgent) {
        this.readerAgent = readerAgent;
        this.generatorAgent = generatorAgent;
    }

    public void execute(String sourcePath) {
        // Step 1: Read requirement
        String requirement = readerAgent.readRequirement(sourcePath);
        System.out.println("Requirement: " + requirement);

        // Step 2: Generate code
        String code = generatorAgent.generateCode(requirement);
        System.out.println("Generated Code:\n" + code);
    }
}
