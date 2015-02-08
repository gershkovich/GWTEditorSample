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

    String geneName = "Gene name:</i>";

    String chromPos = "<i>Chromosomal position:</i>";

    String apPloid = "<i>Approximate ploidy:</i>";

    String cdna = "";  //assign later to get accurate length

    String cdna1 = "<i>Variant (DNA):</i>";

    String cdna2 = "<i>Variant (cDNA):</i>";

    String cdna3 = "<i>Variant (coding DNA):</i>";

    String prot = "<i>Variant (protein):</i>";

    String fTum = "<i>Frequency in tumor:</i>";

    String fNorm = "<i>Frequency in normal:</i>";

    String vtDNA = "<i>Variant type (DNA):</i>";

    String vtProt = "<i>Variant type (protein):</i>";

    String protDomain = "<i>Protein domain affected:</i>";

    String protFunction = "";

    String protFunction1 = "<i>Function of protein:</i>";

    String protFunction2 = "<i>Function of normal protein:</i>";

    String protFunction3 = "<i>Function of normal protein</i>";


    String protPredEffect = "";

    String protPredEffect1 = "<i>Predicted effects of variant</i><i> on protein structure/function:</i>";

    String protPredEffect2 = "<i>Predicted effects of variant</i><i>on protein structure/function:</i>";

    String protPredEffect3 = "<i>Predicted effects on</i><i>protein structure/function:</i>";

    String protPredEffect4 = "<i>Predicted effects of variant</i><i>onprotein structure/function:</i>";
    String protPredEffect5 = "<i>Predicted effects on</i><i>protein</i><i>structure/function:</i>";

    String protPredEffect6 = "Predicted effects on<i>protein</i><i>structure/function:</i>";
                             //"<i>Predicted effects on</i><i>protein</i><i>structure/function:</i>"




    //<i>Predicted effects of variant</i><i>on protein structure/function:</i>

    String previouslyReported = "";
    String previouslyReported1 = "<i>Variant previously reported?</i>";
    String previouslyReported2 = "<i>Variant previously reported:</i>";
    String previouslyReported3 = "<i>Previously reported:</i>";
    String previouslyReported4 = "<i>Previously reported?</i>";




    String addNotes = "<i>Additional notes:</i>";

    @Test
    public void testSymbolMapProcess()
    {

        String line;
        try
        {
            File input = new File("/Users/petergershkovich/Downloads/LibraryVariant.htm");

            Document doc = Jsoup.parse(input, "x-MacRoman", "");

            String[] aTypes = new String[1];

            for ( Node node : doc.childNodes() )
            {

                aTypes = filterHTML(node.toString()).split("\\___________+");


            }

            List<Annotation> annotations = new ArrayList<>();

              /*

Chromosomal position: 		chr11:32414250
Variant (cDNA):			c.1097G>A
Variant (protein):		R366H	(Arg366His)
Frequency in tumor:		      46%
Frequency in normal:		        0%
Variant type (DNA):		Single nucleotide substitution
Variant type (protein):		Missense
Protein domain affected:	Second zinc finger domain
Function of normal protein:	DNA-binding protein required for development of urogenital system
Predicted effects of variant
 on protein structure/function:	Deleterious (SIFT)
 				Probably Damaging (Polyphen)
Variant previously reported?	Yes (rare); COSMIC database ID = COSM428907. The R366H variant has been previously reported in the COSMIC database in a breast carcinoma and a carcinoma of the large intestine.
Additional notes:

             */


            for ( String type : aTypes )
            {
                Annotation annotation = new Annotation();

                String filtered = type.replaceAll("^</i>", "").replaceAll("</p><p>", "")
                        .replaceAll("<i>(\\s*|\\W*)</i>", "")
                        .replaceAll("<b>(\\s*|\\W*)</b>", "");


                //  System.out.println(filtered);

                //find position of Gene Name heading

                int geneNameIdx = filtered.indexOf(geneName);

                int chromPosIdx = filtered.indexOf(chromPos);

                int apPloidIdx = filtered.indexOf(apPloid);

                int cdnaIdx = filtered.indexOf(cdna1);
                cdna = cdna1;

                if ( cdnaIdx < 1 )
                {
                    cdnaIdx = filtered.indexOf(cdna2);
                    cdna = cdna2;
                }
                if ( cdnaIdx < 1 )
                {
                    cdnaIdx = filtered.indexOf(cdna3);
                    cdna = cdna3;
                }

                int addNotesIdx = filtered.indexOf(addNotes);

                int chrPosIdx = filtered.indexOf(chromPos);

                int protIdx = filtered.indexOf(prot);

                int fTumIdx = filtered.indexOf(fTum);

                int fNormIdx = filtered.indexOf(fNorm);

                int vtDNAIdx = filtered.indexOf(vtDNA);

                int vtProtIdx = filtered.indexOf(vtProt);

                int protDomainIdx = filtered.indexOf(protDomain);

                int protFunctionIdx = filtered.indexOf(protFunction1);

                protFunction = protFunction1;

                if ( protFunctionIdx < 1 )
                {
                    protFunctionIdx = filtered.indexOf(protFunction2);
                    protFunction = protFunction2;

                }

                if ( protFunctionIdx < 1 )
                {
                    protFunctionIdx = filtered.indexOf(protFunction3);
                    protFunction = protFunction3;

                }



                int protPredEffectIdx = filtered.indexOf(protPredEffect1);

                protPredEffect = protPredEffect1;

                if ( protPredEffectIdx < 1 )
                {
                    protPredEffectIdx = filtered.indexOf(protPredEffect2);
                    protPredEffect = protPredEffect2;

                }

                if ( protPredEffectIdx < 1 )
                {
                    protPredEffectIdx = filtered.indexOf(protPredEffect3);
                    protPredEffect = protPredEffect3;

                }

                if ( protPredEffectIdx < 1 )
                {
                    protPredEffectIdx = filtered.indexOf(protPredEffect4);
                    protPredEffect = protPredEffect4;

                }
                if ( protPredEffectIdx < 1 )
                {
                    protPredEffectIdx = filtered.indexOf(protPredEffect5);
                    protPredEffect = protPredEffect5;

                }

                if ( protPredEffectIdx < 1 )
                {
                    protPredEffectIdx = filtered.indexOf(protPredEffect6);
                    protPredEffect = protPredEffect6;

                }

                int previouslyReportedIdx = filtered.indexOf(previouslyReported1);

                previouslyReported = previouslyReported1;

                if (previouslyReportedIdx < 1)
                {

                    previouslyReportedIdx = filtered.indexOf(previouslyReported2);
                    previouslyReported = previouslyReported2;
                }
                if (previouslyReportedIdx < 1)
                {

                    previouslyReportedIdx = filtered.indexOf(previouslyReported3);
                    previouslyReported = previouslyReported3;
                }
                if (previouslyReportedIdx < 1)
                {

                    previouslyReportedIdx = filtered.indexOf(previouslyReported4);
                    previouslyReported = previouslyReported4;
                }


                if ( geneNameIdx < 1 )
                {
                    System.out.println("NOT FOUND \t" + filtered);

                }
                else if ( chromPosIdx > 1 )
                {
                    setSymbolAndDescription(geneName, annotation, filtered, geneNameIdx, chromPosIdx);

                }
                else if ( apPloidIdx > 1 )
                {
                    setSymbolAndDescription(geneName, annotation, filtered, geneNameIdx, apPloidIdx);

                }
                else if ( cdnaIdx > 1 )
                {
                    setSymbolAndDescription(geneName, annotation, filtered, geneNameIdx, cdnaIdx);

                }
                else
                {

                    System.out.println("NOT FOUND \t" + filtered);

                }

                if ( addNotesIdx > 1 )
                {
                    annotation.setAdditionalNotes(filtered.substring(addNotesIdx + addNotes.length()).replaceAll("(\\s+|\\xA0+)", " "));

                }

                if ( chromPosIdx > 0 )
                {
                    if ( cdnaIdx > 0 )
                    {
                        setChromPos(annotation, filtered, chromPosIdx, cdnaIdx);
                    } else
                    {
                        System.out.println("CHR. POS \t" + filtered);
                    }

                }

                if ( cdnaIdx > 0 )
                {
                    if ( protIdx > 0 )
                    {
                        setCdnaPos(annotation, filtered, cdnaIdx, protIdx);
                    } else
                    {
                        System.out.println("CDNA \t" + filtered);
                    }

                }


                if ( protIdx > 0 )
                {
                    if (fTumIdx > 0)
                    {
                        setProtPos(annotation, filtered, protIdx, fTumIdx);
                    } else if (fNormIdx > 0)
                    {
                        setProtPos(annotation, filtered, protIdx, fNormIdx);

                    } else if (vtDNAIdx > 0 && protIdx < vtDNAIdx )
                    {
                        setProtPos(annotation, filtered, protIdx, vtDNAIdx);
                    } else
                    {
                        System.out.println("PROT \t" + filtered);
                    }
                }

                if ( vtDNAIdx > 0 )
                {
                    if ( vtProtIdx > 0 )
                    {
                        setVarTypeDNA(annotation, filtered, vtDNAIdx, vtProtIdx);
                    } else
                    {
                        System.out.println("TYPE DNA \t" + filtered);
                    }

                }

                if ( vtProtIdx > 0 )
                {
                    if ( protDomainIdx > 0 )
                    {
                        setVarTypeProt(annotation, filtered, vtProtIdx, protDomainIdx);

                    }  else  if ( protFunctionIdx > 0 )
                    {
                        setVarTypeProt(annotation, filtered, vtProtIdx, protFunctionIdx);
                    }
                    else
                    {
                        System.out.println("TYPE PROT \t" + filtered);
                    }

                }

                if ( protDomainIdx > 0 )
                {
                    if ( protFunctionIdx > 0 && protDomainIdx < protFunctionIdx )
                    {
                        setProtDomain(annotation, filtered, protDomainIdx, protFunctionIdx);
                    }  else  if ( protPredEffectIdx > 0 && protDomainIdx < protPredEffectIdx )
                    {
                        setProtDomain(annotation, filtered, protDomainIdx, protPredEffectIdx);
                    }
                    else
                    {
                        System.out.println("PROT DOMAIN \t" + filtered);
                    }

                }


                if ( protFunctionIdx > 0 )
                {
                    if ( protPredEffectIdx > 0 && protFunctionIdx < protPredEffectIdx )
                    {
                        setProtFunction(annotation, filtered, protFunctionIdx, protPredEffectIdx);
                    }
                    else if ( previouslyReportedIdx > 0 && protFunctionIdx < previouslyReportedIdx )
                    {
                        setProtFunction(annotation, filtered, protFunctionIdx, previouslyReportedIdx);
                    }
                    else if ( addNotesIdx > 0 && protFunctionIdx < addNotesIdx )
                    {
                        setProtFunction(annotation, filtered, protFunctionIdx, addNotesIdx);
                    }
                    else
                    {
                        System.out.println("PROT. FUNCTION  \t" + filtered);
                    }

                }


                if ( protPredEffectIdx > 0 )
                {
                    if ( previouslyReportedIdx > 0 && protPredEffectIdx < previouslyReportedIdx )
                    {
                        setPredictedProtEffect(annotation, filtered, protPredEffectIdx, previouslyReportedIdx);
                    }
                    else
                    {
                        System.out.println("PREDICTED EFFECT  \t" + filtered);
                    }

                }

                if ( previouslyReportedIdx > 0 )
                {
                    if ( addNotesIdx > 0 && previouslyReportedIdx < addNotesIdx )
                    {
                        setPreviouslyReported(annotation, filtered, previouslyReportedIdx, addNotesIdx);
                    } else if ( addNotesIdx < 0)
                    {
                        setPreviouslyReported(annotation, filtered, previouslyReportedIdx, filtered.length());
                    }
                    else
                    {
                        System.out.println("PREVIOUSLY REPORTED  \t" + filtered);
                    }

                }



                     /*

Chromosomal position: 		chr11:32414250
Variant (cDNA):			c.1097G>A
Variant (protein):		R366H	(Arg366His)
Frequency in tumor:		      46%
Frequency in normal:		        0%
Variant type (DNA):		Single nucleotide substitution
Variant type (protein):		Missense
Protein domain affected:	Second zinc finger domain
Function of normal protein:	DNA-binding protein required for development of urogenital system
Predicted effects of variant
 on protein structure/function:	Deleterious (SIFT)
 				Probably Damaging (Polyphen)
Variant previously reported?	Yes (rare); COSMIC database ID = COSM428907. The R366H variant has been previously reported in the COSMIC database in a breast carcinoma and a carcinoma of the large intestine.
Additional notes:

             */


                //                fillAnnotationObject(filtered, annotation);
                //
                if ( StringUtils.isNotEmpty(annotation.getGeneSymbol()) )
                {

                    annotations.add(annotation);
                }


            }


            for ( Annotation an : annotations )
            {

                System.out.println(an.toString());
            }


        } catch ( IOException e)
        {
            e.printStackTrace();
        }

    }

    private void setPreviouslyReported(Annotation annotation, String filtered, int idx1, int idx2)
    {
        annotation.setPreviouslyReported(filtered.substring(idx1 + previouslyReported.length(), idx2)
                .replaceAll("(\\s+|\\xA0+)", " "));

    }

    private void setPredictedProtEffect(Annotation annotation, String filtered, int idx1, int idx2)
    {
        annotation.setPredictedEffect(filtered.substring(idx1 + protPredEffect.length(), idx2)
                .replaceAll("(\\s+|\\xA0+)", " "));

    }

    private void setProtFunction(Annotation annotation, String filtered, int idx1, int idx2)
    {

        annotation.setFunctionOfNormalProtein(filtered.substring(idx1 + protFunction.length(), idx2)
                .replaceAll("(\\s+|\\xA0+)", " "));
    }

    private void setProtDomain(Annotation annotation, String filtered, int idx1, int idx2)
    {

        annotation.setProteinDomainAffected(filtered.substring(idx1 + protDomain.length(), idx2)
                .replaceAll("(\\s+|\\xA0+)", " "));
    }

    private void setVarTypeProt(Annotation annotation, String filtered, int idx1, int idx2)
    {

        annotation.setVariantTypeProtein(filtered.substring(idx1 + vtProt.length(), idx2).replaceAll("<(\\w|/\\w)>", "")
                .replaceAll("(\\s+|\\xA0+)", " "));

    }

    private void setVarTypeDNA(Annotation annotation, String filtered, int idx1, int idx2)
    {

        if ( (idx1 + vtDNA.length() <= idx2) )
        {
            annotation.setVariantTypeDna(filtered.substring(idx1 + vtDNA.length(), idx2).replaceAll("<(\\w|/\\w)>", "").replaceAll("(\\s+|\\xA0+)", " "));
        }


    }

    private void setProtPos(Annotation annotation, String filtered, int idx1, int idx2)
    {

        String[] nameAndDesc = filtered.substring(idx1 + prot.length(), idx2).replaceAll("<(\\w|/\\w)>", "").split("\\(|\\)");

        if ( nameAndDesc.length > 1 )
        {
            annotation.setProteinShortPos(nameAndDesc[0].replaceAll("(\\s+|\\xA0+)", ""));
            annotation.setProteinLongPos(nameAndDesc[1].trim().replaceAll("(\\s+|\\xA0+)", " "));
        }
        else
        {
            annotation.setProteinShortPos(nameAndDesc[0].replaceAll("(\\s+|\\xA0+)", ""));
        }

    }

    private void setCdnaPos(Annotation annotation, String filtered, int cdnaIdx, int protIdx)
    {

        annotation.setCdna(filtered.substring(cdnaIdx + cdna.length(), protIdx).replaceAll("<(\\w|/\\w)>", "").replaceAll("(\\s+|\\xA0+)", ""));

    }

    private void setChromPos(Annotation annotation, String filtered, int chromPosIdx, int cdnaIdx)
    {

        String[] nameAndDesc = filtered.substring(chromPosIdx + chromPos.length(), cdnaIdx).replaceAll("<(\\w|/\\w)>", "").split(":");

        if ( nameAndDesc.length > 1 )
        {
            annotation.setChrom(nameAndDesc[0].replaceAll("(\\s+|\\xA0+)", ""));

            try
            {
                long pos = Long.valueOf(nameAndDesc[1].trim().replaceAll("(\\s+|\\xA0+)", ""));

                annotation.setPos(pos);

            } catch ( NumberFormatException e )
            {
                String[] range = nameAndDesc[1].trim().replaceAll("(\\s+|\\xA0+)", "").split("-");

                if ( range.length > 1 )
                {
                    try
                    {
                        long pos = Long.valueOf(range[1].trim().replaceAll("(\\s+|\\xA0+)", ""));

                    } catch ( NumberFormatException ex )
                    {
                        //do nothing
                    }
                }
            }

        }

    }

    private void setSymbolAndDescription(String geneName, Annotation annotation, String filtered, int idx1, int idx2)
    {

        String[] nameAndDesc = filtered.substring(idx1 + geneName.length(), idx2).replaceAll("<(\\w|/\\w)>", "").split("\\(|\\)");

        if ( nameAndDesc.length > 1 )
        {
            annotation.setGeneSymbol(nameAndDesc[0].replaceAll("(\\s+|\\u00A0+)", ""));
            annotation.setGeneDescription(nameAndDesc[1].trim().replaceAll("(\\s+|\\u00A0+)", " "));
        }
        else
        {
            annotation.setGeneSymbol(nameAndDesc[0].replaceAll("(\\s+|\\u00A0+)", ""));
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


                if ( "#text".equalsIgnoreCase(node.nodeName())
                        && StringUtils.isNotEmpty(node.toString().replaceAll("&nbsp;+", " ").trim()) )
                {
                    foundText = true;

                    if ( node.toString().matches(".*\\u00A0*.*") )
                    {
                        String x = node.toString();
                    }

                    dc.append(Jsoup.parse(node.toString().replaceAll("&nbsp;+", "\\s").trim()).text().trim());

                }
                else if ( foundText && (
                        "b".equalsIgnoreCase(node.nodeName()) ||
                                "i".equalsIgnoreCase(node.nodeName())) )
                {
                    dc.append("<");
                    dc.append(node.nodeName());
                    dc.append(">");
                }
            }

            public void tail(Node node, int depth)
            {

                if ( foundText && (
                        "b".equalsIgnoreCase(node.nodeName()) ||
                                "i".equalsIgnoreCase(node.nodeName())
                ) )

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

                if ( "#text".equalsIgnoreCase(node.nodeName()) && StringUtils.isNotEmpty(node.toString().trim()) )
                {
                    foundText = true;


                    String text = Jsoup.parse(node.toString().trim()).text(); //pure text

                    if ( "Gene name:".matches(text) )
                    {
                        //found gene name
                        foundGeneName = true;
                    }

                    if ( foundGeneName )
                    {
                        //add text to gene name buffer
                        annotation.setGeneSymbol(text);

                        foundGeneName = false;
                    }

                    dc.append(text.trim());

                }
                else if ( foundText && ("p".equalsIgnoreCase(node.nodeName()) ||
                        "b".equalsIgnoreCase(node.nodeName()) ||
                        "i".equalsIgnoreCase(node.nodeName())) ||
                        "blockquote".equalsIgnoreCase(node.nodeName()) )
                {
                    dc.append("<");
                    dc.append(node.nodeName());
                    dc.append(">");
                }
                else if ( foundText && ("img".equalsIgnoreCase(node.nodeName())) )
                {

                    dc.append(node.toString());
                }
            }

            public void tail(Node node, int depth)
            {

                if ( foundText && ("p".equalsIgnoreCase(node.nodeName()) ||
                        "b".equalsIgnoreCase(node.nodeName()) ||
                        "i".equalsIgnoreCase(node.nodeName()) ||
                        "blockquote".equalsIgnoreCase(node.nodeName())) )

                {
                    dc.append("</");
                    dc.append(node.nodeName());
                    dc.append(">");
                }


            }
        });

    }


}
