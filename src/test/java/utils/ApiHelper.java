package utils;

import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.Map;

public class ApiHelper {

    // Исправленный URL: без слэша и пробелов в конце
    private static final String BASE_URL = "https://stellarburgers.nomoreparties.site";

    /**
     * Регистрирует нового пользователя через API.
     *
     * @param userData Map с ключами "email", "password", "name"
     * @return Map с ключами "success", "user", "accessToken", "refreshToken"
     */
    public static Map<String, Object> registerUser(Map<String, Object> userData) {
        // Используем Gson для сериализации
        Gson gson = new Gson();
        String jsonBody = gson.toJson(userData);

        System.out.println("DEBUG ApiHelper: Отправка запроса регистрации с данными: " + jsonBody);

        Response response = RestAssured
                .given()
                .header("Content-Type", "application/json")
                .body(jsonBody) // Передаем JSON строку
                .post(BASE_URL + "/api/auth/register"); // Правильный URL

        System.out.println("DEBUG ApiHelper: Ответ API регистрации. Код: " + response.statusCode() + ", Тело: " + response.asString());

        // Проверяем код ответа
        if (response.statusCode() != 200) {
            System.err.println("Ошибка при регистрации. Код: " + response.statusCode() + ", Тело: " + response.asString());
            throw new RuntimeException("Ошибка при регистрации пользователя через API: " + response.asString());
        }

        // Парсим JSON-ответ в Map
        Map<String, Object> apiResponseMap = response.jsonPath().getMap("");
        System.out.println("DEBUG ApiHelper: Данные пользователя из API: " + apiResponseMap);
        return apiResponseMap;
    }

    /**
     * Удаляет текущего пользователя через API.
     *
     * @param accessToken Токен авторизации пользователя.
     */
    public static void deleteCurrentUser(String accessToken) {
        if (accessToken == null || accessToken.trim().isEmpty()) {
            System.out.println("Токен не предоставлен. Удаление пользователя невозможно.");
            return;
        }

        Response response = RestAssured
                .given()
                .header("Authorization", accessToken) // Обычно токен передается в заголовке Authorization
                .delete(BASE_URL + "/api/auth/user");

        if (response.statusCode() != 202) {
            System.out.println("Не удалось удалить пользователя: " + response.asString());
        } else {
            System.out.println("Пользователь успешно удален.");
        }
    }
}