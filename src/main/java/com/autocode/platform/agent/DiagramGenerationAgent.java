package com.autocode.platform.agent;

public interface DiagramGenerationAgent {
    byte[] generateHLDImage();
    byte[] generateArchitectureImage();
    byte[] generateLLDImage();
    byte[] generateSequenceImage();
}
