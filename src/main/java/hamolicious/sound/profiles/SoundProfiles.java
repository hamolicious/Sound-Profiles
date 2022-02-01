package hamolicious.sound.profiles;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.nbt.NbtCompound;

import java.util.Vector;

import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hamolicious.sound.profiles.client.SettingsClientGUI;
import hamolicious.sound.profiles.client.SettingsClientScreen;

public class SoundProfiles implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("soundprofiles");

	private static KeyBinding keyBindOpenSettings;
	public static Vector<SoundProfile> profiles = new Vector<SoundProfile>();

	public static void writeCustomDataToNbt(NbtCompound nbt) {
		NbtCompound profilesNbt = new NbtCompound();

		for (int i = 0; i < SoundProfiles.profiles.size(); i++) {
			SoundProfile profile = SoundProfiles.profiles.get(i);
			NbtCompound profileNbt = new NbtCompound();
			profile.writeToNbt(profileNbt);
			profilesNbt.put(Integer.toString(i), profileNbt);
		}

		nbt.put("SoundProfiles", profilesNbt);
	}

	public static void readCustomDataFromNbt(NbtCompound nbt) {
		SoundProfiles.profiles.clear();

		NbtCompound profilesNbt = nbt.getCompound("SoundProfiles");
		for (String key : profilesNbt.getKeys()) {
			SoundProfile profile = SoundProfile.readFromNbt(profilesNbt.getCompound(key));
			SoundProfiles.profiles.add(profile);
		}
	}

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
