package hamolicious.sound.profiles;

import java.util.HashMap;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.GameOptions;
import net.minecraft.sound.SoundCategory;



public class SoundProfile {
	public static int currentProfileIndex = 0;

	private HashMap<SoundCategory, Float> soundLevels;
	private String name;

	public SoundProfile() {
		currentProfileIndex++;
		name = "Profile " + Integer.toString(currentProfileIndex);

		soundLevels = new HashMap<SoundCategory, Float>();
		soundLevels.put(SoundCategory.MASTER, 1.0f);
		soundLevels.put(SoundCategory.MUSIC, 1.0f);
		soundLevels.put(SoundCategory.RECORDS, 1.0f);
		soundLevels.put(SoundCategory.WEATHER, 1.0f);
		soundLevels.put(SoundCategory.BLOCKS, 1.0f);
		soundLevels.put(SoundCategory.HOSTILE, 1.0f);
		soundLevels.put(SoundCategory.NEUTRAL, 1.0f);
		soundLevels.put(SoundCategory.PLAYERS, 1.0f);
		soundLevels.put(SoundCategory.AMBIENT, 1.0f);
		soundLevels.put(SoundCategory.VOICE, 1.0f);
	}

	public SoundProfile(String name_) {
		name = name_;

		soundLevels = new HashMap<SoundCategory, Float>();
		soundLevels.put(SoundCategory.MASTER, 1.0f);
		soundLevels.put(SoundCategory.MUSIC, 1.0f);
		soundLevels.put(SoundCategory.RECORDS, 1.0f);
		soundLevels.put(SoundCategory.WEATHER, 1.0f);
		soundLevels.put(SoundCategory.BLOCKS, 1.0f);
		soundLevels.put(SoundCategory.HOSTILE, 1.0f);
		soundLevels.put(SoundCategory.NEUTRAL, 1.0f);
		soundLevels.put(SoundCategory.PLAYERS, 1.0f);
		soundLevels.put(SoundCategory.AMBIENT, 1.0f);
		soundLevels.put(SoundCategory.VOICE, 1.0f);
	}

	public String getName() {
		return name;
	}

	public void saveCurrentSettings() {
		MinecraftClient client = MinecraftClient.getInstance();
		try { // incase something goes wrong...
			GameOptions options = client.options;
			soundLevels.put(SoundCategory.MASTER, options.getSoundVolume(SoundCategory.MASTER));
			soundLevels.put(SoundCategory.MUSIC, options.getSoundVolume(SoundCategory.MUSIC));
			soundLevels.put(SoundCategory.RECORDS, options.getSoundVolume(SoundCategory.RECORDS));
			soundLevels.put(SoundCategory.WEATHER, options.getSoundVolume(SoundCategory.WEATHER));
			soundLevels.put(SoundCategory.BLOCKS, options.getSoundVolume(SoundCategory.BLOCKS));
			soundLevels.put(SoundCategory.HOSTILE, options.getSoundVolume(SoundCategory.HOSTILE));
			soundLevels.put(SoundCategory.NEUTRAL, options.getSoundVolume(SoundCategory.NEUTRAL));
			soundLevels.put(SoundCategory.PLAYERS, options.getSoundVolume(SoundCategory.PLAYERS));
			soundLevels.put(SoundCategory.AMBIENT, options.getSoundVolume(SoundCategory.AMBIENT));
			soundLevels.put(SoundCategory.VOICE, options.getSoundVolume(SoundCategory.VOICE));
		} catch (Exception e) { // ...wanna get rid of the client
			client.close();
			e.printStackTrace();
		}
	}

	public void setSoundLevel(SoundCategory soundType, float level) {
		soundLevels.put(soundType, level);
	}

	public void applyProfile() {
		MinecraftClient client = MinecraftClient.getInstance();
		try { // incase something goes wrong...
			GameOptions options = client.options;
			options.setSoundVolume(SoundCategory.MASTER, (float)soundLevels.get(SoundCategory.MASTER));
			options.setSoundVolume(SoundCategory.MUSIC, (float)soundLevels.get(SoundCategory.MUSIC));
			options.setSoundVolume(SoundCategory.RECORDS, (float)soundLevels.get(SoundCategory.RECORDS));
			options.setSoundVolume(SoundCategory.WEATHER, (float)soundLevels.get(SoundCategory.WEATHER));
			options.setSoundVolume(SoundCategory.BLOCKS, (float)soundLevels.get(SoundCategory.BLOCKS));
			options.setSoundVolume(SoundCategory.HOSTILE, (float)soundLevels.get(SoundCategory.HOSTILE));
			options.setSoundVolume(SoundCategory.NEUTRAL, (float)soundLevels.get(SoundCategory.NEUTRAL));
			options.setSoundVolume(SoundCategory.PLAYERS, (float)soundLevels.get(SoundCategory.PLAYERS));
			options.setSoundVolume(SoundCategory.AMBIENT, (float)soundLevels.get(SoundCategory.AMBIENT));
			options.setSoundVolume(SoundCategory.VOICE, (float)soundLevels.get(SoundCategory.VOICE));
		} catch (Exception e) { // ...wanna get rid of the client
			client.close();
			e.printStackTrace();
		}
	}
}
