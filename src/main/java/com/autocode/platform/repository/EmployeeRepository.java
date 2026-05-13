    package com.autocode.platform.repository;

    import com.autocode.platform.entity.Employee;
    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.stereotype.Repository;

    @Repository
    public interface EmployeeRepository extends JpaRepository<Employee, Long> {}
