package pathology.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.i18n.client.Constants;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import pathology.client.rta.RichTextAreaImproved;
import pathology.client.rta.RichTextToolbar;
import pathology.client.rta.RichTextToolbarMin;

import java.util.Iterator;

/**
 * Created by pg86 on 7/24/14.
 */
public class Editor implements EntryPoint
{


    public void onModuleLoad()
    {
        SplitLayoutPanel splitLayoutPanel = new SplitLayoutPanel(3);

        splitLayoutPanel.ensureDebugId("cwSplitLayoutPanel");

       final SplitLayoutPanel splitLayoutPanel2 = new SplitLayoutPanel(3)
        {
            @Override
            public void onResize() {
                super.onResize();

                int sz = this.getSplitterSize();

                if (this.getWidgetCount() > 0)
                {
                    Iterator<Widget>  iter =  this.getChildren().iterator();

                    while (iter.hasNext())
                    {
                        Widget w = iter.next();
                        if (w instanceof AbsolutePanel)
                        {
                            AbsolutePanel ap = ( AbsolutePanel )w;
                            int h = ap.getElement().getAbsoluteBottom() - ap.getElement().getAbsoluteTop();

                            RichTextAreaImproved rta = ( RichTextAreaImproved ) ap.getWidget(0);

                            rta.getElement().getStyle().setHeight(h-20, Style.Unit.PX);

                        }

                    }







                }

            }
        };

        splitLayoutPanel.addStyleName("splitLayoutPanel");

        splitLayoutPanel.getElement().getStyle()
                .setProperty("border", "3px solid #e7e7e7");


        int winHeight = Window.getClientHeight();

        int winWidth = Window.getClientWidth();

        splitLayoutPanel.addWest(splitLayoutPanel2, winWidth/2);

        splitLayoutPanel2.addStyleName("splitLayoutPanel");
        splitLayoutPanel2.getElement().getStyle()
                .setProperty("border", "3px solid #e7e7e7");

        splitLayoutPanel2.addSouth(makeRtaBlock("South 1"), winHeight/10);

        splitLayoutPanel2.addSouth(makeRtaBlock("South 2"), winHeight/10);

        splitLayoutPanel.addNorth(new HTML("list"), winWidth/5);

        splitLayoutPanel.add(new HTML("details"));

        splitLayoutPanel2.addSouth(makeRtaBlock("Gene"), winHeight/10);

        Button button = new Button("Submit");

        button.addClickHandler(new ClickHandler()
        {

            public void onClick(ClickEvent event)
            {
                //                 if (rtaFormatter != null)
                //                rtaFormatter.removeFormat(); //todo here we can make sure the formatting is set for the entire content

                // EditorService.App.getInstance().submitRTA(rta.getHTML(), new SubmitRichTextAsyncCallback(rta));
            }
        });


        RootLayoutPanel rp = RootLayoutPanel.get();
        rp.add(splitLayoutPanel);

    }

    private Widget makeRtaBlock(String title)
    {
        final RichTextAreaImproved rta = new RichTextAreaImproved();

        rta.setWidth("100%");


        int winHeight = Window.getClientHeight();

       rta.setHeight((winHeight/10)-25 + "px");

       // rta.setHeight("80%");



        rta.setStyleName("gwt-TextArea");
        rta.addStyleName("rta_style");

       final RichTextToolbarMin toolbar = new RichTextToolbarMin(rta, title);

       final AbsolutePanel absolutePanel = new AbsolutePanel();



        absolutePanel.setStyleName("abs-panel");



        absolutePanel.add(rta,0,20);




        absolutePanel.add(toolbar,0,0);



        rta.addScrollHandler(new ScrollHandler()
        {

            @Override
            public void onScroll(ScrollEvent event)
            {
                absolutePanel.remove(toolbar);
                absolutePanel.add(toolbar,0,0);
            }
        });

        rta.addFocusHandler(new FocusHandler()
        {
            @Override
            public void onFocus(FocusEvent event)
            {
               toolbar.setVisible(true);

            }
        });


        rta.addBlurHandler(new BlurHandler()
        {


            @Override
            public void onBlur(BlurEvent event)
            {
                toolbar.setVisible(true);
            }
        });



        absolutePanel.setWidth("100%");

        absolutePanel.setHeight("100%");




//        Grid grid = new Grid(2, 1);
//        grid.setStyleName("cw-RichText");
//        grid.setWidget(0, 0, toolbar);
//        grid.setWidget(1, 0, rta);


//        grid.setWidth("100%");
//        grid.setHeight("100%");
//
//        grid.getCellFormatter().setStyleName(1, 0, "cell-rta");


        return absolutePanel;
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
