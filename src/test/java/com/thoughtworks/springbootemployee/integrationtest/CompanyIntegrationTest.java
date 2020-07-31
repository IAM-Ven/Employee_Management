package com.thoughtworks.springbootemployee.integrationtest;

import com.thoughtworks.springbootemployee.entity.Company;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CompanyIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    CompanyRepository companyRepository;

    @AfterEach
    void clean() {
        companyRepository.deleteAll();
    }

    @Test
    public void should_return_1_company_when_add_company_given_1_company() throws Exception {
        String companyContent = "{\"name\":\"cargosmart\"}";
        mockMvc.perform(
                post("/companies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(companyContent)).andExpect(status().isCreated());

        List<Company> companies = companyRepository.findAll();
        assertEquals(1, companies.size());
    }

    @Test
    public void should_return_1_company_when_get_company_given_1_company() throws Exception {
        Company company = new Company();
        company.setName("cargosmart");
        company = companyRepository.save(company);
        mockMvc.perform(
                get("/companies/" + company.getCompanyId()))
                .andExpect(status().isOk()).andExpect(jsonPath("name").value("cargosmart"));

    }

    @Test
    public void should_return_1_company_when_update_company_given_1_company() throws Exception {
        Company company = new Company();
        company.setName("cargosmart");

        company = companyRepository.save(company);

        String updateCompanyContent = "{\"name\":\"oocl\"}";
        mockMvc.perform(
                put("/companies/" + company.getCompanyId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateCompanyContent))
                .andExpect(status().isOk());
        List<Company> companies = companyRepository.findAll();
        assertEquals("oocl", companies.get(0).getName());
    }

    @Test
    void should_return_0_company_when_delete_company_given_1_company() throws Exception {
        Company company = new Company();
        company.setName("cargosmart");

        company = companyRepository.save(company);

        mockMvc.perform(delete("/companies/" + company.getCompanyId())).andExpect(status().isOk());
        List<Company> companies = companyRepository.findAll();
        assertEquals(0, companies.size());
    }


    @Test
    void should_return_1_when_get_company_by_page_given_2_companies_and_page_0_size_1() throws Exception {
        Company company1 = new Company();
        company1.setName("cargosmart");
        companyRepository.save(company1);

        Company company2 = new Company();
        company2.setName("oocl");
        companyRepository.save(company2);

        mockMvc.perform(get("/companies?page=0&size=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("numberOfElements").value(1));
    }


    @Test
    void should_return_1_employee_when_get_employee_by_company_id_given_1_company() throws Exception {
        String companyContent1 = "{\n" +
                "    \"name\": \"cargosmart\"\n" +
                "}";
        mockMvc.perform(
                post("/companies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(companyContent1)).andExpect(status().isCreated());
        String employee = "{\n" +
                "    \n" +
                "    \"name\":\"lester\",\n" +
                "    \"gender\": \"male\",\n" +
                "    \"age\": 22,\n" +
                "    \"companyId\": 1\n" +
                "}";
        mockMvc.perform(
                post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employee)).andExpect(status().isCreated());

        mockMvc.perform(get("/companies/1/employees"))
                .andExpect(jsonPath("[0].name").value("lester"));

    }


}
