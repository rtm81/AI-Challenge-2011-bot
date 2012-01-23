package ants;

import java.util.HashMap;
import java.util.Map;

public class Ant {

	enum OrderType {
		FOOD, OWN_HILL, MOVE_FROM_OWN_HILL, ANY_UNOCCUPIED
	}

	private final Tile tile;

	private final Map<OrderType, Aim> orders = new HashMap<OrderType, Aim>();

	public Ant(final Tile tile) {
		this.tile = tile;
	}

	public void addOrder(OrderType orderType, Aim aim) {
		orders.put(orderType, aim);
	}
}
