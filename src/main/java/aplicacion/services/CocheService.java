package aplicacion.services;

import java.io.IOException;
import java.net.SocketException;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import aplicacion.modelo.Coche;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CocheService {

    private static final OkHttpClient cliente = new OkHttpClient();

    public static boolean addCoche(Coche coche) {
        Gson gson = new Gson();
        String json = gson.toJson(coche);

        RequestBody body = RequestBody.create(json, MediaType.get("application/json"));

        Request request = new Request.Builder()
                .url("http://localhost:9001/api/coche")
                .post(body)
                .addHeader("Content-Type", "application/json")
                .build();

        Call call = cliente.newCall(request);

        try (Response response = call.execute()) {
            boolean success = response.isSuccessful();
            String errorMessage = success ? "Dado de alta" :
            (response.body() != null ? response.body().string() : "Error desconocido");

            if (success) {
                System.out.println(errorMessage);
                return true;  
            } else {
                String finalErrorMessage = errorMessage;
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText(finalErrorMessage);
                    alert.setHeaderText(null);
                    alert.showAndWait();
                });
                cliente.connectionPool().evictAll();
                return false; 
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static List<Coche> getAllCoches() {

        String url = "http://localhost:9001/api/coches";

        Request request = new Request.Builder().url(url).build();

        try (Response response = cliente.newCall(request).execute()) {

            if (response.isSuccessful()) {

                String json = response.body().string();
                Gson gson = new Gson();
                TypeToken<List<Coche>> typeToken = new TypeToken<List<Coche>>() {
                };
                response.close();
                cliente.connectionPool().evictAll();
                return gson.fromJson(json, typeToken.getType());

            } else {
                cliente.connectionPool().evictAll();
                response.close();
                return null;
            }

        } catch (SocketException e) {
            System.out.println("Buenas tardes");
            return null;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static int delete(Coche coche) {

        Gson gson = new Gson();
        String json = gson.toJson(coche);
        System.out.println(coche.toString());

        RequestBody body = RequestBody.create(json, MediaType.parse("application/json"));

        Request request = new Request.Builder()
                .url("http://localhost:9001/api/coche")
                .delete(body)
                .build();

        Call call = cliente.newCall(request);

        try (Response response = call.execute()) {
            if (response.isSuccessful()) {
                System.out.println("Dado de alta");
                return 1;
            } else {
                System.out.println("Se ha producido un error");
                return 0;
            }

        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }

    }

    public static void updateCoche(Coche coche) {

        Gson gson = new Gson();
        String json = gson.toJson(coche);
        System.out.println(coche.toString());

        RequestBody body = RequestBody.create(json, MediaType.parse("application/json"));

        Request request = new Request.Builder()
                .url("http://localhost:9001/api/coche")
                .put(body)
                .build();

        Call call = cliente.newCall(request);
        

        try (Response response = call.execute()) {

            boolean success = response.isSuccessful();
            String errorMessage = success ? "Dado de alta" :
            (response.body() != null ? response.body().string() : "Error desconocido");

            if (response.isSuccessful()) {
                System.out.println("Dado de alta");
            } else {

                String finalErrorMessage = errorMessage;
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText(finalErrorMessage);
                    alert.setHeaderText(null);
                    alert.showAndWait();
                });
                cliente.connectionPool().evictAll();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
