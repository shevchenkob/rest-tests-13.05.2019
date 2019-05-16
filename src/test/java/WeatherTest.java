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
                //.log().uri()
                .get()
                .then()
//                .log().all()
                .statusCode(200);

        String cityId = response.extract().asString();
        System.out.println(cityId);
    }
}
