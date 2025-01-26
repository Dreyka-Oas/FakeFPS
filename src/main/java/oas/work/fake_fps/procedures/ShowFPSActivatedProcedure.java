package oas.work.fake_fps.procedures;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ShowFPSActivatedProcedure {
    public static boolean execute() {
        JsonObject config = readConfigFromFile();
        if (config != null) {
            return config.get("activated").getAsBoolean();
        }
        return false;
    }

    private static JsonObject readConfigFromFile() {
        File configFile = new File("config/oas_work/fake_fps.json");
        Gson gson = new Gson();
        try (BufferedReader reader = new BufferedReader(new FileReader(configFile))) {
            return gson.fromJson(reader, JsonObject.class);
        } catch (IOException | JsonSyntaxException e) {
            System.err.println("Erreur lors de la lecture du fichier 'fake_fps.json': " + e.getMessage());
            return null;
        }
    }
}
