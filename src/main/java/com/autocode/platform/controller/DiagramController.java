package com.autocode.platform.controller;

import com.autocode.platform.agent.DiagramGenerationAgent;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/diagram")
@Tag(name = "Diagram Generator", description = "Generates architecture, HLD, LLD, and sequence flow diagrams as images")
public class DiagramController {

    private final DiagramGenerationAgent diagramAgent;

    public DiagramController(DiagramGenerationAgent diagramAgent) {
        this.diagramAgent = diagramAgent;
    }

    @Operation(summary = "Generate High Level Design Diagram")
    @GetMapping(value = "/hld", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] hld() {
        return diagramAgent.generateHLDImage();
    }

    @Operation(summary = "Generate Architecture Diagram")
    @GetMapping(value = "/architecture", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] architecture() {
        return diagramAgent.generateArchitectureImage();
    }

    @Operation(summary = "Generate Low Level Design Diagram")
    @GetMapping(value = "/lld", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] lld() {
        return diagramAgent.generateLLDImage();
    }

    @Operation(summary = "Generate Sequence Flow Diagram")
    @GetMapping(value = "/sequence", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] sequence() {
        return diagramAgent.generateSequenceImage();
    }
}
