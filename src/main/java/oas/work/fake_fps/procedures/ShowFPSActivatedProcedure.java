package oas.work.fake_fps.procedures;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 *  This class implements a procedure to determine if the FPS display feature is activated based on a configuration file.
 *  It reads the "fake_fps.json" configuration file and checks the boolean value of the "activated" field.
 */
public class ShowFPSActivatedProcedure {

    /**
     *  This method executes the logic to check if the FPS display is activated.
     *  It reads the configuration from the "fake_fps.json" file and returns the value of the "activated" field.
     *  @return boolean Returns true if the "activated" field in the configuration is true, false otherwise.
     */
    public static boolean execute() {
        // Read the configuration from the JSON file.
        JsonObject config = readConfigFromFile();
        // Check if the configuration object was successfully read.
        if (config != null) {
            // If the configuration is valid, return the boolean value of the "activated" field.
            return config.get("activated").getAsBoolean();
        }
        // If the configuration file could not be read or is invalid, return false (FPS display is considered deactivated by default in case of error).
        return false;
    }

    /**
     *  This method is responsible for reading the configuration from the "fake_fps.json" file.
     *  It attempts to open and read the file, parse it as a JSON object using Gson, and handle potential exceptions during file reading or JSON parsing.
     *  @return JsonObject Returns a JsonObject representing the configuration if successful, or null if an error occurred.
     */
    private static JsonObject readConfigFromFile() {
        // Define the File object for the configuration file "fake_fps.json" located in the "config/oas_work/" directory.
        File configFile = new File("config/oas_work/fake_fps.json");
        // Create a Gson object to parse the JSON file.
        Gson gson = new Gson();
        // Attempt to read and parse the configuration file.
        try (BufferedReader reader = new BufferedReader(new FileReader(configFile))) {
            // Use Gson to parse the JSON content from the BufferedReader into a JsonObject and return it.
            return gson.fromJson(reader, JsonObject.class);
        } catch (IOException | JsonSyntaxException e) {
            // If an IOException occurs during file reading or a JsonSyntaxException during JSON parsing,
            // print an error message to the error stream and return null to indicate failure.
            System.err.println("Error reading configuration file 'fake_fps.json': " + e.getMessage());
            return null;
        }
    }
}