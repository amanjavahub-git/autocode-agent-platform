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
