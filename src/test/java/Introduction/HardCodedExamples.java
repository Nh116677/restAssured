package Introduction;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class HardCodedExamples {
    //test1
    //create an Employee using restassured
    //set the baseURI

    String baseURI = RestAssured.baseURI = "http://hrm.syntaxtechs.net/syntaxapi/api";

    String token ="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE3NTU0NzgyOTEsImlzcyI6ImxvY2FsaG9zdCIsImV4cCI6MTc1NTUyMTQ5MSwidXNlcklkIjoiNzQyNCJ9.K2EszKjNR4rzJkWFvNwzKxApuuIuM5VgXYS5V3R8Z_U";
    static String empID;

    @Test
    public void aCreatedAnEmployee(){
        // given keyword to prepare the request specifications
        // headers --> contentType, Authorization
        // body --> the request body

        RequestSpecification request = given().header("Content-Type","application/json").header("Authorization", token)
                .body("{\n" +
                        "  \"emp_firstname\": \"Nhu\",\n" +
                        "  \"emp_lastname\": \"Nguyen\",\n" +
                        "  \"emp_middle_name\": \"Trang\",\n" +
                        "  \"emp_gender\": \"F\",\n" +
                        "  \"emp_birthday\": \"1983-09-23\",\n" +
                        "  \"emp_status\": \"employed\",\n" +
                        "  \"emp_job_title\": \"QA Engineer\"\n" +
                        "}");

        // hitting the endpoint
        // when keyword to sent the request to the server.
        // make sure to have the request at attached to the when keyword
        Response response = request.when().post("/createEmployee.php");
        //print the response
        response.prettyPrint();
        // verify that the status code is 201
        response.then().assertThat().statusCode(201);
        //verify that the message is equals to Employee Created
        response.then().assertThat().body("Message",equalTo("Employee Created"));
        // verify that the employee first time is Nhu
        response.then().assertThat().body("Employee.emp_firstname", equalTo("Nhu"));

        // extract the Employee id using JsonPath
        empID = response.jsonPath().getString("Employee.employee_id");
        System.out.println(empID);

    }
    @Test
    public void bGetTheCreatedEmployee(){
        // create request
       RequestSpecification request =given().header("Content-Type","application/json").header("Authorization", token)
                .queryParam("employee_id","empID");
       //send to server
       Response response = request.when().get("/getOneEmployee.php");
       //print the response
       response.prettyPrint();

    }

}
