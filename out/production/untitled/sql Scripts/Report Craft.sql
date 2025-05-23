-- departments table
CREATE TABLE departments (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50)
);

-- employees table
CREATE TABLE employees (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100),
    department_id INT,
    salary INT,
    hire_date DATE,
    FOREIGN KEY (department_id) REFERENCES departments(id)
);

-- sales table
CREATE TABLE sales (
    id INT PRIMARY KEY AUTO_INCREMENT,
    employee_id INT,
    product VARCHAR(50),
    amount INT,
    sale_date DATE,
    FOREIGN KEY (employee_id) REFERENCES employees(id)
);

select * from departments,employees,sales;
