package oas.work.fake_fps.procedures;

import com.google.gson.Gson;
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

public class ReturnFPSProcedure {
    private static final Random random = new Random();
    private static int fpsMin;
    private static int fpsMax;
    private static int currentFPS;
    private static ScheduledExecutorService scheduler;

    private static int fpsUpdateInterval = 1;

    private static class FPSConfig {
        int fps_min;
        int fps_mx;
        boolean activated;
    }

    public static String execute() {
        if (fpsMin == 0 && fpsMax == 0) {
            FPSConfig config = readConfigFromFile();

            if (config != null && config.activated) {
                fpsMin = config.fps_min;
                fpsMax = config.fps_mx;
                currentFPS = getRandomFPS(fpsMin, fpsMax);

                if (scheduler == null || scheduler.isShutdown()) {
                    scheduler = Executors.newScheduledThreadPool(1);
                    scheduler.scheduleAtFixedRate(() -> {
                        Entity entity = getEntity();
                        if (entity != null) {
                            String newFpsMessage = updateFPS(entity);
                            FakeFpsMod.LOGGER.info(newFpsMessage);
                        }
                    }, 0, fpsUpdateInterval, TimeUnit.SECONDS);
                }
            } else {
                return "FPS non activé ou erreur de configuration.";
            }
        }

        return currentFPS + " fps";
    }

    public static String updateFPS(Entity entity) {
        if (entity == null) {
            return "Entité non valide.";
        }

        currentFPS = generateNewFPS(currentFPS);
        if (currentFPS < fpsMin) {
            currentFPS = fpsMin;
        }

        return "Nouvelle valeur de FPS : " + currentFPS;
    }

    private static FPSConfig readConfigFromFile() {
        File configFile = new File("config/oas_work/fake_fps.json");
        Gson gson = new Gson();
        try (BufferedReader reader = new BufferedReader(new FileReader(configFile))) {
            return gson.fromJson(reader, FPSConfig.class);
        } catch (IOException | JsonSyntaxException e) {
            System.err.println("Erreur lors de la lecture du fichier 'fake_fps.json': " + e.getMessage());
            return null;
        }
    }

    private static int getRandomFPS(int min, int max) {
        if (min < 0 || max <= 0 || min >= max) {
            System.err.println("Erreur : Les valeurs de FPS doivent être positives et min doit être inférieur à max.");
            return min;
        }

        return min + random.nextInt(max - min + 1);
    }

    private static int generateNewFPS(int previousFPS) {
        int variation = random.nextInt(21) - 10;
        int newFPS = previousFPS + variation;

        if (newFPS < fpsMin) {
            newFPS = fpsMin;
        } else if (newFPS > fpsMax) {
            newFPS = fpsMax;
        }

        return newFPS;
    }

    private static Entity getEntity() {
        return Minecraft.getInstance().player;
    }
}
