package com.reliaquest.api;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApiApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        
    }

    @Test
    void shouldReturnAllEmployees() throws Exception {
        mockMvc.perform(get("/api/v1/employee"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.data").isArray());
    }

    @Test
    void shouldReturnEmployeesByNameSearch() throws Exception {
        String nameFragment = "Tiger";
        mockMvc.perform(get("/api/v1/employee/search/{name}", nameFragment))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.data").isArray())
               .andExpect(jsonPath("$.data[0].employee_name").value("Tiger Nixon"));
    }

    @Test
    void shouldReturnEmployeeById() throws Exception {
        String employeeId = "5255f1a5-f9f7-4be5-829a-134bde088d17";  
        mockMvc.perform(get("/api/v1/employee/{id}", employeeId))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.data.id").value(employeeId));
    }

    @Test
    void shouldReturnHighestSalary() throws Exception {
        mockMvc.perform(get("/api/v1/employee/highest-salary"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.data").isNumber());
    }

    @Test
    void shouldReturnTop10HighestEarningEmployees() throws Exception {
        mockMvc.perform(get("/api/v1/employee/top10"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.data").isArray())
               .andExpect(jsonPath("$.data.length()").value(10));
    }

    @Test
    void shouldCreateNewEmployee() throws Exception {
        String newEmployeeJson = "{\n" +
                                 "  \"employee_name\": \"John Doe\",\n" +
                                 "  \"employee_salary\": 50000,\n" +
                                 "  \"employee_age\": 30,\n" +
                                 "  \"employee_title\": \"Software Engineer\"\n" +
                                 "}";

        mockMvc.perform(post("/api/v1/employee")
               .contentType("application/json")
               .content(newEmployeeJson))
               .andExpect(status().isCreated())
               .andExpect(jsonPath("$.data.employee_name").value("John Doe"))
               .andExpect(jsonPath("$.data.employee_salary").value(50000));
    }

    @Test
    void shouldDeleteEmployeeById() throws Exception {
        String employeeId = "d005f39a-beb8-4390-afec-fd54e91d94ee";  
        mockMvc.perform(delete("/api/v1/employee/{id}", employeeId))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.data").value(true));
    }
}
