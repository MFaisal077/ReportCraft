CREATE TABLE IF NOT EXISTS employees (
                                         id INT AUTO_INCREMENT PRIMARY KEY,
                                         name VARCHAR(100),
    department VARCHAR(100),
    salary DECIMAL(10, 2)
    );

INSERT INTO employees (name, department, salary) VALUES
                                                     ('Alice', 'Engineering', 60000),
                                                     ('Bob', 'Marketing', 50000),
                                                     ('Charlie', 'HR', 55000);

SELECT * FROM employees;
