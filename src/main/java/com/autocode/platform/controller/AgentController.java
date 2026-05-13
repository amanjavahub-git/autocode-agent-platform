package com.autocode.platform.controller;


import com.autocode.platform.agent.WorkflowOrchestrator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/workflow")
@Tag(
        name = "Workflow Controller",
        description = "Endpoints to orchestrate autonomous agents for requirement reading and code generation"
)
public class AgentController {

    private final WorkflowOrchestrator orchestrator;

    public AgentController(WorkflowOrchestrator orchestrator) {
        this.orchestrator = orchestrator;
    }

    @Operation(
            summary = "Run Workflow",
            description = "Reads requirements from the given source path (e.g., Jira, Confluence, PDF) and generates professional Spring Boot code using autonomous agents."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Workflow executed successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid source path provided"),
            @ApiResponse(responseCode = "500", description = "Internal server error while executing workflow")
    })
    @GetMapping("/run")
    public String runWorkflow(@RequestParam String sourcePath) {
        orchestrator.execute(sourcePath);
        return "Workflow executed successfully for source: " + sourcePath;
    }
}
