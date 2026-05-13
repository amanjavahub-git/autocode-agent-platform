# Autocode-Agent-Platform

## 📌 Overview
Autocode-Agent-Platform is a **Spring Boot based agentic AI system** that automatically reads requirements and generates **enterprise-grade code** (Entity, Repository, Service, Controller) along with **UML diagrams** (HLD, LLD, Architecture, Sequence).

This project demonstrates how autonomous agents can orchestrate workflows for enterprise automation.

---

## 🏗️ Class Descriptions

### RequirementReaderAgent
- **Role**: Reads and parses user requirements (JSON/spec/acceptance criteria).
- **Output**: Structured requirement object passed to `CodeGeneratorAgent`.

### CodeGeneratorAgent
- **Role**: Generates actual working code automatically.
- **Creates**:
    - **Entity** (e.g., Employee.java with fields)
    - **Repository** (EmployeeRepository extends JpaRepository)
    - **Service** (EmployeeService with CRUD methods)
    - **Controller** (EmployeeController with REST endpoints)
- **Output**: `.java` files saved in `GeneratedClasses` folder.

### DiagramGenerationAgent (Interface)
- **Role**: Defines contract for diagram agents.
- **Method**: `generateDiagram()` returns UML string.

### HldDiagramAgentImpl
- **Role**: Generates High-Level Design diagram.
- **Trigger**: `/api/diagram/hld`

### LldDiagramAgentImpl
- **Role**: Generates Low-Level Design diagram.
- **Trigger**: `/api/diagram/lld`

### ArchitectureDiagramAgentImpl
- **Role**: Generates system architecture diagram.
- **Trigger**: `/api/diagram/architecture`

### SequenceDiagramAgentImpl
- **Role**: Generates execution flow diagram (User → Controller → Service → Repo → DB).
- **Trigger**: `/api/diagram/sequence`

### WorkflowOrchestrator
- **Role**: Central orchestrator that decides which agent to invoke.
- **Flow**: RequirementReaderAgent → CodeGeneratorAgent → DiagramAgents.

### AgentController
- **Role**: Exposes REST endpoints via Swagger.
- **Flow**: User request → Orchestrator → Agents → Response (code/diagram).

---

## ⚙️ Configuration Note

### VM Option Required

### Why Needed?
- PlantUML + Graphviz internally use **JDK imageio classes** for PNG rendering.
- In Java 17+, these classes are encapsulated under the module system.
- Without this option, you may face **IllegalAccessError** when generating PNG diagrams.
- This option exports the `com.sun.imageio.plugins.png` package to **ALL-UNNAMED modules**, allowing PlantUML to safely generate PNG outputs.

👉 Add this option in **Run/Debug Configurations → VM Options**.

---

## 📊 Sequence Diagram

```plantuml
@startuml
actor User
User -> AgentController: API Request (/api/workflow/run)
AgentController -> WorkflowOrchestrator: execute()
WorkflowOrchestrator -> RequirementReaderAgent: readRequirement()
RequirementReaderAgent --> WorkflowOrchestrator: Requirement Object
WorkflowOrchestrator -> CodeGeneratorAgent: generateCode(requirement)
CodeGeneratorAgent --> WorkflowOrchestrator: Generated Classes
WorkflowOrchestrator -> DiagramGenerationAgent: generateDiagram()
DiagramGenerationAgent --> WorkflowOrchestrator: UML Diagram
WorkflowOrchestrator --> AgentController: Response (Code + Diagram)
AgentController --> User: Output via Swagger
@enduml
