package utils;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class ApiHelper {

    public static void deleteCurrentUser(String accessToken) {
        Response response = RestAssured
                .given()
                .header("Authorization", accessToken)
                .delete("https://stellarburgers.nomoreparties.site/api/auth/user");

        if (response.statusCode() != 202) {
            System.out.println("Не удалось удалить пользователя: " + response.asString());
        }
    }
}