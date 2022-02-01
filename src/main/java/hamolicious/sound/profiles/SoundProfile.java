package hamolicious.sound.profiles;

import java.util.HashMap;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.GameOptions;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;



public class SoundProfile {
	public static int currentProfileIndex = 0;

	private HashMap<SoundCategory, Float> soundLevels;
	private String name;
	private boolean isSet = false;

	public SoundProfile() {
		currentProfileIndex++;
		name = "Profile " + Integer.toString(currentProfileIndex);

		soundLevels = new HashMap<SoundCategory, Float>();
		setDefaultLevels();
	}

	public SoundProfile(String name_) {
		name = name_;

		soundLevels = new HashMap<SoundCategory, Float>();
		setDefaultLevels();
	}

	public void setActive() {
		isSet = true;
	}

	public boolean isActive() {
		//TODO: if compareSounds returns false, setActive to false
		return isSet && compareSounds();
	}

	private boolean compareSounds() {
		MinecraftClient client = MinecraftClient.getInstance();
		boolean theSame = true;
		try { // incase something goes wrong...
			GameOptions options = client.options;
			if (Float.compare(options.getSoundVolume(SoundCategory.MASTER), soundLevels.get(SoundCategory.MASTER)) != 0) {theSame = false;};
			if (Float.compare(options.getSoundVolume(SoundCategory.MUSIC), soundLevels.get(SoundCategory.MUSIC)) != 0) {theSame = false;};
			if (Float.compare(options.getSoundVolume(SoundCategory.RECORDS), soundLevels.get(SoundCategory.RECORDS)) != 0) {theSame = false;};
			if (Float.compare(options.getSoundVolume(SoundCategory.WEATHER), soundLevels.get(SoundCategory.WEATHER)) != 0) {theSame = false;};
			if (Float.compare(options.getSoundVolume(SoundCategory.BLOCKS), soundLevels.get(SoundCategory.BLOCKS)) != 0) {theSame = false;};
			if (Float.compare(options.getSoundVolume(SoundCategory.HOSTILE), soundLevels.get(SoundCategory.HOSTILE)) != 0) {theSame = false;};
			if (Float.compare(options.getSoundVolume(SoundCategory.NEUTRAL), soundLevels.get(SoundCategory.NEUTRAL)) != 0) {theSame = false;};
			if (Float.compare(options.getSoundVolume(SoundCategory.PLAYERS), soundLevels.get(SoundCategory.PLAYERS)) != 0) {theSame = false;};
			if (Float.compare(options.getSoundVolume(SoundCategory.AMBIENT), soundLevels.get(SoundCategory.AMBIENT)) != 0) {theSame = false;};
			if (Float.compare(options.getSoundVolume(SoundCategory.VOICE), soundLevels.get(SoundCategory.VOICE)) != 0) {theSame = false;};
		} catch (Exception e) { // ...wanna get rid of the client
			client.close();
			e.printStackTrace();
			theSame = false;
		}
		return theSame;
	}

	private void setDefaultLevels() {
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

	public static SoundProfile readFromNbt(NbtCompound nbt) {
		SoundProfile profile = new SoundProfile();
		profile.name = nbt.getString("name");

		NbtCompound soundLevels = nbt.getCompound("soundLevels");
		for (String key : soundLevels.getKeys()) {
			profile.soundLevels.put(SoundCategory.valueOf(key), soundLevels.getFloat(key));
		}

		return profile;
	}

	public void writeToNbt(NbtCompound list) {
		list.putString("name", getName());
		list.putFloat(SoundCategory.MASTER.getName(), soundLevels.get(SoundCategory.MASTER));
		list.putFloat(SoundCategory.MUSIC.getName(), soundLevels.get(SoundCategory.MUSIC));
		list.putFloat(SoundCategory.RECORDS.getName(), soundLevels.get(SoundCategory.RECORDS));
		list.putFloat(SoundCategory.WEATHER.getName(), soundLevels.get(SoundCategory.WEATHER));
		list.putFloat(SoundCategory.BLOCKS.getName(), soundLevels.get(SoundCategory.BLOCKS));
		list.putFloat(SoundCategory.HOSTILE.getName(), soundLevels.get(SoundCategory.HOSTILE));
		list.putFloat(SoundCategory.NEUTRAL.getName(), soundLevels.get(SoundCategory.NEUTRAL));
		list.putFloat(SoundCategory.PLAYERS.getName(), soundLevels.get(SoundCategory.PLAYERS));
		list.putFloat(SoundCategory.AMBIENT.getName(), soundLevels.get(SoundCategory.AMBIENT));
		list.putFloat(SoundCategory.VOICE.getName(), soundLevels.get(SoundCategory.VOICE));
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
