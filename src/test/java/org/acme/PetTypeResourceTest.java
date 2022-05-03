package org.acme;

import com.example.petstore.PetType;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PetTypeResourceTest {

    PetType Dog = new PetType();
    PetType Bird = new PetType();


    PetTypeResourceTest() {
        Dog.setPetTypeId(1);
        Dog.setPetTypeName("Dog");

        Bird.setPetTypeId(2);
        Bird.setPetTypeName("Bird");
    }

    @Test
    @Order(1)
    public void testPetTypeCreateEndpointSuccess() { // PET-TYPE CREATE SUCCESS
        given()
                .contentType(ContentType.JSON)
                .body(Dog)
                .when().post("/v1/pet-types/add")
                .then()
                .statusCode(200)
                .body("pet_type_id", notNullValue())
                .body("pet_type_name", equalTo(Dog.getPetTypeName()));
    }

    @Test
    @Order(2)
    public void testPetTypeReadEndpointSuccess() { // PET-TYPE READ SUCCESS
        given()
                .when().get("/v1/pet-types/1")
                .then()
                .statusCode(200)
                .body("pet_type_id", notNullValue())
                .body("pet_type_name", equalTo(Dog.getPetTypeName()));
    }

    @Test
    @Order(3)
    public void testPetTypeUpdateEndpointSuccess() {  // PET-TYPE UPDATE SUCCESS
        given()
                .contentType(ContentType.JSON)
                .body(Bird)
                .when().put("/v1/pet-types/update/1")
                .then()
                .statusCode(200)
                .body("pet_type_id", notNullValue())
                .body("pet_type_name", equalTo(Bird.getPetTypeName()));
    }

    @Test
    @Order(4)
    public void testPetTypeUpdateEndpointFailure() {  // PET-TYPE UPDATE FAILURE
        given()
                .contentType(ContentType.JSON)
                .body(Bird)
                .when().put("/v1/pet-types/update/3") // 3 wouldn't exist
                .then()
                .statusCode(404);
    }

    @Test
    @Order(5)
    public void testPetTypeDeleteEndpointSuccess() {  // PET-TYPE DELETE SUCCESS
        given()
                .when().delete("/v1/pet-types/delete/1")
                .then()
                .statusCode(200);
    }

    @Test
    @Order(6)
    public void testPetTypeDeleteEndpointFailure() {  // PET-TYPE DELETE FAILURE
        given()
                .when().delete("/v1/pet-types/delete/1")
                .then()
                .statusCode(404);
    }
}
