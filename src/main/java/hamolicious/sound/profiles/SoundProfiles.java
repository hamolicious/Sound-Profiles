package hamolicious.sound.profiles;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.LiteralText;

import java.util.Vector;

import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hamolicious.sound.profiles.client.SettingsClientGUI;
import hamolicious.sound.profiles.client.SettingsClientScreen;

public class SoundProfiles implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger("soundprofiles");

	private static KeyBinding keyBindOpenSettings;
	public static Vector<SoundProfile> profiles = new Vector<SoundProfile>();

	private void initializeKeyBinding() {
		keyBindOpenSettings = KeyBindingHelper.registerKeyBinding(new KeyBinding(
				"key.open.settings.hamolicious.sound.profiles", // The translation key of the keybinding's name
				InputUtil.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
				GLFW.GLFW_KEY_R, // The keycode of the key
				"category.hamolicious.sound.profiles" // The translation key of the keybinding's category.
		));
	}

	@Override
	public void onInitialize() {
		this.initializeKeyBinding();

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			while (keyBindOpenSettings.wasPressed()) {
				MinecraftClient.getInstance().setScreen(new SettingsClientScreen(new SettingsClientGUI()));
			}
		});
	}
}
