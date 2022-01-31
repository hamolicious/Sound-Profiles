package hamolicious.sound.profiles;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.LiteralText;

import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SoundProfiles implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger("soundprofiles");

	private static KeyBinding setSoundProfile1;
	private static KeyBinding setSoundProfile2;
	private static KeyBinding saveSoundProfile1;
	private static KeyBinding saveSoundProfile2;

	private static SoundProfile prof1;
	private static SoundProfile prof2;

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		prof1 = new SoundProfile();
		prof2 = new SoundProfile();

		setSoundProfile1 = KeyBindingHelper.registerKeyBinding(new KeyBinding(
				"key.apply.profile.one.hamolicious.sound.profiles", // The translation key of the keybinding's name
				InputUtil.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
				GLFW.GLFW_KEY_R, // The keycode of the key
				"category.hamolicious.sound.profiles" // The translation key of the keybinding's category.
		));

		setSoundProfile2 = KeyBindingHelper.registerKeyBinding(new KeyBinding(
				"key.apply.profile.two.hamolicious.sound.profiles", // The translation key of the keybinding's name
				InputUtil.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
				GLFW.GLFW_KEY_R, // The keycode of the key
				"category.hamolicious.sound.profiles" // The translation key of the keybinding's category.
		));

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			while (setSoundProfile1.wasPressed()) {
				client.player.sendMessage(new LiteralText("Loading Profile 1!"), false);
				prof1.applyProfile();
			}
		});

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			while (setSoundProfile2.wasPressed()) {
				client.player.sendMessage(new LiteralText("Loading Profile 2!"), false);
				prof2.applyProfile();
			}
		});


		saveSoundProfile1 = KeyBindingHelper.registerKeyBinding(new KeyBinding(
				"key.save.profile.one.hamolicious.sound.profiles", // The translation key of the keybinding's name
				InputUtil.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
				GLFW.GLFW_KEY_K, // The keycode of the key
				"category.hamolicious.sound.profiles" // The translation key of the keybinding's category.
		));

		saveSoundProfile2 = KeyBindingHelper.registerKeyBinding(new KeyBinding(
				"key.save.profile.two.hamolicious.sound.profiles", // The translation key of the keybinding's name
				InputUtil.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
				GLFW.GLFW_KEY_L, // The keycode of the key
				"category.hamolicious.sound.profiles" // The translation key of the keybinding's category.
		));

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			while (saveSoundProfile1.wasPressed()) {
				client.player.sendMessage(new LiteralText("Saving Profile 1!"), false);
				prof1.saveCurrentSettings();
			}
		});

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			while (saveSoundProfile2.wasPressed()) {
				client.player.sendMessage(new LiteralText("Saving Profile 2!"), false);
				prof2.saveCurrentSettings();
			}
		});

	}
}
