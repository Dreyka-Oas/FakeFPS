package oas.work.fake_fps.procedures;

import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;


import javax.annotation.Nullable;
import java.io.File;
import java.nio.file.Paths;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class CreateFolderProcedure {

    // Cet événement est déclenché pendant l'initialisation commune du mod
    @SubscribeEvent
    public static void init(FMLCommonSetupEvent event) {
        execute();
    }

    public static void execute() {
        execute(null);
    }

    private static void execute(@Nullable Event event) {
        // Créer les dossiers 'oas_work' et 'Colony' si nécessaire
        createDirectory(Paths.get("config", "oas_work").toString());

        CreateFileProcedure.execute();
    }

    private static void createDirectory(String path) {
        File dir = new File(path);
        if (!dir.exists()) {
            if (dir.mkdirs()) {
                System.out.println("Dossier '" + path + "' créé avec succès.");
            } else {
                System.err.println("Échec de la création du dossier '" + path + "'.");
            }
        } else {
            System.out.println("Le dossier '" + path + "' existe déjà.");
        }
    }

	
}
