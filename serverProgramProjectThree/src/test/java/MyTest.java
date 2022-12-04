import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MyTest {

	// Function being evaluated
	public boolean isValidMove(int[][] gameBoard, int r, int c) {
		// bound check
		if (c > 6 || c < 0 || r > 5 || r < 0)
			return false;
		// case where position is occupied
		if (r == 5) {
			if (gameBoard[r][c] == 0) {
				return true;
			}
		} else {
			if (gameBoard[r + 1][c] != 0)
				return true;
		}
		return false;
	}

	public boolean areFourConnected(int[][] gameBoard, int player) {
		// horizontalCheck
		for (int j = 0; j < 5; j++) {
			for (int i = 0; i < 6; i++) {
				if (gameBoard[i][j] == player && gameBoard[i][j + 1] == player
						&& gameBoard[i][j + 2] == player && gameBoard[i][j + 3] == player) {
					return true;
				}
			}
		}
		// verticalCheck
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 7; j++) {
				if (gameBoard[i][j] == player && gameBoard[i + 1][j] == player
						&& gameBoard[i + 2][j] == player && gameBoard[i + 3][j] == player) {
					return true;
				}
			}
		}
		// ascendingDiagonalCheck
		for (int i = 3; i < 6; i++) {
			for (int j = 0; j < 3; j++) {
				if (gameBoard[i][j] == player && gameBoard[i - 1][j + 1] == player
						&& gameBoard[i - 2][j + 2] == player && gameBoard[i - 3][j + 3] == player)
					return true;
			}
		}
		// descendingDiagonalCheck
		for (int i = 3; i < 6; i++) {
			for (int j = 3; j < 7; j++) {
				if (gameBoard[i][j] == player && gameBoard[i - 1][j - 1] == player
						&& gameBoard[i - 2][j - 2] == player && gameBoard[i - 3][j - 3] == player)
					return true;
			}
		}
		return false;
	}

	@Test
	void testInBoundInvalidMoves() {
		// test with within bound and invalid moves
		int[][] gameBoard = {
				{ 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 2, 0, 0, 0 },
				{ 2, 1, 1, 1, 2, 0, 0 }
		};

		assertFalse(isValidMove(gameBoard, 3, 2));
		assertFalse(isValidMove(gameBoard, 2, 0));
		assertFalse(isValidMove(gameBoard, 1, 5));
		assertFalse(isValidMove(gameBoard, 4, 6));
		assertFalse(isValidMove(gameBoard, 1, 0));
	}

	@Test
	void testOutBoundRowMoves() {

		int[][] gameBoard = {
				{ 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 1, 0, 0, 0 },
				{ 0, 0, 2, 1, 0, 0, 0 },
				{ 2, 2, 1, 1, 0, 0, 0 }
		};
		// test with within bound and invalid moves
		assertFalse(isValidMove(gameBoard, 100, 3));
		assertFalse(isValidMove(gameBoard, 200, 0));
		assertFalse(isValidMove(gameBoard, 6, 0));
		assertFalse(isValidMove(gameBoard, -1, 1));
		assertFalse(isValidMove(gameBoard, -2, 0));
	}

	@Test
	void testOutBoundColumnMoves() {

		int[][] gameBoard = {
				{ 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 1, 2, 1, 0 },
				{ 0, 0, 0, 1, 2, 1, 0 }
		};
		// test with within bound and invalid moves
		assertFalse(isValidMove(gameBoard, 0, -1));
		assertFalse(isValidMove(gameBoard, 2, -2));
		assertFalse(isValidMove(gameBoard, 3, -3));
		assertFalse(isValidMove(gameBoard, 5, 7));
		assertFalse(isValidMove(gameBoard, 1, 8));
	}

	@Test
	void testOccupiedMoves() {

		int[][] gameBoard = {
				{ 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 2, 0, 0, 0, 0 },
				{ 0, 0, 1, 1, 2, 1, 0 },
				{ 0, 0, 1, 1, 2, 1, 0 }
		};
		// test with within bound and invalid moves
		assertFalse(isValidMove(gameBoard, 5, 2));
		assertFalse(isValidMove(gameBoard, 5, 3));
		assertTrue(isValidMove(gameBoard, 4, 2));
		assertTrue(isValidMove(gameBoard, 4, 3));
		assertTrue(isValidMove(gameBoard, 4, 5));
	}

	@Test
	void testValidMoves() {

		int[][] gameBoard = {
				{ 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0 }
		};
		// test with within bound and invalid moves
		assertTrue(isValidMove(gameBoard, 5, 0));
		assertTrue(isValidMove(gameBoard, 5, 1));
		assertTrue(isValidMove(gameBoard, 5, 2));
		assertTrue(isValidMove(gameBoard, 5, 3));
		assertTrue(isValidMove(gameBoard, 5, 4));
		assertTrue(isValidMove(gameBoard, 5, 5));
		assertTrue(isValidMove(gameBoard, 5, 6));
	}

	@Test
	void testValidMovesAdvance() {

		int[][] gameBoard = {
				{ 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 2, 1, 0, 0 },
				{ 0, 0, 1, 2, 1, 0, 0 },
				{ 0, 0, 1, 2, 2, 1, 0 }
		};
		// test with within bound and invalid moves
		assertTrue(isValidMove(gameBoard, 5, 1));
		assertTrue(isValidMove(gameBoard, 2, 3));
		assertTrue(isValidMove(gameBoard, 2, 4));
		assertTrue(isValidMove(gameBoard, 3, 2));
		assertTrue(isValidMove(gameBoard, 4, 5));
		assertTrue(isValidMove(gameBoard, 5, 0));
		assertTrue(isValidMove(gameBoard, 5, 6));
	}

	@Test
	void testFourInARowHorizontal() {

		int[][] gameBoard = {
				{ 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 1, 0, 0 },
				{ 0, 0, 1, 2, 1, 0, 0 },
				{ 0, 1, 1, 1, 1, 1, 0 }
		};
		// test with within bound and invalid moves
		assertTrue(areFourConnected(gameBoard, 1));
	}

	@Test
	void testFourInARowHorizontal2() {

		int[][] gameBoard = {
				{ 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 1, 0, 0 },
				{ 0, 2, 2, 2, 2, 0, 0 },
				{ 0, 1, 0, 0, 0, 1, 0 }
		};
		// test with within bound and invalid moves
		assertTrue(areFourConnected(gameBoard, 2));
	}

	@Test
	void testFourInARowVerticle() {

		int[][] gameBoard = {
				{ 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 1, 0, 0, 0, 0, 0 },
				{ 0, 1, 0, 0, 1, 0, 0 },
				{ 0, 1, 2, 2, 2, 0, 0 },
				{ 0, 1, 0, 0, 0, 1, 0 }
		};
		// test with within bound and invalid moves
		assertTrue(areFourConnected(gameBoard, 1));
	}

	@Test
	void testFourInARowVerticle2() {

		int[][] gameBoard = {
				{ 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 2, 0 },
				{ 0, 1, 0, 0, 1, 2, 0 },
				{ 0, 1, 2, 0, 0, 2, 0 },
				{ 0, 1, 0, 0, 0, 2, 0 }
		};
		// test with within bound and invalid moves
		assertTrue(areFourConnected(gameBoard, 2));
	}

	@Test
	void testFourInARowDiagonal() {

		int[][] gameBoard = {
				{ 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 1, 0 },
				{ 0, 1, 0, 0, 1, 2, 0 },
				{ 0, 1, 2, 1, 0, 2, 0 },
				{ 0, 1, 1, 0, 0, 2, 0 }
		};
		// test with within bound and invalid moves
		assertTrue(areFourConnected(gameBoard, 1));
	}

	@Test
	void testFourInARowDiagonal2() {

		int[][] gameBoard = {
				{ 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 2, 0, 0, 1, 0 },
				{ 0, 1, 0, 2, 1, 2, 0 },
				{ 0, 1, 2, 1, 2, 2, 0 },
				{ 0, 1, 1, 0, 0, 2, 0 }
		};
		// test with within bound and invalid moves
		assertTrue(areFourConnected(gameBoard, 2));
	}

	@Test
	void testFourInARowDiagonal3() {

		int[][] gameBoard = {
				{ 0, 0, 0, 0, 0, 0, 1 },
				{ 0, 0, 0, 0, 0, 1, 0 },
				{ 0, 0, 1, 0, 1, 1, 0 },
				{ 0, 1, 0, 0, 1, 2, 0 },
				{ 0, 1, 0, 1, 1, 0, 0 },
				{ 0, 1, 1, 0, 0, 1, 0 }
		};
		// test with within bound and invalid moves
		assertTrue(areFourConnected(gameBoard, 1));
	}

}
