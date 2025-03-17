Display Fake FPS! 🚀

[![Fake FPS Mod - Presentation Video](https://img.youtube.com/vi/v3cF6aD5YN4/0.jpg)](https://www.youtube.com/watch?v=v3cF6aD5YN4)

This client-side mod allows you to display a **fake** and **customizable** FPS counter at the top of your Minecraft screen! Ideal for fun, videos, or simply to prank your friends into believing you have 1000 FPS! 😉

**⚙️ How does it work?**

The "Fake FPS Mod" is a **purely client-side** mod, meaning it only needs to be installed **on your Minecraft game**, not on the server. Here's how it works:

1. **📝 Configuration:** When you launch the game for the first time with the mod, a configuration file named `fake_fps.json` is created in the `config/oas_work/` folder.
2. **📖 Reading the configuration file:** The mod reads this `fake_fps.json` file to determine:
    * **⏱️ `fps_min`:** The minimum value of fake FPS to be displayed (by default, this value needs to be configured in the `fake_fps.json` file).
    * **🚀 `fps_mx`:** The maximum value of fake FPS to be displayed (by default, this value needs to be configured in the `fake_fps.json` file).
    * **✅ `activated`:** If the value is `true`, the display of fake FPS is activated. If `false`, it is deactivated (by default, to be configured in the `fake_fps.json` file).
3. **🎲 Random Generation:** The mod then generates a **random** FPS value every second, between the `fps_min` and `fps_mx` values you have configured.
4. **📊 Display:** This fake FPS value is displayed in the **top left** corner of your screen in-game.

**🎮 How to use it?**

1. **🛠️ Configuration (recommended):**
    * **📂 Locate the configuration file:** After launching Minecraft with the mod at least once, go to the `config/oas_work/` folder in your Minecraft directory. Open the `fake_fps.json` file with a text editor.
    * **✏️ Modify the settings:**
        * `"fps_min": ... ,`  : Replace `...` with the minimum value of fake FPS you want to display.
        * `"fps_mx": ... ,`  : Replace `...` with the maximum value of fake FPS you want to display. Make sure `fps_mx` is **greater** than `fps_min`.
        * `"activated": ...` :  Replace `...` with `true` to activate the display of fake FPS, or `false` to deactivate it.
    * **💾 Save** the `fake_fps.json` file.
    * 🔄 **Restart your Minecraft game** for the changes to take effect.

**📌 Important Points:**

* 💻 **Client-Side ONLY:** This mod is **client-side only**. It should not be installed on the server.
* ⚙️ **Customizable Configuration:** You can easily adjust the range of fake FPS displayed via the `fake_fps.json` file.
* 📍 **Configuration File:** The `fake_fps.json` file is located in the `config/oas_work/` folder of your Minecraft installation.
* ⚠️ **Purely Visual:** This mod **does not modify your real FPS in any way**. It just displays a fake value for the display.
