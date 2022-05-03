package org.acme;

import com.example.petstore.Pet;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PetResourceTest {

    Pet Ava = new Pet();
    Pet Ollie = new Pet();


    PetResourceTest() {
        Ava.setPetId(1);
        Ava.setPetName("Ava");
        Ava.setPetType("Dog");
        Ava.setPetAge(1);

        Ollie.setPetId(2);
        Ollie.setPetName("Ollie");
        Ollie.setPetType("Bird");
        Ollie.setPetAge(3);
    }

    @Test
    @Order(1)
    public void testPetCreateEndpointSuccess() { // PET CREATE SUCCESS
        given()
                .contentType(ContentType.JSON)
                .body(Ava)
                .when().post("/v1/pets/add")
                .then()
                .statusCode(200)
                .body("pet_id", notNullValue())
                .body("pet_type", equalTo(Ava.getPetType()))
                .body("pet_name", equalTo(Ava.getPetName()))
                .body("pet_age", equalTo(Ava.getPetAge()));
    }

    @Test
    @Order(2)
    public void testPetReadEndpointSuccess() { // PET READ SUCCESS
        given()
                .when().get("/v1/pets/1")
                .then()
                .statusCode(200)
                .body("pet_id", notNullValue())
                .body("pet_type", equalTo(Ava.getPetType()))
                .body("pet_name", equalTo(Ava.getPetName()))
                .body("pet_age", equalTo(Ava.getPetAge()));
    }

    @Test
    @Order(3)
    public void testPetSearchEndpointSuccess() { // PET SEARCH SUCCESS
        given()
                .when().get("/v1/pets?age=1&name=Ava&type=Dog")
                .then()
                .statusCode(200)
                .body("pet_type", everyItem(equalTo(Ava.getPetType())))
                .body("pet_name", everyItem(equalTo(Ava.getPetName())))
                .body("pet_age", everyItem(equalTo(Ava.getPetAge())));
    }

    @Test
    @Order(4)
    public void testPetSearchEndpointFailure() { // PET SEARCH FAILURE
        given()
                .when().get("/v1/pets?age=1&name=Ava&type=Bird") // Ava is a Dog during testing
                .then()
                .statusCode(200)
                .body("pet_type", everyItem(not(equalTo(Ava.getPetType()))));
    }

    @Test
    @Order(5)
    public void testPetUpdateEndpointSuccess() {  // PET UPDATE SUCCESS
        given()
                .contentType(ContentType.JSON)
                .body(Ollie)
                .when().put("/v1/pets/update/1")
                .then()
                .statusCode(200)
                .body("pet_id", notNullValue())
                .body("pet_type", equalTo(Ollie.getPetType()))
                .body("pet_name", equalTo(Ollie.getPetName()))
                .body("pet_age", equalTo(Ollie.getPetAge()));
    }

    @Test
    @Order(6)
    public void testPetUpdateEndpointFailure() {  // PET UPDATE FAILURE
        given()
                .contentType(ContentType.JSON)
                .body(Ollie)
                .when().put("/v1/pets/update/3") // 3 wouldn't exist
                .then()
                .statusCode(404);
    }

    @Test
    @Order(7)
    public void testPetDeleteEndpointSuccess() {  // PET DELETE SUCCESS
        given()
                .when().delete("/v1/pets/delete/1")
                .then()
                .statusCode(200);
    }

    @Test
    @Order(8)
    public void testPetDeleteEndpointFailure() {  // PET DELETE FAILURE
        given()
                .when().delete("/v1/pets/delete/1")
                .then()
                .statusCode(404);
    }
}
