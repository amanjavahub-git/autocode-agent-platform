package com.autocode.platform.agent;

import org.springframework.stereotype.Component;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;

@Component
public class CodeGeneratorAgentImpl implements CodeGeneratorAgent {

    @Override
    public String generateCode(String requirement) {
        if (requirement.contains("Entity: Employee")) {
            try {
                // ✅ Generate Entity
                String entityCode = """
                    package com.autocode.platform.entity;

                    import jakarta.persistence.*;
                    import jakarta.validation.constraints.*;
                    import lombok.Data;
                    import java.time.LocalDate;

                    @Data
                    @Entity
                    @Table(name = "employees")
                    public class Employee {
                        @Id
                        @GeneratedValue(strategy = GenerationType.IDENTITY)
                        private Long id;

                        @NotBlank @Size(max = 50)
                        private String firstName;

                        @NotBlank @Size(max = 50)
                        private String lastName;

                        @NotBlank @Email @Column(unique = true)
                        private String email;

                        @NotBlank @Pattern(regexp = "\\\\d{10}")
                        private String phone;

                        @NotBlank
                        private String department;

                        @NotBlank
                        private String designation;

                        @NotNull
                        private LocalDate dateOfJoining;

                        @NotNull @Positive
                        private Double salary;
                    }
                """;
                writeFile("src/main/java/com/autocode/platform/entity/Employee.java", entityCode);

                // ✅ Generate Repository
                String repoCode = """
                    package com.autocode.platform.repository;

                    import com.autocode.platform.entity.Employee;
                    import org.springframework.data.jpa.repository.JpaRepository;
                    import org.springframework.stereotype.Repository;

                    @Repository
                    public interface EmployeeRepository extends JpaRepository<Employee, Long> {}
                """;
                writeFile("src/main/java/com/autocode/platform/repository/EmployeeRepository.java", repoCode);

                // ✅ Generate Service
                String serviceCode = """
                    package com.autocode.platform.service;

                    import com.autocode.platform.entity.Employee;
                    import com.autocode.platform.repository.EmployeeRepository;
                    import org.springframework.stereotype.Service;
                    import java.util.List;

                    @Service
                    public class EmployeeService {
                        private final EmployeeRepository repo;
                        public EmployeeService(EmployeeRepository repo) { this.repo = repo; }

                        public Employee save(Employee e) { return repo.save(e); }
                        public List<Employee> findAll() { return repo.findAll(); }
                        public Employee findById(Long id) { return repo.findById(id).orElse(null); }
                        public void delete(Long id) { repo.deleteById(id); }
                    }
                """;
                writeFile("src/main/java/com/autocode/platform/service/EmployeeService.java", serviceCode);

                // ✅ Generate Controller with Swagger Descriptions
                String controllerCode = """
                    package com.autocode.platform.controller;

                    import com.autocode.platform.entity.Employee;
                    import com.autocode.platform.service.EmployeeService;
                    import org.springframework.web.bind.annotation.*;
                    import java.util.List;
                    import io.swagger.v3.oas.annotations.Operation;

                    @RestController
                    @RequestMapping("/api/employees")
                    public class EmployeeController {
                        private final EmployeeService service;
                        public EmployeeController(EmployeeService service) { this.service = service; }

                        @Operation(summary = "Create Employee", description = "Insert a new employee record into the database")
                        @PostMapping
                        public Employee create(@RequestBody Employee e) { return service.save(e); }

                        @Operation(summary = "Fetch All Employees", description = "Retrieve all employee records from the database")
                        @GetMapping
                        public List<Employee> getAll() { return service.findAll(); }

                        @Operation(summary = "Fetch Employee by ID", description = "Retrieve employee details by ID")
                        @GetMapping("/{id}")
                        public Employee getById(@PathVariable Long id) { return service.findById(id); }

                        @Operation(summary = "Update Employee", description = "Update employee record for given ID")
                        @PutMapping("/{id}")
                        public Employee update(@PathVariable Long id, @RequestBody Employee e) {
                            e.setId(id);
                            return service.save(e);
                        }

                        @Operation(summary = "Delete Employee", description = "Delete employee record by ID")
                        @DeleteMapping("/{id}")
                        public String delete(@PathVariable Long id) {
                            service.delete(id);
                            return "Employee deleted successfully!";
                        }
                    }
                """;
                writeFile("src/main/java/com/autocode/platform/controller/EmployeeController.java", controllerCode);

                return "✅ Employee entity, repository, service, and controller generated successfully with Swagger descriptions.";
            } catch (Exception e) {
                throw new RuntimeException("Code generation failed", e);
            }
        }
        return "No entity requirement found.";
    }

    private void writeFile(String path, String content) throws IOException {
        File file = Paths.get(path).toFile();
        file.getParentFile().mkdirs(); // ✅ auto-create package folders if missing
        try (FileWriter writer = new FileWriter(file, false)) { // false → overwrite old file
            writer.write(content);
        }
    }
}
