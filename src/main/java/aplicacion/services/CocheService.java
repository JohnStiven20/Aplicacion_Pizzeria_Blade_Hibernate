package aplicacion.services;

import java.io.IOException;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import aplicacion.modelo.Coche;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CocheService {
    
    private static final OkHttpClient cliente = new OkHttpClient();

    public void addCoche(Coche coche) {
        
        Gson gson = new Gson();
        String json = gson.toJson(coche);

        RequestBody body = RequestBody.create(json, MediaType.parse("application/json"));

        Request request = new Request.Builder()
                .url("http://localhost:9001/api/coche")
                .post(body)
                .addHeader("Content-Type", "application/json")
                .build();

        Call call = cliente.newCall(request);

        try (Response response = call.execute()) {
            if (response.isSuccessful()) {
                System.out.println("Dado de alta");
            } else {
                System.out.println("Se ha producido un error");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } 
    }

    public static List<Coche> getAllCoches() {

        String url = "http://localhost:9001/api/coches";

        Request request = new Request.Builder().url(url).build();

        try (Response response = cliente.newCall(request).execute()) {

            if (response.isSuccessful()) {

                String json = response.body().string();
                Gson gson = new Gson();
                TypeToken<List<Coche>> typeToken = new TypeToken<List<Coche>>() {};
                return  gson.fromJson(json, typeToken.getType());
            } else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    
}
