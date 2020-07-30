DROP TABLE IF EXISTS employee;
DROP TABLE IF EXISTS company;
CREATE TABLE employee (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  age INT NOT NULL,
  gender VARCHAR(10) DEFAULT NULL,
  company_id INT Not NULL
);
--INSERT INTO employee (name, age, gender,company_id) VALUES
-- ('chengcheng','54','male',1),
-- ('yuexie','82','female',1),
-- ('haifeng','69','female',2),
-- ('ang','18','male',2);
 CREATE TABLE company (
   company_id INT AUTO_INCREMENT  PRIMARY KEY,
   name VARCHAR(100) NOT NULL
 );
-- INSERT INTO company (name) VALUES('tw'), VALUES('oocl');