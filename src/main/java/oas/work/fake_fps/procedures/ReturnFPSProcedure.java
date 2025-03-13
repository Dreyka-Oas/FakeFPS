package oas.work.fake_fps.procedures;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import oas.work.fake_fps.FakeFpsMod;

import net.minecraft.world.entity.Entity;
import net.minecraft.client.Minecraft;

/**
 *  This class implements a procedure to simulate and return fake FPS (Frames Per Second) values.
 *  It reads FPS configuration from a JSON file, generates random FPS values within a specified range,
 *  and updates these values periodically.
 */
public class ReturnFPSProcedure {
    private static final Random random = new Random();
    private static int fpsMin;
    private static int fpsMax;
    private static int currentFPS;
    private static ScheduledExecutorService scheduler;

    private static int fpsUpdateInterval = 1; // Interval in seconds to update FPS

    /**
     * Inner class to represent the FPS configuration loaded from the JSON file.
     */
    private static class FPSConfig {
        int fps_min;
        int fps_mx;
        boolean activated;
    }

    /**
     *  This is the main method to get and return the fake FPS value as a string.
     *  It initializes the FPS parameters if they are not already set and starts the FPS update scheduler.
     *  @return String Returns the current fake FPS value as a string (e.g., "60 fps").
     */
    public static String execute() {
        // Check if fpsMin and fpsMax are not yet initialized (default values are 0).
        if (fpsMin == 0 && fpsMax == 0) {
            // Read the FPS configuration from the JSON file.
            FPSConfig config = readConfigFromFile();

            // Check if the configuration was successfully loaded and if the FPS feature is activated.
            if (config != null && config.activated) {
                // Initialize fpsMin and fpsMax from the configuration.
                fpsMin = config.fps_min;
                fpsMax = config.fps_mx;
                // Initialize currentFPS with a random value within the configured range.
                currentFPS = getRandomFPS(fpsMin, fpsMax);

                // Check if the scheduler is not initialized or is shutdown.
                if (scheduler == null || scheduler.isShutdown()) {
                    // Initialize a ScheduledExecutorService to periodically update FPS values.
                    scheduler = Executors.newScheduledThreadPool(1);
                    // Schedule a task to run at a fixed rate to update the FPS.
                    scheduler.scheduleAtFixedRate(() -> {
                        // Get the player entity.
                        Entity entity = getEntity();
                        // Check if the entity is valid.
                        if (entity != null) {
                            // Update the FPS and log the new FPS value.
                            String newFpsMessage = updateFPS(entity);
                            FakeFpsMod.LOGGER.info(newFpsMessage);
                        }
                    }, 0, fpsUpdateInterval, TimeUnit.SECONDS); // Run every 'fpsUpdateInterval' seconds.
                }
            } else {
                // Return a message indicating FPS is not activated or there was a configuration error.
                return "FPS not activated or configuration error.";
            }
        }

        // Return the current FPS value as a string.
        return currentFPS + " fps";
    }

    /**
     *  This method updates the current FPS value by generating a new value based on the previous one.
     *  It also ensures the FPS stays within the configured minimum and maximum limits.
     *  @param entity The entity (player) context (currently not used in the FPS update logic, but included for potential future use).
     *  @return String Returns a message indicating the new FPS value.
     */
    public static String updateFPS(Entity entity) {
        // Check if the entity is valid.
        if (entity == null) {
            return "Invalid entity.";
        }

        // Generate a new FPS value based on the previous FPS, adding a random variation.
        currentFPS = generateNewFPS(currentFPS);
        // Ensure currentFPS is not below the minimum FPS limit.
        if (currentFPS < fpsMin) {
            currentFPS = fpsMin;
        }

        // Return a message indicating the new FPS value.
        return "New FPS value: " + currentFPS;
    }

    /**
     *  This method reads the FPS configuration from the "fake_fps.json" file.
     *  It uses Gson to parse the JSON file into an FPSConfig object.
     *  @return FPSConfig Returns an FPSConfig object if the configuration is successfully loaded, null otherwise.
     */
    private static FPSConfig readConfigFromFile() {
        // Define the File object for the configuration file "fake_fps.json".
        File configFile = new File("config/oas_work/fake_fps.json");
        // Create a Gson object for JSON parsing.
        Gson gson = new Gson();
        // Attempt to read and parse the configuration file.
        try (BufferedReader reader = new BufferedReader(new FileReader(configFile))) {
            // Parse the JSON content from the reader into an FPSConfig object and return it.
            return gson.fromJson(reader, FPSConfig.class);
        } catch (IOException | JsonSyntaxException e) {
            // If an IOException or JsonSyntaxException occurs, print an error message to the error stream and return null.
            System.err.println("Error reading configuration file 'fake_fps.json': " + e.getMessage());
            return null;
        }
    }

    /**
     *  This method generates a random FPS value within the specified minimum and maximum range.
     *  @param min The minimum FPS value.
     *  @param max The maximum FPS value.
     *  @return int Returns a random FPS value between min and max (inclusive).
     */
    private static int getRandomFPS(int min, int max) {
        // Validate the min and max FPS values.
        if (min < 0 || max <= 0 || min >= max) {
            System.err.println("Error: FPS values must be positive and min must be less than max.");
            return min; // Return min in case of invalid input.
        }

        // Generate a random integer between min and max (inclusive).
        return min + random.nextInt(max - min + 1);
    }

    /**
     *  This method generates a new FPS value by adding a random variation to the previous FPS.
     *  It ensures the new FPS value stays within the configured minimum and maximum limits.
     *  @param previousFPS The previous FPS value.
     *  @return int Returns the newly generated FPS value.
     */
    private static int generateNewFPS(int previousFPS) {
        // Generate a random variation between -10 and +10 FPS.
        int variation = random.nextInt(21) - 10;
        // Calculate the new FPS value by adding the variation to the previous FPS.
        int newFPS = previousFPS + variation;

        // Ensure newFPS is not below the minimum FPS limit.
        if (newFPS < fpsMin) {
            newFPS = fpsMin;
        } else if (newFPS > fpsMax) {
            // Ensure newFPS is not above the maximum FPS limit.
            newFPS = fpsMax;
        }

        // Return the new FPS value.
        return newFPS;
    }

    /**
     *  This method gets the player entity from the Minecraft client instance.
     *  @return Entity Returns the player entity, or null if not available.
     */
    private static Entity getEntity() {
        // Get the player entity from the Minecraft client instance.
        return Minecraft.getInstance().player;
    }
}