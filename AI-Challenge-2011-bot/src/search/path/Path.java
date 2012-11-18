package search.path;

/**
 * A path for AI computation.
 * 
 * Comparable boils down to comparison of the length.
 * 
 * @author christopher.roscoe
 * 
 * @param <T>
 *            the type of the length. Extends Number & Comparable<? super T>
 * @param <U>
 *            the type of the node. (should implement
 *            {@link Object#equals(Object)} and {@link Object#hashCode()})
 */
public interface Path<T extends Number & Comparable<? super T>, U> extends
		Comparable<Path<T, U>> {

	public abstract Path<T, U> create(U end);

	public abstract T getLength();

	public abstract U getEnd();

	public abstract Path<T, U> getParent();

	public String stepsToString();

}