package oas.work.fake_fps.procedures;

import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;

import javax.annotation.Nullable;
import java.io.File;
import java.nio.file.Paths;

/**
 *  This class is responsible for creating the necessary configuration directory for the Fake FPS mod
 *  during the common setup phase of mod initialization. It ensures that the "oas_work" folder
 *  exists within the "config" directory and then triggers the file creation procedure for the Fake FPS configuration.
 */
@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class CreateFolderProcedure {

    /**
     *  This method is subscribed to the `FMLCommonSetupEvent`, which is triggered during the common setup phase of the mod.
     *  It ensures the execution of the folder creation logic when the mod is being initialized.
     *  @param event The FMLCommonSetupEvent provided by Forge.
     */
    @SubscribeEvent
    public static void init(FMLCommonSetupEvent event) {
        execute();
    }

    public static void execute() {
        execute(null);
    }

    /**
     *  This method contains the core logic to create the "oas_work" directory within the "config" directory.
     *  It calls the `createDirectory` method to handle directory creation and then proceeds to execute
     *  the file creation procedure for the Fake FPS configuration file.
     *  @param event The event that triggered this procedure (can be null if called directly).
     */
    private static void execute(@Nullable Event event) {
        // Create the 'oas_work' directory within the 'config' directory if it does not exist.
        createDirectory(Paths.get("config", "oas_work").toString());

        // After ensuring the directory exists, call the procedure to create the Fake FPS configuration file.
        CreateFileProcedure.execute();
    }

    /**
     *  This method handles the creation of a directory at the specified path.
     *  It checks if the directory already exists, and if not, attempts to create it.
     *  Only error messages are printed to the console in case of directory creation failure.
     *  @param path The path to the directory to be created as a String.
     */
    private static void createDirectory(String path) {
        File dir = new File(path);
        // Check if the directory does not exist.
        if (!dir.exists()) {
            // If the directory does not exist, attempt to create it.
            if (!dir.mkdirs()) {
                // If directory creation fails, print an error message to the error stream.
                System.err.println("Failed to create directory '" + path + "'.");
            }
        }
    }
}