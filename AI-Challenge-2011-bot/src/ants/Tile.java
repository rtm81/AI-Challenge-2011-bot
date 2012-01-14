package ants;
/**
 * Represents a tile of the game map.
 */
public class Tile {
    private final int row;
    private final int column;
    
    /**
     * Creates new {@link Tile} object.
     * 
     * @param row row index
     * @param col column index
     */
    public Tile(int row, int col) {
        this.row = row;
        this.column = col;
    }
    public int getRow() {
        return row;
    }
    public int getColumn() {
        return column;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "[" + row + ", " + column + "]";
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + column;
		result = prime * result + row;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Tile other = (Tile) obj;
		if (column != other.column) {
			return false;
		}
		if (row != other.row) {
			return false;
		}
		return true;
	}
}
