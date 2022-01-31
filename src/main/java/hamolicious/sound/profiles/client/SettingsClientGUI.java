package hamolicious.sound.profiles.client;

import hamolicious.sound.profiles.SoundProfile;
import hamolicious.sound.profiles.SoundProfiles;
import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.WPlainPanel;
import io.github.cottonmc.cotton.gui.widget.WScrollPanel;
import io.github.cottonmc.cotton.gui.widget.data.Insets;
import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.text.TranslatableText;

public class SettingsClientGUI extends LightweightGuiDescription {
	public SettingsClientGUI() {
		WPlainPanel root = new WPlainPanel();
		setRootPanel(root);
		root.setSize(256, 240);
		root.setInsets(Insets.ROOT_PANEL);

		// Title
		WLabel label = new WLabel(new TranslatableText("category.hamolicious.sound.profiles"));
		root.add(label, 0, 0);

		// Save State Button
		WButton buttonSaveCurrentState = new WButton(new TranslatableText("button.save.state.hamolicious.sound.profiles"));
		root.add(buttonSaveCurrentState, 0, 40, 256, 40);
		buttonSaveCurrentState.setOnClick(() -> {
			SoundProfile soundProfile = new SoundProfile();
			soundProfile.saveCurrentSettings();
			SoundProfiles.profiles.add(soundProfile);
		});

		// Create Sound Profiles Panel
		WPlainPanel plainPanel = new WPlainPanel();
		for (int i = 0; i < SoundProfiles.profiles.size(); i++) {
			SoundProfile soundProfile = SoundProfiles.profiles.get(i);
			WButton button = new WButton(new TranslatableText("button.load.state.hamolicious.sound.profiles"));
			button.setOnClick(() -> {
				soundProfile.applyProfile();
			});


			label = new WLabel(Integer.toString(i));
			//TODO: add automatic scaling for all GUI elements
			plainPanel.add(button, 256/2, i*45, 256/2, 40);
			plainPanel.add(label, 0, i*45, 256/2, 40);
		}

		// Add above panel to scroll panel to allow scrolling
		WScrollPanel scrollPanel = new WScrollPanel(plainPanel);
		scrollPanel.setScrollingHorizontally(TriState.FALSE);
		scrollPanel.setScrollingVertically(TriState.DEFAULT);

		root.add(scrollPanel, 0, 80, 256, 240-80);
		root.validate(this);
	}
}
