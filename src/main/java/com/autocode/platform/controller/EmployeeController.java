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
