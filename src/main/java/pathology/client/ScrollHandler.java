package pathology.client;

/**
 * Created by IntelliJ IDEA.
 * User: nm343
 * Date: 10/2/13
 * Time: 3:54 PM
 */

import com.google.gwt.event.shared.EventHandler;


/**
 *
 *
 * @param <T> the type for print
 */
public interface ScrollHandler<T> extends EventHandler
{
	/**
	 *
	 * @param event the {@link com.google.gwt.event.logical.shared.CloseEvent} that was fired
	 */
	void onScroll(ScrollEvent<T> event);
}