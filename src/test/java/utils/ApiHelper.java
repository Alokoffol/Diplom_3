package utils;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import java.util.Map;

public class ApiHelper {

    private static final String BASE_URL = "https://stellarburgers.nomoreparties.site";

    /**
     * Регистрирует нового пользователя через API.
     * @param userData Map с ключами "email", "password", "name"
     * @return Map с ключами "success", "user", "accessToken", "refreshToken"
     */
    public static Map<String, Object> registerUser(Map<String, Object> userData) {
        Response response = RestAssured
                .given()
                .header("Content-Type", "application/json")
                .body(userData)
                .post(BASE_URL + "/api/auth/register");

        if (response.statusCode() != 200) {
            throw new RuntimeException("Ошибка при регистрации пользователя через API: " + response.asString());
        }

        return response.jsonPath().getMap("");
    }

    /**
     * Удаляет текущего пользователя через API.
     * @param accessToken Токен авторизации пользователя.
     */
    public static void deleteCurrentUser(String accessToken) {
        if (accessToken == null || accessToken.trim().isEmpty()) {
            System.out.println("Токен не предоставлен. Удаление пользователя невозможно.");
            return;
        }

        Response response = RestAssured
                .given()
                .header("Authorization", accessToken)
                .delete(BASE_URL + "/api/auth/user");

        if (response.statusCode() != 202) {
            System.out.println("Не удалось удалить пользователя: " + response.asString());
        } else {
            System.out.println("Пользователь успешно удален.");
        }
    }
}