package game.frontend;

import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import static javafx.scene.paint.Color.YELLOW;

public class BoardPanel extends TilePane {

	/*private ImageView[][] cells;

	public BoardPanel(final int rows, final int columns, final int cellSize) {
		setPrefRows(rows);
		setPrefColumns(columns);
		setPrefTileHeight(cellSize);
		setPrefTileWidth(cellSize);
		this.cells = new ImageView[rows][columns];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				cells[i][j] = new ImageView();
				getChildren().add(cells[i][j]);
			}
		}
	}
	
	public void setImage(int row, int column, Image image, boolean color) {
		cells[row][column].setImage(image);
		if(color) {
			Light . Distant spotLight = new Light. Distant ();
			spotLight . setColor ( Color.YELLOW );
			spotLight . setElevation ( 100 );
			Lighting lighting = new Lighting( spotLight );
			cells [ row ][ column ]. setEffect ( lighting );
		}

		public void setImage(int row, int column, Image image) {
			cells[row][column].getChildren().add(new ImageView(image));
		}

		public void setImage(int row, int column, Image image, int bombCounter) {
			setImage(row, column, image);

			DropShadow dropShadow = new DropShadow();
			dropShadow.setRadius(3.0);
			dropShadow.setOffsetX(3.0);
			dropShadow.setOffsetY(3.0);
			dropShadow.setColor(Color.ORANGERED);

			Text text = new Text(String.valueOf(bombCounter));
			text.setFont(Font.font("Impact", FontWeight.BOLD, 40));
			text.setFill(Color.BLACK);
			text.setEffect(dropShadow);
			cells[row][column].getChildren().add(text);
		}
	}*/
	private StackPane[][] cells;

	public BoardPanel(final int rows, final int columns, final int cellSize) {
		setPrefRows(rows);
		setPrefColumns(columns);
		setPrefTileHeight(cellSize);
		setPrefTileWidth(cellSize);
		this.cells = new StackPane[rows][columns];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				cells[i][j] = new StackPane();
				cells[i][j].getChildren().add(new ImageView());
				getChildren().add(cells[i][j]);
			}
		}
	}

	public void setImage(int row, int column, Image image) {
		cells[row][column].getChildren().add(new ImageView(image));
	}

	public void setImage(int row, int column,Image image, boolean isGolden) {
		setImage(row, column, image);

		if (isGolden) {
			Light.Distant spotLight = new Light.Distant();
			spotLight.setColor(YELLOW);
			spotLight.setElevation(100);
			Lighting lighting = new Lighting(spotLight);
			cells[row][column].setEffect(lighting);
		}
	}


	public void setImage(int row, int column, Image image, int bombCounter) {
		setImage(row, column, image);

		DropShadow dropShadow = new DropShadow();
		dropShadow.setRadius(3.0);
		dropShadow.setOffsetX(3.0);
		dropShadow.setOffsetY(3.0);
		dropShadow.setColor(Color.ORANGERED);

		Text text = new Text(String.valueOf(bombCounter));
		text.setFont(Font.font("Impact", FontWeight.BOLD, 40));
		text.setFill(Color.BLACK);
		text.setEffect(dropShadow);
		cells[row][column].getChildren().add(text);
	}


}