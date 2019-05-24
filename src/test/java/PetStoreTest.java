import io.restassured.RestAssured;
import io.restassured.internal.path.json.mapping.JsonObjectDeserializer;
import io.restassured.response.ResponseBody;
import io.restassured.response.ValidatableResponse;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.awt.*;
import java.lang.reflect.Array;

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

         for (Status s :Status.values()) {

             ValidatableResponse statusResponse = RestAssured.given()
                     .param("status", s)
                     .log().uri()
                     .get(Config.GET_PET_BY_STATUS)
                     .then()
                     .log().all()
                     .statusCode(200);
             //ResponseBody bodyStatusResponse = statusResponse.andReturn().getBody();

             try {
             String data = statusResponse.toString();
              String newData = data.substring(1, data.length() - 1);
             JSONObject jsonObject = new JSONObject(newData);
             String pets = jsonObject.getJSONObject("newData").getString("name");
             System.out.println(pets);
             } catch (JSONException e) {
                 e.printStackTrace();
             }
         }
        }
    }

