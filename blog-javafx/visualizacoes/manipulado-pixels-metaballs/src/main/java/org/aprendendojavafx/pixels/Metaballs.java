package org.aprendendojavafx.pixels;

import org.fxapps.drawingfx.DrawingApp;

import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class Metaballs extends DrawingApp {

	int COLOR_MODES = 7;
	Blob[] blobs = new Blob[12];
	int selectedColorMode = 1;
	boolean inverter = false;

	public static void main(String[] args) {
		launch();
	}

	public void setup() {
		for (int i = 0; i < blobs.length; i++) {
			blobs[i] = new Blob(random.nextFloat() * width, random.nextFloat() * height, this);
		}

		title = "Metaballs with JavaFX";

		width = 600;
		height = 400;

	}

	@Override
	public void mouseCliked(MouseEvent e) {
		if (e.isControlDown())
			inverter = !inverter;
		else if (selectedColorMode > COLOR_MODES)
			selectedColorMode = 1;
		else
			selectedColorMode++;
	}

	public void draw() {
		ctx.clearRect(0, 0, width, height);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				int total = 0;
				for (Blob blob : blobs) {
					double d = distance(x, y, blob.posX, blob.posY);
					total += 15 * (blob.raio / d);
				}
				if (total > 255)
					total = 255;
				Color color = selectColor(total);
				ctx.getPixelWriter().setColor(x, y, color);
			}
		}

		for (int i = 0; i < blobs.length; i++) {
			blobs[i].update();
		}

	}

	private Color selectColor(int total) {
		int inverso = 255;
		if (inverter) {
			inverso = 255 % total;
		}
		Color color = Color.rgb(total, total, total);
		switch (selectedColorMode) {
		case (1):
			color = Color.rgb(total, total, total);
			break;
		case (2):
			color = Color.rgb(total, total, inverso);
			break;
		case (3):
			color = Color.rgb(total, inverso, total);
			break;
		case (4):
			color = Color.rgb(total, inverso, inverso);
			break;
		case (5):
			color = Color.rgb(inverso, total, total);
			break;
		case (6):
			color = Color.rgb(inverso, total, inverso);
			break;
		case (7):
			color = Color.rgb(inverso, inverso, total);
		}
		return color;
	}

}