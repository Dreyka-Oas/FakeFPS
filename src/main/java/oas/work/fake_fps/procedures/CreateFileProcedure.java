package oas.work.fake_fps.procedures;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;

/**
 *  This class is responsible for creating the configuration file "fake_fps.json"
 *  within the "oas_work" directory inside the "config" directory.
 *  This configuration file is used to store settings for the fake FPS feature,
 *  including minimum FPS, maximum FPS, and activation status.
 */
public class CreateFileProcedure {

    /**
     *  This method is the main entry point for the file creation procedure.
     *  It ensures that the configuration directory exists and then calls the method to create the "fake_fps.json" file.
     */
    public static void execute() {
        // Get the path to the configuration directory "oas_work" inside the main "config" directory.
        File configDir = new File(Paths.get("config", "oas_work").toString());

        // Create the directory if it does not exist.
        if (!configDir.exists()) {
            configDir.mkdirs(); // Creates the directory and any necessary parent directories.
        }

        // Call the method to create the "fake_fps.json" configuration file within the specified directory.
        createConfigFile(configDir);
    }

    /**
     *  This method handles the actual creation of the "fake_fps.json" configuration file.
     *  It checks if the file already exists and, if not, creates it and writes default JSON content
     *  defining the FPS feature settings.
     *  @param configDir The File object representing the configuration directory where the file should be created.
     */
    private static void createConfigFile(File configDir) {
        // Define the File object for the "fake_fps.json" configuration file within the specified directory.
        File configFile = new File(configDir, "fake_fps.json");

        // Check if the configuration file already exists.
        if (!configFile.exists()) {
            // If the file does not exist, attempt to create it and write default JSON content.
            try (FileWriter writer = new FileWriter(configFile)) {
                // Default JSON content for the "fake_fps.json" file, defining fps_mx, fps_min, and activated settings.
                String jsonContent = "{\n" +
                        "    \"fps_mx\": 88,\n" +  // Maximum FPS value
                        "    \"fps_min\": 30,\n" + // Minimum FPS value
                        "    \"activated\": true\n" + // FPS feature activation status
                        "}\n";
                writer.write(jsonContent);
            } catch (IOException e) {
                // If an IOException occurs during file creation or writing, print an error message to the error stream.
                System.err.println("Error creating configuration file 'fake_fps.json': " + e.getMessage());
            }
        }
    }
}