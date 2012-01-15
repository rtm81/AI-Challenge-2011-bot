package search;
import java.util.Set;

public interface Problem<U> {

	public Set<U> getActions(U end);

}
