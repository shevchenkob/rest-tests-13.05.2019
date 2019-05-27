package petstore;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import petstore.Config;
import petstore.models.CategoryModel;
import petstore.models.PetModel;
import petstore.models.TagModel;

import java.util.ArrayList;

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

//             try {
             String data = statusResponse.toString();
             System.out.println(data);
//              String newData = data.substring(1, data.length() - 1);
//             JSONObject jsonObject = new JSONObject(newData);
//             String pets = jsonObject.getJSONObject("newData").getString("name");
//             System.out.println(pets);
//             } catch (JSONException e) {
//                 e.printStackTrace();
//             }
         }
        }
        @Test
    public void createPetTest(){
            PetModel petModel = new PetModel(30,
                    new CategoryModel(),
                    "test30",
                    new String[]{"www.zoo.com"},
                    new TagModel[]{new TagModel()},
                    "AVAILIBLE");
            ValidatableResponse statusResponse = RestAssured.given()
                    .log().uri()
                    .header("Content-Type", "application/json") //similar as contentType
                    .contentType("application/json")
                    .body(petModel)
                    .post(Config.CREATE_PET)
                    .then()
                    .log().all()
                    .statusCode(200);

        }
    }

