package strategie;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import ants.Aim;
import ants.Ants;
import ants.Tile;

public class Fight {

	public void fight(Ants ants, Map<Tile, Aim> plannedOrders, int round) {
		for (Map.Entry<Tile, Aim> entry : plannedOrders.entrySet()) {
			Tile myFightingAnt = entry.getKey();
			fightDecision(ants, entry, myFightingAnt);
		}
	}

	private void fightDecision(Ants ants, Map.Entry<Tile, Aim> entry,
			Tile myFightingAnt) {
		Set<Tile> enemiesInRange = new HashSet<Tile>();
		for (Tile enemy : ants.getEnemyAnts()) {
			if (isInRange(myFightingAnt, enemy, ants, 4)) {
				enemiesInRange.add(enemy);
			}
		}
		if (enemiesInRange.isEmpty())
			return;
		Tile nearestEnemy = ants.getNearest(myFightingAnt, enemiesInRange);
		boolean isLosing = calcIsLosing(ants, enemiesInRange);
		move(ants, entry, myFightingAnt, nearestEnemy,
				isLosing);
	}

	private boolean calcIsLosing(Ants ants, Set<Tile> enemiesInRange) {
		boolean isLosing;

		int lose = 0;
		int win = 0;
		for (Tile enemy : enemiesInRange) {
			Set<Tile> enemyOfEnemy = new HashSet<Tile>();
			for (Tile myAnt : ants.getMyAnts()) {
				if (isInRange(enemy, myAnt, ants, 4)) {
					enemyOfEnemy.add(myAnt);
				}
			}
			if (enemiesInRange.size() >= enemyOfEnemy.size()) {
				lose = possibleLost(lose);
			} else {
				win = possibleWin(win);
			}
		}
		isLosing = (lose >= win);

		return isLosing;
	}

	private void move(Ants ants, Map.Entry<Tile, Aim> entry,
			Tile myFightingAnt, Tile nearestEnemy, boolean isLosing) {
		final List<Aim> directions = ants.getDirections(myFightingAnt,
				nearestEnemy);
		if (isLosing) {
			run(ants, entry, myFightingAnt, directions);
		} else {
			attack(ants, entry, myFightingAnt, directions);
		}
	}

	private int possibleWin(int win) {
		win++;
		return win;
	}

	private void attack(Ants ants, Map.Entry<Tile, Aim> entry, Tile tile,
			List<Aim> directions) {
		for (Aim aim : directions) {
			if (ants.getIlk(tile, aim).isPassable()) {
				attack(entry, aim);
				return;
			}
		}
	}

	private void attack(Map.Entry<Tile, Aim> entry, Aim aim) {
		entry.setValue(aim);
	}

	private void run(Ants ants, Map.Entry<Tile, Aim> entry, Tile tile,
			List<Aim> directions) {
		for (Aim aim : directions) {
			aim = aim.getOpposite();
			if (ants.getIlk(tile, aim).isPassable()) {
				run(entry, aim);
				return;
			}
		}
	}

	private void run(Entry<Tile, Aim> entry, Aim aim) {
		entry.setValue(aim);
	}

	private int possibleLost(int lose) {
		lose = possibleWin(lose);
		return lose;
	}

	private boolean isInRange(Tile tile, Tile enemy, Ants ants, int delta) {
		int x_lower = ants.getRow(tile, delta);
		int x_upper = ants.getRow(tile, -delta);
		int y_right = ants.getColumn(tile, delta);
		int y_left = ants.getColumn(tile, -delta);
		return enemy.getRow() < x_lower && enemy.getRow() > x_upper
				&& enemy.getColumn() < y_right && enemy.getColumn() > y_left;
	}

}