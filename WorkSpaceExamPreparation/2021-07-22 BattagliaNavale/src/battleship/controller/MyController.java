package battleship.controller;

import battleship.model.ShipBoard;
import battleship.model.ShipItem;

public class MyController extends AbstractController implements Controller {

	public MyController(ShipBoard solutionBoard, ShipBoard initialBoard) {
		super(solutionBoard, initialBoard);
	}

	@Override
	public int verify() {
		
		int response = 0;
		gameOver = true;
		for (int row = 0 ; row < getSize() ; ++row) {
			for (int column = 0 ; column < getSize() ; ++column) {
				ShipItem playerValue = playerBoard.getCell(row, column);
				if (playerValue != ShipItem.EMPTY) {
					if (playerValue != solutionBoard.getCell(row, column)) {
						++response;
						playerBoard.setCell(row, column, ShipItem.EMPTY);
					}
				} else if (gameOver) gameOver = false;
					
			}
		}
		if (gameOver && response != 0) gameOver = false;
		return response;
	}

}
