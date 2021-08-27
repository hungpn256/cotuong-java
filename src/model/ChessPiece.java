package model;
import java.awt.Color;

public class ChessPiece {
	// thuộc tính quân cờ
	private Color color;
	private String name;
	// vị trí quân cờ
	private int x;
	private int y;
	private boolean selected = false;// click chọn quân cờ
	public ChessPiece() {
	}

	public ChessPiece(Color color, String name, int x, int y) {
		this.color = color;
		this.name = name;
		this.x = x;
		this.y = y;
		this.selected = false;
	}

	public Color getColor() {
		return this.color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getX() {
		return this.x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return this.y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public boolean getSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

}
