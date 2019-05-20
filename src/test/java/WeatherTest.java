import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.Argument;
import org.junit.Test;

import java.util.Collections;

import static org.hamcrest.core.Is.is;

public class WeatherTest {
    @Test
    public void getWeatherPerCity() {
       // RestAssured.basePath = "search.php";
        String cityName = "Lviv";
        RestAssured.baseURI = "https://pinformer.sinoptik.ua/";
        ValidatableResponse response = RestAssured.given()
                .basePath("search.php")
                .param("Lang", "ua")
                .param("return_id", 1)
                .param("q", cityName)
                .log().uri()
                .get()
                .then()
                .log().all()
                .statusCode(200);

        String cityId = response.extract().asString();
        System.out.println(cityId);
        //String idOfCity = cityId.substring(cityId.length() - 9);
        String idOfCity = cityId.substring(cityId.lastIndexOf("|")+1);
        System.out.println(idOfCity);

        //RestAssured.basePath = "pinformer4.php";
        ValidatableResponse secondResponse = RestAssured.given()
                .basePath("pinformer4.php")
                .param("Lang", "ua")
                .param("type", "js")
                .param("id", idOfCity)
                .log().uri()
                .get()
                .then()
                .log().all()
                .statusCode(200)
                .body("any {it.key == '{pcity}'}", is(true)) //groovy path with hamcrest matchers
                .body("'{pcity}'", is(idOfCity)); //JSON path with hamcrest matchers

        String cityWeather = secondResponse.extract().asString();
        System.out.println(cityWeather);
        System.out.println(secondResponse.extract().path("'{pcity}'"));
    }
}
