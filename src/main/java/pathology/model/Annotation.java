package pathology.model;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by petergershkovich on 2/7/15.
 */
public class Annotation
{
    private String geneSymbol;
    private String geneDescription;
    private String chrom;
    private long pos;
    private String cdna;
    private String proteinShortPos;
    private String proteinLongPos;
    private String variantTypeDna;
    private String variantTypeProtein;
    private String proteinDomainAffected;
    private String predictedEffect;
    private String functionOfNormalProtein;

    private String additionalNotes;

    public String getGeneSymbol()
    {
        return geneSymbol;
    }

    public void setGeneSymbol(String geneSymbol)
    {
        this.geneSymbol = geneSymbol;
    }

    public String getGeneDescription()
    {
        return geneDescription;
    }

    public void setGeneDescription(String geneDescription)
    {
        this.geneDescription = geneDescription;
    }

    public String getChrom()
    {
        return chrom;
    }

    public void setChrom(String chrom)
    {
        this.chrom = chrom;
    }

    public long getPos()
    {
        return pos;
    }

    public void setPos(long pos)
    {
        this.pos = pos;
    }

    public String getCdna()
    {
        return cdna;
    }

    public void setCdna(String cdna)
    {
        this.cdna = cdna;
    }

    public String getProteinShortPos()
    {
        return proteinShortPos;
    }

    public void setProteinShortPos(String proteinShortPos)
    {
        this.proteinShortPos = proteinShortPos;
    }

    public String getProteinLongPos()
    {
        return proteinLongPos;
    }

    public void setProteinLongPos(String proteinLongPos)
    {
        this.proteinLongPos = proteinLongPos;
    }

    public String getVariantTypeDna()
    {
        return variantTypeDna;
    }

    public void setVariantTypeDna(String variantTypeDna)
    {
        this.variantTypeDna = variantTypeDna;
    }

    public String getVariantTypeProtein()
    {
        return variantTypeProtein;
    }

    public void setVariantTypeProtein(String variantTypeProtein)
    {
        this.variantTypeProtein = variantTypeProtein;
    }

    public String getProteinDomainAffected()
    {
        return proteinDomainAffected;
    }

    public void setProteinDomainAffected(String proteinDomainAffected)
    {
        this.proteinDomainAffected = proteinDomainAffected;
    }

    public String getPredictedEffect()
    {
        return predictedEffect;
    }

    public void setPredictedEffect(String predictedEffect)
    {
        this.predictedEffect = predictedEffect;
    }

    public String getFunctionOfNormalProtein()
    {
        return functionOfNormalProtein;
    }

    public void setFunctionOfNormalProtein(String functionOfNormalProtein)
    {
        this.functionOfNormalProtein = functionOfNormalProtein;
    }

    public String getAdditionalNotes()
    {
        return additionalNotes;
    }

    public void setAdditionalNotes(String additionalNotes)
    {
        this.additionalNotes = additionalNotes;
    }

    public String toString()
    {
        if ( StringUtils.isNotEmpty(getVariantTypeProtein()))
        {
            String f = getVariantTypeProtein();
        }

        return getGeneSymbol() + "\t" + getGeneDescription() + "\t" + getChrom() + ":" + getPos() + "\t"
                + getCdna() + "\t" + getProteinShortPos() + "\t" + getProteinLongPos() + "\t"
                + "\tVT DNA: " + getVariantTypeDna()
                + "\t VT Prot: " + getVariantTypeProtein()
                + "\t  Func: " + getFunctionOfNormalProtein()
                + "\t  Eff: " + getPredictedEffect()
                + "\t" +  getAdditionalNotes();
    }
}
