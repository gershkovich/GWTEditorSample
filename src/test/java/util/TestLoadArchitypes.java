package util;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;
import org.jsoup.select.NodeVisitor;
import org.junit.Test;
import pathology.model.Annotation;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by petergershkovich on 2/7/15.
 */
public class TestLoadArchitypes
{
    @Test
    public void testSymbolMapProcess()
    {

        String line;
        try
        {

            File input = new File("/Users/petergershkovich/Downloads/LibraryVariant.htm");

            Document doc = Jsoup.parse(input, "UTF-8", "");

            String[] aTypes = new String[1];

            for (Node node : doc.childNodes())
            {

                aTypes = filterHTML(node.toString()).split("\\___________+");


            }

            List<Annotation> annotations = new ArrayList<>();


            String geneName = "Gene name:</i>";
            String chromPos = "<i>Chromosomal position:</i>";
            String apPloid = "<i>Approximate ploidy:</i>";
            String cdna1 = " <i>Variant (DNA):</i>";
            String cdna2 = "<i>Variant (cDNA):</i>";
            String cdna3 = "<i>Variant (coding DNA):</i>";
            String prot = "<i>Variant (protein):</i>";
            String protFunction = "<i>Function of protein:</i>";



            String addNotes = "<i>Additional notes:</i>";









            for (String type : aTypes)
            {
                Annotation annotation = new Annotation();

                String filtered = type.replaceAll("^</i>", "").replaceAll("</p><p>", "")
                        .replaceAll("<i>(\\s*|\\W*)</i>", "")
                        .replaceAll("<b>(\\s*|\\W*)</b>", "");


                //  System.out.println(filtered);

                //find position of Gene Name heading

                int gi = filtered.indexOf(geneName);

                int cp = filtered.indexOf(chromPos);

                int ap = filtered.indexOf(apPloid);

                int cdnaIdx = filtered.indexOf(cdna1);

                if (cdnaIdx < 1)
                {
                    cdnaIdx = filtered.indexOf(cdna2);
                }
                if (cdnaIdx < 1)
                {
                    cdnaIdx = filtered.indexOf(cdna3);
                }

                int addNotesIdx = filtered.indexOf(addNotes);


                if (gi < 1 )
                {
                    System.out.println("NOT FOUND \t" + filtered);


                } else if (cp > 1 )
                {
                    setSymbolAndDescription(geneName, annotation, filtered, gi, cp);


                }else if ( ap > 1)
                {
                    setSymbolAndDescription(geneName, annotation, filtered, gi, ap);


                } else if ( cdnaIdx > 1)
                {
                    setSymbolAndDescription(geneName, annotation, filtered, gi, cdnaIdx);

                }  else
                {

                    System.out.println("NOT FOUND \t" + filtered);

                }

                if (addNotesIdx > 1)
                {
                    annotation.setAdditionalNotes(filtered.substring(addNotesIdx + addNotes.length()).replaceAll("(\\s+|\\xA0+)", " "));

                }


//                fillAnnotationObject(filtered, annotation);
//
                if (StringUtils.isNotEmpty(annotation.getGeneSymbol()))
                {


                    annotations.add(annotation);
                }

            }


            for (Annotation an : annotations)
            {

                System.out.println(an.toString());
            }


        } catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    private void setSymbolAndDescription(String geneName, Annotation annotation, String filtered, int idx1, int idx2)
    {
        String [] nameAndDesc = filtered.substring(idx1 + geneName.length(), idx2).replaceAll("<(\\w|/\\w)>", "").split("\\(|\\)");

        if (nameAndDesc.length > 1)
        {
            annotation.setGeneSymbol(nameAndDesc[0].replaceAll("(\\s+|\\xA0+)", ""));
            annotation.setGeneDescription(nameAndDesc[1].trim().replaceAll("(\\s+|\\xA0+)", " "));
        } else
        {
            annotation.setGeneSymbol(nameAndDesc[0].replaceAll("(\\s+|\\xA0+)", ""));
        }
    }


    private String filterHTML(String htmlString)
    {
        StringBuilder dc = new StringBuilder();

        Document doc = Jsoup.parseBodyFragment(htmlString);

        doc.traverse(new NodeVisitor()
        {
            boolean foundText = false;

            public void head(Node node, int depth)
            {

                //we need to find text and ignore empty lines


                if ("#text".equalsIgnoreCase(node.nodeName()) && StringUtils.isNotEmpty(node.toString().trim()))
                {
                    foundText = true;

                    dc.append(Jsoup.parse(node.toString().trim()).text().trim());

                } else if (foundText && ("p".equalsIgnoreCase(node.nodeName()) ||
                        "b".equalsIgnoreCase(node.nodeName()) ||
                        "i".equalsIgnoreCase(node.nodeName())) ||
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

            public void tail(Node node, int depth)
            {

                if (foundText && ("p".equalsIgnoreCase(node.nodeName()) ||
                        "b".equalsIgnoreCase(node.nodeName()) ||
                        "i".equalsIgnoreCase(node.nodeName()) ||
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


    private void fillAnnotationObject(String htmlString, Annotation annotation)
    {
        StringBuilder dc = new StringBuilder();

        // StringBuilder geneSymbol = new StringBuilder();

        Document doc = Jsoup.parseBodyFragment(htmlString);

        doc.traverse(new NodeVisitor()
        {
            boolean foundText = false;
            boolean foundGeneName = false;

            public void head(Node node, int depth)
            {

                if ("#text".equalsIgnoreCase(node.nodeName()) && StringUtils.isNotEmpty(node.toString().trim()))
                {
                    foundText = true;


                    String text = Jsoup.parse(node.toString().trim()).text(); //pure text

                    if ("Gene name:".matches(text))
                    {
                        //found gene name
                        foundGeneName = true;
                    }

                    if (foundGeneName)
                    {
                        //add text to gene name buffer
                        annotation.setGeneSymbol(text);

                        foundGeneName = false;
                    }

                    dc.append(text.trim());

                } else if (foundText && ("p".equalsIgnoreCase(node.nodeName()) ||
                        "b".equalsIgnoreCase(node.nodeName()) ||
                        "i".equalsIgnoreCase(node.nodeName())) ||
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

            public void tail(Node node, int depth)
            {

                if (foundText && ("p".equalsIgnoreCase(node.nodeName()) ||
                        "b".equalsIgnoreCase(node.nodeName()) ||
                        "i".equalsIgnoreCase(node.nodeName()) ||
                        "blockquote".equalsIgnoreCase(node.nodeName())))

                {
                    dc.append("</");
                    dc.append(node.nodeName());
                    dc.append(">");
                }


            }
        });

    }


}
