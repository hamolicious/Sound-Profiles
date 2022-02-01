package hamolicious.sound.profiles.client;

import hamolicious.sound.profiles.SoundProfile;
import hamolicious.sound.profiles.SoundProfiles;
import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.WPlainPanel;
import io.github.cottonmc.cotton.gui.widget.WScrollPanel;
import io.github.cottonmc.cotton.gui.widget.WTextField;
import io.github.cottonmc.cotton.gui.widget.data.Insets;
import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.Window;
import net.minecraft.text.TranslatableText;

public class SettingsClientGUI extends LightweightGuiDescription {
	private static final float ROOT_PANEL_WIDTH_MULTIPLIER = 0.6f;
	private static final float ROOT_PANEL_HEIGHT_MULTIPLIER = 0.5f;

	private void setupButtons(WPlainPanel root, WTextField profileName, int width, int height) {
		WButton[] buttons = new WButton[1];

		// Save State Button
		WButton buttonSaveCurrentState = new WButton(
				new TranslatableText("menu.button.save.state.soundprofiles"));
		buttonSaveCurrentState.setOnClick(() -> {
			SoundProfile soundProfile;
			String cmp = new TranslatableText("menu.input.hint.soundprofiles").getString();

			if (profileName.getText().equals(cmp)) {
				soundProfile = new SoundProfile();
			} else {
				soundProfile = new SoundProfile(profileName.getText());
			}

			soundProfile.saveCurrentSettings();
			SoundProfiles.profiles.add(soundProfile);
			refreshScreen();
		});
		buttons[0] = buttonSaveCurrentState;

		for (int i = 0; i < buttons.length; i++) {
			int w = (int)(((float)width) / buttons.length + 5);
			int x = i * w;
			root.add(buttons[i], x, 20, w, 40);
		}
	}

	private void refreshScreen() {
		// dirty solution TODO: find a better way to refresh the screen
		MinecraftClient.getInstance().setScreen(new SettingsClientScreen(new SettingsClientGUI()));
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
		WLabel label = new WLabel(new TranslatableText("menu.category.soundprofiles"));
		root.add(label, 0, 0);

		// Add text input
		WTextField inputFieldProfileName = new WTextField();
		inputFieldProfileName.setText(new TranslatableText("menu.input.hint.soundprofiles").getString());
		root.add(inputFieldProfileName, 0, 45, WIDTH, 40);

		setupButtons(root, inputFieldProfileName, WIDTH, HEIGHT);

		// Create Sound Profiles Panel
		WPlainPanel plainPanel = new WPlainPanel();
		for (int i = 0; i < SoundProfiles.profiles.size(); i++) {
			SoundProfile soundProfile = SoundProfiles.profiles.get(i);
			WButton loadButton = new WButton(new TranslatableText("menu.button.load.state.soundprofiles"));
			loadButton.setOnClick(() -> {
				soundProfile.applyProfile();
				soundProfile.setActive();
				refreshScreen();
			});

			WButton deleteButton = new WButton(new TranslatableText("menu.button.delete.state.soundprofiles"));
			deleteButton.setOnClick(() -> {
				SoundProfiles.profiles.remove(soundProfile);
				refreshScreen();
			});

			plainPanel.add(loadButton, WIDTH/2, i * 45, WIDTH / 3, 40);
			plainPanel.add(deleteButton, WIDTH - WIDTH / 10, i * 45, WIDTH / 10, 40);

			label = new WLabel(soundProfile.getName());
			label.setColor((soundProfile.isActive() ? 0x00C000 : WLabel.DEFAULT_TEXT_COLOR));
			plainPanel.add(label, 0, i * 45, WIDTH / 2, 40);
		}

		// Add above panel to scroll panel to allow scrolling
		WScrollPanel scrollPanel = new WScrollPanel(plainPanel);
		scrollPanel.setScrollingHorizontally(TriState.FALSE);
		scrollPanel.setScrollingVertically(TriState.DEFAULT);

		root.add(scrollPanel, 0, 90, WIDTH, 240 - 80);
		root.validate(this);
	}
}
