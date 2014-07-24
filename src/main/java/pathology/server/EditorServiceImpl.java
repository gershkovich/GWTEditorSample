package pathology.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import pathology.PrintInstruction;
import pathology.client.EditorService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pg86 on 7/24/14.
 */
public class EditorServiceImpl extends RemoteServiceServlet implements EditorService
{

    public String submitRTF(String rtf)
    {
        // System.out.println(rtf);

        Document doc = Jsoup.parseBodyFragment(rtf);

        System.out.println("HTML:" + doc.html());

        Element els = doc.body();  //the only elen

        ArrayList<PrintInstruction> printInstructions = new ArrayList<PrintInstruction>();



        for ( Element el : els.getAllElements() )
        {

            System.out.println("Element:" + el.tagName());

            for ( Node node : el.childNodes() )
            {
                if ( node.nodeName().equalsIgnoreCase("#text") )
                {
                    printNode(printInstructions, node);
                    //test
                }
            }

            if ( StringUtils.isEmpty(el.tagName()) )
            {
                System.out.println("Text:" + el.text());

            }

            //first we extract text that may be in the body element if we try to get text it will be entire text


            //            if ( "b".equalsIgnoreCase(el.tagName()) )
            //            {
            //                if ( el.children().size() > 0 )
            //                {
            //                    isBold = true;
            //                }
            //                else
            //                {
            //                    List<TextNode> txtElements = el.textNodes();
            //
            //                    for ( TextNode node : txtElements )
            //                    {
            //                        System.out.println("Bold: " + el.text());
            //
            //                    }
            //                }
            //
            //
            //            }
            //            if ( "i".equalsIgnoreCase(el.nodeName()) )
            //            {
            //                System.out.println("Italic: " + el.text());
            //            }
            //            else
            //            {
            //                List<TextNode> txtElements = el.textNodes();
            //
            //                for ( TextNode node : txtElements )
            //                {
            //                    if ( isBold )
            //                    {
            //                        System.out.println("Bold: " + node.toString());
            //                        isBold = false;
            //
            //                    }
            //                    else
            //                    {
            //                        System.out.println(node.toString());
            //                    }
            //
            //                }
            //            }


            //            String linkText = el.text();
            //            System.out.println("body text:\t" + linkText);
        }


        for ( PrintInstruction inst : printInstructions )
        {
            System.out.println(inst.getText());

        }


        return null;
    }

    private void printNode(ArrayList<PrintInstruction> printInstructions, Node node)
    {

        PrintInstruction printInstruction = new PrintInstruction();

        printInstruction.setText(node.toString());

        printInstructions.add(printInstruction);

    }
}