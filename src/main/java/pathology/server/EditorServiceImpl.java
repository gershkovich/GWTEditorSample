package pathology.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import org.jsoup.select.NodeVisitor;
import pathology.PrintInstruction;
import pathology.client.EditorService;

import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.rtf.RTFEditorKit;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pg86 on 7/24/14.
 */
public class EditorServiceImpl extends RemoteServiceServlet implements EditorService
{

    public String submitRTA(String rtaText)
    {
        String dc = filterHTML(rtaText);

        System.out.println("HTML: \n" + dc);

        System.out.println(dc);

        String rtfVersion = convertHTMLtoRTF(dc);

        System.out.println("After rft filter:\n" + rtfVersion);


        String htmlVersion = convertRTFtoHTML(rtfVersion);

        System.out.println("After html filter:\n" + filterHTML(htmlVersion));

        return filterHTML(htmlVersion);
    }

    private String filterHTML(String htmlString)
    {
        StringBuilder dc = new StringBuilder();

        Document doc = Jsoup.parseBodyFragment(htmlString);




        doc.traverse(new NodeVisitor() {
            boolean foundText = false;

            public void head(Node node, int depth) {

                //we need to find text and ignore empty lines


                if ("#text".equalsIgnoreCase(node.nodeName()) && StringUtils.isNotEmpty(node.toString().trim()))
                {
                    foundText = true;

                    dc.append(node.toString());

                } else if (foundText && ("p".equalsIgnoreCase(node.nodeName()) ||
                        "b".equalsIgnoreCase(node.nodeName()) ||
                        "i".equalsIgnoreCase(node.nodeName()))  ||
                        "blockquote".equalsIgnoreCase(node.nodeName()))
                {
                    dc.append("<");
                    dc.append(node.nodeName());
                    dc.append(">");
                } else if (foundText && ("img".equalsIgnoreCase(node.nodeName())))
                {

                    dc.append(node.toString());
                }
            }
            public void tail(Node node, int depth) {

                if (foundText && ("p".equalsIgnoreCase(node.nodeName()) ||
                        "b".equalsIgnoreCase(node.nodeName()) ||
                        "i".equalsIgnoreCase(node.nodeName())  ||
                        "blockquote".equalsIgnoreCase(node.nodeName())))

                {
                    dc.append("</");
                    dc.append(node.nodeName());
                    dc.append(">");
                }

            }
        });

        return dc.toString();
    }

    public static String convertHTMLtoRTF(String finalText)

    {
        String documentContent = "";

        try {

            if (StringUtils.isNotEmpty(finalText)) {

                InputStream is = new ByteArrayInputStream(finalText.getBytes("UTF-8"));

                RTFEditorKit editorKit = new RTFEditorKit();

                HTMLEditorKit htmlKit = new HTMLEditorKit();

                javax.swing.text.Document document = htmlKit.createDefaultDocument();

                htmlKit.read(is, document, 0);

                OutputStream os = new ByteArrayOutputStream();

                document.getRootElements();

                editorKit.write(os, document, 0, document.getLength());

                documentContent = os.toString(); //document.getText (document.getStartPosition ().getOffset (), document.getEndPosition ().getOffset ());

                is.close();

                os.close();

                return documentContent;
            }

        } catch (Throwable t) {
            documentContent = "Unable to convert text to HTML. Please report this error.";
        }

        return documentContent;
    }



    public static String convertRTFtoHTML(String finalText)

    {
        String documentContent = "";

        try {

            if (StringUtils.isNotEmpty(finalText)) {

                InputStream is = new ByteArrayInputStream(finalText.getBytes("UTF-8"));


                RTFEditorKit editorKit = new RTFEditorKit();

                HTMLEditorKit htmlKit = new HTMLEditorKit();

                javax.swing.text.Document document = editorKit.createDefaultDocument();

                editorKit.read(is, document, 0);

                OutputStream os = new ByteArrayOutputStream();

                document.getRootElements();


                htmlKit.write(os, document, 0, document.getLength());

                documentContent = os.toString(); //document.getText (document.getStartPosition ().getOffset (), document.getEndPosition ().getOffset ());

                is.close();

                os.close();


                System.out.println("After default filter:\n" + documentContent);

                return documentContent.replaceAll("(?s)<head>.*head>", "")
                        .replaceAll("(<body>|<\\/body>)", "")
                        .replaceAll("(<html>|<\\/html>)", "");//.replaceAll("(<span\\b)", "<div").replaceAll("\\bspan>", "div>");

            }

        } catch (Throwable t) {
            documentContent = "Unable to convert text to HTML. Please report this error.";
        }

        return documentContent;
    }

}