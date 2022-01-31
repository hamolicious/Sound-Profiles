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
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Window;
import net.minecraft.text.TranslatableText;

public class SettingsClientGUI extends LightweightGuiDescription {
	private static final float ROOT_PANEL_WIDTH_MULTIPLIER = 0.5f;
	private static final float ROOT_PANEL_HEIGHT_MULTIPLIER = 0.5f;

	private void setupButtons(WPlainPanel root, int width, int height) {
		WButton[] buttons = new WButton[1];

		// Save State Button
		WButton buttonSaveCurrentState = new WButton(
				new TranslatableText("button.save.state.hamolicious.sound.profiles"));
		buttonSaveCurrentState.setOnClick(() -> {
			SoundProfile soundProfile = new SoundProfile();
			soundProfile.saveCurrentSettings();
			SoundProfiles.profiles.add(soundProfile);
		});
		buttons[0] = buttonSaveCurrentState;

		for (int i = 0; i < buttons.length; i++) {
			int w = (int)(((float)width) / buttons.length + 5);
			int x = i * w;
			root.add(buttons[i], x, 20, w, 40);
		}
	}

	public SettingsClientGUI() {
		WPlainPanel root = new WPlainPanel();
		setRootPanel(root);
		Window win = MinecraftClient.getInstance().getWindow();
		final int WIDTH = (int)((float)win.getScaledWidth() * ROOT_PANEL_WIDTH_MULTIPLIER);
		final int HEIGHT = (int)((float)win.getScaledHeight() * ROOT_PANEL_HEIGHT_MULTIPLIER);
		root.setSize(WIDTH, HEIGHT);
		root.setInsets(Insets.ROOT_PANEL);

		// Title
		WLabel label = new WLabel(new TranslatableText("category.hamolicious.sound.profiles"));
		root.add(label, 0, 0);

		setupButtons(root, WIDTH, HEIGHT);

		// Create Sound Profiles Panel
		WPlainPanel plainPanel = new WPlainPanel();
		for (int i = 0; i < SoundProfiles.profiles.size(); i++) {
			SoundProfile soundProfile = SoundProfiles.profiles.get(i);
			WButton button = new WButton(new TranslatableText("button.load.state.hamolicious.sound.profiles"));
			button.setOnClick(() -> {
				soundProfile.applyProfile();
			});

			label = new WLabel(soundProfile.getName());
			plainPanel.add(button, WIDTH / 2, i * 45, WIDTH / 2, 40);
			plainPanel.add(label, 0, i * 45, WIDTH / 2, 40);
		}

		// Add above panel to scroll panel to allow scrolling
		WScrollPanel scrollPanel = new WScrollPanel(plainPanel);
		scrollPanel.setScrollingHorizontally(TriState.FALSE);
		scrollPanel.setScrollingVertically(TriState.DEFAULT);

		root.add(scrollPanel, 0, 80, WIDTH, 240 - 80);
		root.validate(this);
	}
}
