package pathology.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;

/**
 * Created by pg86 on 7/24/14.
 */
public class Editor implements EntryPoint
{

    public void onModuleLoad()
    {

        final RichTextArea rta = new RichTextArea();

        rta.addStyleName("rta_style");

        // Get formatters - will be null if not available


        final RichTextArea.Formatter rtaFormatter = rta.getFormatter();

        RichTextToolbar toolbar = new RichTextToolbar(rta);

        // Main container panel
        VerticalPanel panel = new VerticalPanel();
        panel.addStyleName("rta_style");


        // Add the RichTextArea
        panel.add(rta);
        panel.setCellWidth(rta, "100%");
        rta.setWidth("100%");

        // Create a button box and add it to the main panel
        HorizontalPanel buttons = new HorizontalPanel();
        panel.add(toolbar);
        panel.setCellWidth(buttons, "100%");

        panel.add(buttons);

        // A simple button to show the text - anything can do this
        buttons.add(new Button("Show Text", new ClickHandler()
        {

            public void onClick(ClickEvent event)
            {

                Window.alert(rta.getText());
            }
        }));

        // A simple button to show the HTML - anything can do this, too
        buttons.add(new Button("Show HTML", new ClickHandler()
        {

            public void onClick(ClickEvent event)
            {

                Window.alert(rta.getHTML());
            }
        }));

        // A button to toggle 'bold' - BasicFormatters and above - note that the
        // button won't be shown if the browser can't do this so no checking is
        // required when the button is pressed
        if ( rtaFormatter != null )
        {
            buttons.add(new Button("b", new ClickHandler()
            {

                public void onClick(ClickEvent event)
                {

                    rtaFormatter.toggleBold();
                }
            }));
        }

        if ( rtaFormatter != null )
        {
            Button italic = new Button("i", new ClickHandler()
            {

                public void onClick(ClickEvent event)
                {

                    rtaFormatter.toggleItalic();
                }
            });

            italic.setStyleName("button_italic");

            buttons.add(italic);
        }


        if ( rtaFormatter != null )
        {
            buttons.add(new Button("Font Large", new ClickHandler()
            {

                public void onClick(ClickEvent event)
                {

                    rtaFormatter.setFontSize(RichTextArea.FontSize.LARGE);
                }
            }));
        }

        if ( rtaFormatter != null )
        {
            buttons.add(new Button("Font med", new ClickHandler()
            {

                public void onClick(ClickEvent event)
                {

                    rtaFormatter.setFontSize(RichTextArea.FontSize.MEDIUM);
                }
            }));
        }

        if ( rtaFormatter != null )
        {
            buttons.add(new Button("Font Small", new ClickHandler()
            {

                public void onClick(ClickEvent event)
                {

                    rtaFormatter.setFontSize(RichTextArea.FontSize.SMALL);
                }
            }));
        }


        // A button to toggle 'bold' - ExtendedFormatters only - note that the
        // button won't be shown if the browser can't do this so no checking is
        // required when the button is pressed
        if ( rtaFormatter != null )
        {
            buttons.add(new Button("Add Rule", new ClickHandler()
            {

                public void onClick(ClickEvent event)
                {

                    rtaFormatter.insertHorizontalRule();
                }
            }));
        }


        buttons.add(new Button("Remove Format", new ClickHandler()
        {

            public void onClick(ClickEvent event)
            {
                //                 if (rtaFormatter != null)
                //                rtaFormatter.removeFormat(); //todo here we can make sure the formatting is set for the entire content

                EditorService.App.getInstance().submitRTF(rta.getHTML(), new SubmitRichTextAsyncCallback(rta));
            }
        }));


        rta.addKeyUpHandler(new KeyUpHandler()
        {

            @Override
            public void onKeyUp(KeyUpEvent event)
            {

                rta.getHTML();

            }
        });


        RootPanel.get().add(panel);
    }


    private class SubmitRichTextAsyncCallback implements AsyncCallback<String>
    {

        RichTextArea rta;

        public SubmitRichTextAsyncCallback(RichTextArea rtaIn)
        {

            rta = rtaIn;

        }

        public void onFailure(Throwable caught)
        {

        }

        public void onSuccess(String result)
        {
            if ( result != null )
            {
                rta.setHTML(result);

            }

        }
    }
}
