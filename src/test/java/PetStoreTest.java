import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.junit.BeforeClass;
import org.junit.Test;

public class PetStoreTest {
    static {
        RestAssured.baseURI = Config.BASE_URI;
    }

    private enum Status {
        available,
        pending,
        sold }

    @Test
    public void getPetByIdTest(){
        int petId = 10;

        ValidatableResponse response = RestAssured.given()
                .log().uri()
                .get(Config.GET_PET_BY_ID, petId)
                .then()
                .log().all()
                .statusCode(200);
    }

    @Test
    public void getPetByStatus () {
       RestAssured.given()
                .param("status", Status.available)
                .log().uri()
                .get(Config.GET_PET_BY_STATUS)
                .then()
                .log().all()
                .statusCode(200);
    }
}
