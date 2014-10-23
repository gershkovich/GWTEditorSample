package pathology.client.rta;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.RichTextArea;

import pathology.client.HasScrollHandlers;
import pathology.client.ScrollEvent;
import pathology.client.ScrollHandler;

/**
 * Created by pg86 on 10/23/14.
 */
public class RichTextAreaImproved  extends RichTextArea implements HasScrollHandlers<RichTextAreaImproved>

{

    public RichTextAreaImproved()
    {
        super();

    }

    private void scroll()
    {
        ScrollEvent.fire(this, this);
    }

    public HandlerRegistration addScrollHandler( ScrollHandler<RichTextAreaImproved> handler )
    {
        return addHandler(handler, ScrollEvent.getType());
    }
}
