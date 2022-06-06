package battleship.controller;

import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import battleship.model.ShipBoard;
import battleship.model.ShipItem;
import battleship.model.ShipType;

public abstract class AbstractController implements Controller {
	
	protected ShipBoard solutionBoard, playerBoard;
	protected boolean gameOver = false;
	private Map<ShipType, Integer> shipCountMap;

	public  AbstractController(ShipBoard solutionBoard, ShipBoard initialBoard) {
		
		if(solutionBoard.getSize()!=initialBoard.getSize()) {
			throw new IllegalArgumentException("Initial and solution boards must be of the same size, instead of " + initialBoard.getSize() + " vs. " + solutionBoard.getSize());
		}
		this.solutionBoard = solutionBoard;
		this.playerBoard = initialBoard;
		shipCountMap = calcShipCount();
		// System.out.println(shipCountMap);
	}

	@Override
	public ShipItem getCurrentCellItem(int row, int col) {
		return getPlayerBoard().getCell(row, col);
	}

	@Override
	public void setCurrentCellItem(int row, int col, ShipItem value) {
		getPlayerBoard().setCell(row, col, value); 
	}
	
	@Override
	public ShipItem getSolutionCellItem(int row, int col) {
		return getSolutionBoard().getCell(row, col);
	}

	@Override
	public  Map<ShipType, Integer> getShipCount() {
		return shipCountMap;
	}
	
	private Map<ShipType, Integer> calcShipCount() {
		/* 
		 * Se non si fanno ipotesi sul numero e tipo di navi, non resta che contarle tutte esaustivamente,
		 * in orizzontale e verticale, perch� la relazione fra numero/tipo di navi e numero/tipo di item
		 * d� luogo a un sistema di due equazioni e tre incognite che ha potenzialmente pi� soluzioni.
		 * 
		 * Per questo il conteggio esaustivo � l'approccio usato qui, anche se poco efficiente.
		 * 
		 * In alternativa, se introduce una ipotesi aggiuntiva, come ad esempio una delle seguenti:
		 * - c'� una sola portaerei
		 * - il numero di incrociatori (3 caselle) � sempre di 1 superiore al numero di cacciatorpedinieri (2 ceselle)
		 * diventa possibile dedurre il tipo/numero di navi dal semplice conteggio degli item, senza altri conteggi.
		 * Infatti, detti:
		 *   NP = numero portaerei, NI = numero incrociatori, NC = numero cacciatorpedinieri
		 *   E = item estremi (up, down, left, right), C = item centrali (si trascurano i sommergibili perch� identificabili da soli) 
		 * Poich� strutturalmente:
		 *   portaerei = 2E+2C, incrociatore = 2E+C, cacciatorpediniere = 2E
		 * valgono le relazioni:
		 *   E = 2(NP + NI + NC), 	C = 2 NP + NI 
		 * dove E,C sono noti (basta contare i rispettivi item nella scacchiera), mentre NP,NI,NC sono le incognite.
		 * 
		 * Ad esempio nel caso del gioco usato nel mock e nei test, il conteggio degli item fornisce E = 12, C = 4
		 * da cui NP + NI + NC = 6, 2NP+NI = 4 che ammette due soluzioni:
		 *  - NP=1, NI=2, NC=3 (che � quella dell'esempio)  
		 *  - NP=2, NI=0, NC=4 (altra soluzione compatibile con quel conteggio di item)
		 * 
		 * */
		Map<ShipType, Integer> map = new TreeMap<>();
		for (int row = 0; row < getSize(); row++) countShipsAndSubmarinesInRow(row, map);
		for (int col = 0; col < getSize(); col++) countShipsInColumn(col, map);
		return map;
	}

	private void countShipsInColumn(int col, Map<ShipType, Integer> map) {
		int row=0, shipLength=0;
		while (row<getSize()) {
			ShipItem item = getSolutionCellItem(row,col);
			if(item==ShipItem.UP) {
				int startPos = row;
				row++;
				while (row<getSize() && getSolutionCellItem(row,col)!=ShipItem.DOWN) row++;
				shipLength = row-startPos+1;
			}
			else row++;
			//
			if(shipLength>=2) {
				int currentShipTypeNumber = map.getOrDefault(ShipType.of(shipLength),0);
				map.put(ShipType.of(shipLength), currentShipTypeNumber+1);
				shipLength=0;
			}
		}
	}

	private void countShipsAndSubmarinesInRow(int row, Map<ShipType, Integer> map) {
		int col=0, shipLength=0;
		while (col<getSize()) {
			ShipItem item = getSolutionCellItem(row,col);
			// System.out.println("Analyzing item " + item + " in row " + row + " at col " + col);
			if(item==ShipItem.SINGLE) {
				shipLength=1; col++;
			}
			else
			if(item==ShipItem.LEFT) {
				int startPos = col;
				col++;
				while (col<getSize() && getSolutionCellItem(row,col)!=ShipItem.RIGHT) col++;
				shipLength = col-startPos+1;
				// System.out.println("Apparently found ship of len " + shipLength);
			}
			else col++;
			// System.out.println("Current ship len: " + shipLength);
			if(shipLength>=1) {
				int currentShipTypeNumber = map.getOrDefault(ShipType.of(shipLength),0);
				map.put(ShipType.of(shipLength), currentShipTypeNumber+1);
				shipLength=0;
			}
		}
	}
	
	@Override
	public int getSize() {
		return solutionBoard.getSize();
	}

	@Override
	public int[] getCountingRow() {
		return solutionBoard.getCountingRow();
	}
	
	@Override
	public int[] getCountingColumn() {
		return solutionBoard.getCountingCol();
	}
	
	protected ShipBoard getSolutionBoard() {  
		return solutionBoard;
	}

	protected ShipBoard getPlayerBoard() {  
		return playerBoard;
	}

	@Override
	public boolean isGameOver() {
		return gameOver;
	}
	
	@Override
	public String getShipList() {
		return getShipCount().entrySet().stream().map(Map.Entry::toString).collect(Collectors.joining("\n"));
		/*
			"1 portaerei da 4 elementi\n" +
			"2 incrociatori da 3 elementi\n" +
			"3 cacciatorpedinieri da 2 elementi\n" +
			"3 sommergibili da 1 solo elemento";
		*/
	}
}
