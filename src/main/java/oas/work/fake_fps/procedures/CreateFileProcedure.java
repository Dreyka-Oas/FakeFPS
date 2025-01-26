package oas.work.fake_fps.procedures;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;

public class CreateFileProcedure {
    public static void execute() {
        // Utiliser Paths pour obtenir le chemin vers le répertoire config
        File configDir = new File(Paths.get("config", "oas_work").toString());

        // Créer le dossier s'il n'existe pas
        if (!configDir.exists()) {
            configDir.mkdirs(); // Crée les répertoires nécessaires
        }

        // Créer le fichier fake_fps.json
        createConfigFile(configDir);
    }

    private static void createConfigFile(File configDir) {
        File configFile = new File(configDir, "fake_fps.json");

        // Vérifier si le fichier existe déjà
        if (!configFile.exists()) {
            try (FileWriter writer = new FileWriter(configFile)) {
                // Contenu du fichier JSON avec fps_mx, fps_min et activated
                String jsonContent = "{\n" +
                        "    \"fps_mx\": 88,\n" +  // Valeur maximale des FPS
                        "    \"fps_min\": 30,\n" + // Valeur minimale des FPS
                        "    \"activated\": true\n" + // Activation des FPS
                        "}\n";
                writer.write(jsonContent);
                System.out.println("Fichier 'fake_fps.json' créé avec succès dans le dossier 'oas_work'.");
            } catch (IOException e) {
                System.err.println("Erreur lors de la création du fichier 'fake_fps.json': " + e.getMessage());
            }
        } else {
            System.out.println("Le fichier 'fake_fps.json' existe déjà.");
        }
    }
}
