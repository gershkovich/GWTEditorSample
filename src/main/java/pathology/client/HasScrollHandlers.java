package pathology.client;

/**
 * Created by IntelliJ IDEA.
 * User: nm343
 * Date: 10/2/13
 * Time: 4:04 PM
 */

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;

/**
 * A widget that implements this interface is a public source of
 * {@link ScrollEvent} events.
 *
 * @param <T> the type being printed
 */
public interface HasScrollHandlers<T> extends HasHandlers
{
	/**
	 *
	 * @param handler the handler
	 * @return the registration for the event
	 */
	HandlerRegistration addScrollHandler(ScrollHandler<T> handler);
}
