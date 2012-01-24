package ants;

import java.util.HashMap;
import java.util.Map;

public class Ant {

	enum OrderType {
		FOOD(10), OWN_HILL(9), MOVE_FROM_OWN_HILL(8), ANY_UNOCCUPIED(1), NONE(0);

		private final int priority;

		private OrderType(final int priority) {
			this.priority = priority;
		}

		public static OrderType getTopPrio(Iterable<OrderType> orders) {
			OrderType result = NONE;
			for (OrderType orderType : orders) {
				if (orderType.priority > result.priority) {
					result = orderType;
				}
			}
			return result;
		}
	}

	private final Tile tile;

	private final Map<OrderType, Aim> orders = new HashMap<OrderType, Aim>();

	public Ant(final Tile tile) {
		this.tile = tile;
	}

	public void addOrder(OrderType orderType, Aim aim) {
		orders.put(orderType, aim);
	}

	public Tile getTile() {
		return tile;
	}

	public Aim getOrder() {
		OrderType topPrio = OrderType.getTopPrio(orders.keySet());
		return orders.get(topPrio);
	}
}
