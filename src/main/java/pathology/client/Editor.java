package pathology.client;

import com.google.gwt.core.client.EntryPoint;
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

        final  RichTextArea rta = new RichTextArea();

        // Get formatters - will be null if not available


        final RichTextArea.Formatter rtaFormatter = rta.getFormatter() ;


        // Main container panel
        VerticalPanel panel = new VerticalPanel();
        //  panel.addStyleName("demo-panel-padded");

        // Add the RichTextArea
        panel.add(rta);
        panel.setCellWidth(rta, "100%");
        rta.setWidth("100%");

        // Create a button box and add it to the main panel
        HorizontalPanel buttons = new HorizontalPanel();
        panel.add(buttons);
        panel.setCellWidth(buttons, "100%");

        // A simple button to show the text - anything can do this
        buttons.add(new Button("Show Text", new ClickListener()
        {
            public void onClick(Widget sender)
            {
                Window.alert(rta.getText());
            }
        }));

        // A simple button to show the HTML - anything can do this, too
        buttons.add(new Button("Show HTML", new ClickListener()
        {
            public void onClick(Widget sender)
            {
                Window.alert(rta.getHTML());
            }
        }));

        // A button to toggle 'bold' - BasicFormatters and above - note that the
        // button won't be shown if the browser can't do this so no checking is
        // required when the button is pressed
        if (rtaFormatter != null)
        {
            buttons.add(new Button("Toggle Bold", new ClickListener()
            {
                public void onClick(Widget sender)
                {
                    rtaFormatter.toggleBold();
                }
            }));
        }

        if (rtaFormatter != null)
        {
            buttons.add(new Button("Toggle Italic", new ClickListener()
            {
                public void onClick(Widget sender)
                {
                    rtaFormatter.toggleItalic();
                }
            }));
        }
        // A button to toggle 'bold' - ExtendedFormatters only - note that the
        // button won't be shown if the browser can't do this so no checking is
        // required when the button is pressed
        if (rtaFormatter != null)
        {
            buttons.add(new Button("Add Rule", new ClickListener()
            {
                public void onClick(Widget sender)
                {
                    rtaFormatter.insertHorizontalRule();
                }
            }));
        }


        buttons.add(new Button("Show Text", new ClickListener()
        {
            public void onClick(Widget sender)
            {

               // rtaFormatter. //todo here we can make sure the formatting is set for the entire content

                EditorService.App.getInstance().submitRTF(rta.getHTML(), new SubmitRichTextAsyncCallback());
            }
        }));





        RootPanel.get().add(panel);
    }


    private class SubmitRichTextAsyncCallback implements AsyncCallback<String>
    {
        public void onFailure(Throwable caught)
        {

        }

        public void onSuccess(String result)
        {
            if (result!=null)
            {

            }

        }
    }
}
