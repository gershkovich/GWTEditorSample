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

    private PushButton symbols;


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


        //add symbols handling

        final DialogBox dialogBox = createDialogBox();
        dialogBox.setGlassEnabled(true);
        dialogBox.setAnimationEnabled(true);


        // Create a button to show the dialog Box
        Button openButton = new Button(
                "&zeta;", new ClickHandler() {
            public void onClick(ClickEvent sender) {

                int i = topPanel.getElement().getAbsoluteTop();

                dialogBox.setPopupPosition(300, i - 100);
                dialogBox.show();
            }
        });

        topPanel.add(openButton);

    }


    private DialogBox createDialogBox() {
        // Create a dialog box and set the caption text
        final DialogBox dialogBox = new DialogBox();
        dialogBox.ensureDebugId("cwDialogBox");
        dialogBox.setText("Special Symbols");

        // Create a table to layout the content
        VerticalPanel dialogContents = new VerticalPanel();
        dialogContents.setSpacing(4);
        dialogBox.setWidget(dialogContents);
        Grid symbolGrid = makeSymbolGrid();


        dialogContents.add(symbolGrid);
        dialogContents.setCellHorizontalAlignment(
                symbolGrid, HasHorizontalAlignment.ALIGN_CENTER);


        // Add a close button at the bottom of the dialog
        Button closeButton = new Button(
                "close", new ClickHandler() {
            public void onClick(ClickEvent event) {
                dialogBox.hide();
            }
        });
        dialogContents.add(closeButton);


        // Return the dialog box
        return dialogBox;
    }

    private Grid makeSymbolGrid()
    {

                final String [] greekLetters = {"Alpha","Beta","Gamma","Delta","Epsilon","Zeta","Eta","Theta","Iota","Kappa","Lambda","Mu","Nu","Xi","Omicron","Pi","Rho","Sigma","Tau","Upsilon","Phi","Chi","Psi","Omega"} ;

                Grid grid = new Grid(1, 24);
                grid.setStyleName("cw-RichText");

                grid.setWidth("100%");
                grid.setHeight("100%");

                int pos = 0;

                for (int i = 0; i < 1; i++)
                {
                    for (int j = 0; j < 24; j++)
                    {
                        pos = addSymbolPushButtons(greekLetters, grid, pos, i, j);

                    }

                }

               // grid.getCellFormatter().setStyleName(1, 0, "cell-rta");

        return grid;
    }

    private int addSymbolPushButtons(String[] greekLetters, Grid grid, int pos, int i, int j)
    {

        final PushButton hp = new PushButton();

        final String letterName = greekLetters[pos++];

        hp.setText(letterName.substring(0, 1));

        hp.addClickHandler(new ClickHandler()
        {
            @Override
            public void onClick(ClickEvent event)
            {
                richText.setHTML(richText.getHTML() + " &" + letterName + ";" );
            }
        });

        grid.setWidget(i, j, hp);
        return pos;
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
