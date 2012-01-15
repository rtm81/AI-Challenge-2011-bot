package search.path;

public interface Path<T extends Number & Comparable<T>, U> extends
		Comparable<Path<T, U>> {

	public T getLength();

	public U getEnd();

}