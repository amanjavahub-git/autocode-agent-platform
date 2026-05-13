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

        @NotBlank @Pattern(regexp = "\\d{10}")
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
