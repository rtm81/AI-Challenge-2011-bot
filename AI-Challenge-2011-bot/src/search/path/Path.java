package search.path;


public interface Path<T extends Number & Comparable<? super T>, U> extends
		Comparable<Path<T, U>> {

	public abstract Path<T, U> create(U end);

	public abstract <X extends Number> X getLength();

	public abstract U getEnd();

	public abstract Path<T, U> getParent();

}