package ru.diolloyd.tests;

import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import ru.diolloyd.dto.BadRequest;
import ru.diolloyd.dto.Booking;
import ru.diolloyd.dto.Bookingdate;
import ru.diolloyd.dto.ResponseBooking;
import ru.diolloyd.dto.Token;
import ru.diolloyd.dto.User;
import ru.diolloyd.requests.Requests;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class BookingTests {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @BeforeAll
    static void beforeAll() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
    }

    @Test
    void createBookingTest() {
        String body = gson.toJson(new Booking()
                .setFirstname("Jim")
                .setLastname("Brown")
                .setTotalprice(115)
                .setDepositpaid(true)
                .setBookingdates(new Bookingdate()
                        .setCheckin(Bookingdate.getDateAsString("2022-03-24"))
                        .setCheckout(Bookingdate.getDateAsString("2022-03-27"))
                )
                .setAdditionalneeds("Breakfast, Taxi"));

        Response response = Requests.createBooking(body);
        assertThat(response.statusCode(), equalTo(200));
        assertThat(response.getBody(), notNullValue());

        ResponseBooking responseBooking = gson.fromJson(response.getBody().asString(), ResponseBooking.class);
        assertThat(responseBooking.getBookingid(), notNullValue());
        assertThat(responseBooking.getBooking(), notNullValue());

        deleteBooking(getToken(), responseBooking.getBookingid());
    }


    @Test
    void createBookingNegativeTest() {
        String body = gson.toJson(new Booking());

        Response response = Requests.createBooking(body);
        assertThat(response.statusCode(), equalTo(500));
        assertThat(response.getBody(), notNullValue());
        assertThat(response.getBody().asString(), equalTo("Internal Server Error"));
    }

    @Test
    void getTokenNegativeTest() {
        String body = gson.toJson(Map.of(
                "key", "value"
        ));

        Response response = Requests.createToken(body);
        assertThat(response.statusCode(), equalTo(200));
        assertThat(response.getBody(), notNullValue());

        String responseBody = response.getBody().asString();
        BadRequest badRequest = gson.fromJson(responseBody, BadRequest.class);
        assertThat(badRequest.getReason(), equalTo("Bad credentials"));
    }

    private String getToken() {
        String body = gson.toJson(new User()
                .setUsername("admin")
                .setPassword("password123")
        );

        Response response = Requests.createToken(body);
        assertThat(response.statusCode(), equalTo(200));
        assertThat(response.getBody(), notNullValue());

        String responseBody = response.getBody().asString();
        Token token = gson.fromJson(responseBody, Token.class);

        return token.getToken();
    }

    private void deleteBooking(String token, Integer bookingId) {
        Response response = Requests.deleteBooking(getToken(), bookingId);
        assertThat(response.statusCode(), equalTo(201));
        assertThat(response.getBody(), notNullValue());
        assertThat(response.getBody().asString(), equalTo("Created"));
    }

}
