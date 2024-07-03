package com.redhat.sample.note.http;

import com.redhat.sample.note.model.Note;
import io.quarkus.test.junit.QuarkusTest;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@QuarkusTest
public class RestServiceTest {

  private static final Logger LOG = LoggerFactory.getLogger(RestServiceTest.class);


  @Test
  public void testPost() {
    String sampleText = "Note : " + UUID.randomUUID().toString();

    // Make sure we can post a note and we get a reasonable response back
    given()
      .contentType("text/plain")
      .body(sampleText)
      .when().post("/notes")
      .then()
      .statusCode(200)
      .log().body()
      .body("text", equalTo(sampleText))
      .body("id", Matchers.instanceOf(Integer.class));

    // Get all notes, make sure ours is present
    var getResponse = given().when().get("/notes").then().statusCode(200).extract().response();

    var responseArray = getResponse.getBody().as(Note[].class);

    Assertions.assertNotEquals(responseArray.length, 0);

    Assertions.assertNotNull(Arrays.stream(responseArray).filter(n -> n.getText()  != null && n.getText().equals(sampleText)).findFirst().orElse(null));
  }
}
