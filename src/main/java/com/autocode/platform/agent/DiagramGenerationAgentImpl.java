package com.autocode.platform.agent;

import net.sourceforge.plantuml.SourceStringReader;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;

@Component
public class DiagramGenerationAgentImpl implements DiagramGenerationAgent {

    @Override
    public byte[] generateHLDImage() {
        String uml = """
            @startuml
            actor User
            User -> AgentController: /api/workflow/run
            AgentController -> WorkflowOrchestrator: execute()
            WorkflowOrchestrator -> RequirementReaderAgent: readRequirement()
            WorkflowOrchestrator -> CodeGeneratorAgent: generateCode()
            CodeGeneratorAgent -> GeneratedClasses: Entity, Repo, Service, Controller
            GeneratedClasses -> EmployeeController: CRUD Endpoints
            EmployeeController -> EmployeeService: save/find/delete
            EmployeeService -> EmployeeRepository: JPA calls
            EmployeeRepository -> MySQL: SQL Insert/Select
            @enduml
        """;
        return renderDiagram(uml);
    }

    @Override
    public byte[] generateArchitectureImage() {
        String uml = """
            @startuml
            package "Presentation Layer" {
              [Swagger UI] --> [AgentController]
            }
            package "Orchestration Layer" {
              [AgentController] --> [WorkflowOrchestrator]
              [WorkflowOrchestrator] --> [RequirementReaderAgent]
              [WorkflowOrchestrator] --> [CodeGeneratorAgent]
            }
            package "Service Layer" {
              [EmployeeService]
            }
            package "Persistence Layer" {
              [EmployeeRepository] --> [Hibernate ORM]
            }
            package "Database Layer" {
              [MySQL Database]
            }
            [EmployeeService] --> [EmployeeRepository]
            [Hibernate ORM] --> [MySQL Database]
            @enduml
        """;
        return renderDiagram(uml);
    }

    @Override
    public byte[] generateLLDImage() {
        String uml = """
            @startuml
            class RequirementReaderAgentImpl {
              +readRequirement(String sourcePath)
            }
            class CodeGeneratorAgentImpl {
              +generateCode(String requirement)
            }
            class WorkflowOrchestrator {
              +execute(String sourcePath)
            }
            class AgentController {
              +runWorkflow(String sourcePath)
            }
            class EmployeeController {
              +create(Employee)
              +findAll()
            }
            class EmployeeService {
              +save(Employee)
              +findAll()
            }
            class EmployeeRepository {
              +save(Employee)
              +findAll()
            }
            RequirementReaderAgentImpl --> WorkflowOrchestrator
            CodeGeneratorAgentImpl --> WorkflowOrchestrator
            WorkflowOrchestrator --> AgentController
            AgentController --> EmployeeController
            EmployeeController --> EmployeeService
            EmployeeService --> EmployeeRepository
            EmployeeRepository --> MySQL
            @enduml
        """;
        return renderDiagram(uml);
    }

    @Override
    public byte[] generateSequenceImage() {
        String uml = """
            @startuml
            actor User
            User -> AgentController: runWorkflow()
            AgentController -> WorkflowOrchestrator: execute()
            WorkflowOrchestrator -> RequirementReaderAgent: readRequirement()
            WorkflowOrchestrator -> CodeGeneratorAgent: generateCode()
            CodeGeneratorAgent -> GeneratedClasses: create Entity/Repo/Service/Controller
            User -> EmployeeController: create(Employee)
            EmployeeController -> EmployeeService: save(Employee)
            EmployeeService -> EmployeeRepository: save(e)
            EmployeeRepository -> Hibernate: SQL Insert
            Hibernate -> MySQL: persist record
            MySQL --> User: Response
            @enduml
        """;
        return renderDiagram(uml);
    }

    private byte[] renderDiagram(String uml) {
        try {
            SourceStringReader reader = new SourceStringReader(uml);
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            reader.generateImage(os);
            return os.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate diagram image", e);
        }
    }
}
