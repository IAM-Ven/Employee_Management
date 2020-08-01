DROP TABLE IF EXISTS employee;
DROP TABLE IF EXISTS company;
CREATE TABLE employee
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    name       VARCHAR(100) NOT NULL,
    age        INT          NOT NULL,
    gender     VARCHAR(10) DEFAULT NULL,
    company_id INT          Not NULL
);

CREATE TABLE company
(
    company_id INT AUTO_INCREMENT PRIMARY KEY,
    name       VARCHAR(100) NOT NULL
);