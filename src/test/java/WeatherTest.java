import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;

public class WeatherTest {
    @Test
    public void getWeatherPerCity() {
        RestAssured.baseURI = "https://pinformer.sinoptik.ua/search.php";
        ValidatableResponse response = RestAssured.given()
                .param("Lang", "ua")
                .param("return_id", 1)
                .param("q", "Lviv")
                .log().uri()
                .get()
                .then()
                .log().all()
                .statusCode(200);

        String cityId = response.extract().asString();
        System.out.println(cityId);
        String idOfCity = cityId.substring(cityId.length() - 9);
        System.out.println(idOfCity);

        RestAssured.baseURI = "https://pinformer.sinoptik.ua/pinformer4.php";
        ValidatableResponse secondResponse = RestAssured.given()
                .param("Lang", "ua")
                .param("type", "js")
                .param("id", idOfCity)
                .log().uri()
                .get()
                .then()
                .log().all()
                .statusCode(200);

        String cityWeather = secondResponse.extract().asString();
        System.out.println(cityWeather);
    }
}
