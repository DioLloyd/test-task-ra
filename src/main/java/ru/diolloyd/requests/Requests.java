package ru.diolloyd.requests;

import java.util.Map;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class Requests {


    public static Response createToken(String body) {
        return given()
                .header("Content-Type", "application/json")
                .body(body)
                .when()
                .post("/auth");
    }

    public static Response createBooking(String body) {
        return given()
                .headers(Map.of(
                        "Content-Type", "application/json",
                        "Accept", "application/json"
                ))
                .body(body)
                .when()
                .post("/booking");
    }

    public static Response deleteBooking(String token, Integer id) {
        return given()
                .headers(Map.of(
                        "Content-Type", "application/json",
                        "Cookie", "token=" + token
                ))
                .when()
                .delete("/booking/" + id);
    }

}
