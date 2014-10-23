package pathology.client.rta;


import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.i18n.client.Constants;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;

/**
 * A sample toolbar for use with {@link RichTextArea}. It provides a simple UI
 * for all rich text formatting, dynamically displayed only for the available
 * functionality.
 */

public class RichTextToolbarMin extends Composite
{
  private class EventHandler implements ClickHandler, ChangeHandler,
                                          KeyUpHandler, FocusHandler, BlurHandler
    {

        public void onChange(ChangeEvent event)
        {


        }

        public void onClick(ClickEvent event)
        {

            Widget sender = ( Widget ) event.getSource();

            if ( sender == bold )
            {
                formatter.toggleBold();
            }
            else if ( sender == italic )
            {
                formatter.toggleItalic();
            }
            else if ( sender == underline )
            {
                formatter.toggleUnderline();
            }
            else if ( sender == removeFormat )
            {
                formatter.removeFormat();
            }
            else if ( sender == richText )
            {
                updateStatus();
            }
        }

        public void onKeyUp(KeyUpEvent event)
        {

            Widget sender = ( Widget ) event.getSource();
            if ( sender == richText )
            {
                updateStatus();
            }
        }

        @Override
        public void onBlur(BlurEvent event)
        {
          outer.setVisible(false);
        }

        @Override
        public void onFocus(FocusEvent event)
        {
            outer.setVisible(true);

        }
    }

    private EventHandler handler = new EventHandler();

    private RichTextArea richText;

    private RichTextArea.Formatter formatter;

    private VerticalPanel outer = new VerticalPanel();

    private HorizontalPanel topPanel = new HorizontalPanel();

    private ToggleButton bold;

    private ToggleButton italic;

    private ToggleButton underline;

    private PushButton removeFormat;


    /**
     * Creates a new toolbar that drives the given rich text area.
     *
     * @param richText the rich text area to be controlled
     */
    public RichTextToolbarMin(RichTextArea richText, String title)
    {
        outer.setVisible(false);

        this.richText = richText;

        this.formatter = richText.getFormatter();

        outer.add(topPanel);
        // outer.add(bottomPanel);
        // topPanel.setWidth("100%");
        outer.setWidth("100%");
        //bottomPanel.setWidth("100%");

        initWidget(outer);
        setStyleName("gwt-RichTextToolbar");
        //richText.addStyleName("hasRichTextToolbar");

       Label titleLabel =  new Label(title);

       titleLabel.setStyleName("title_label");

        topPanel.add(titleLabel);

        if ( formatter != null )
        {

            topPanel.add(bold = createToggleButton("b", "bold"));

            topPanel.add(italic = createToggleButton("i",
                    "italic"));
            topPanel.add(underline = createToggleButton("u",
                    "underline"));



            richText.addKeyUpHandler(handler);
            richText.addClickHandler(handler);
        }
    }


    private ToggleButton createToggleButton(String label, String tip)
    {

        ToggleButton tb = new ToggleButton(label);
        tb.addClickHandler(handler);
        tb.setWidth("20px");
        tb.addStyleName("toggle_bt");
        tb.setTitle(tip);
        return tb;
    }

    /**
     * Updates the status of all the stateful buttons.
     */
    private void updateStatus()
    {

        if ( formatter != null )
        {
            bold.setDown(formatter.isBold());
            italic.setDown(formatter.isItalic());
            underline.setDown(formatter.isUnderlined());

        }

    }
}
