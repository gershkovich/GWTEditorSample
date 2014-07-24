package pathology;

/**
 * Created by pg86 on 7/24/14.
 */
public class PrintInstruction
{
    private boolean underline;
    private boolean bold;
    private boolean italic;

    private String text;

    public boolean isUnderline()
    {

        return underline;
    }

    public void setUnderline(boolean underline)
    {

        this.underline = underline;
    }

    public boolean isBold()
    {

        return bold;
    }

    public void setBold(boolean bold)
    {

        this.bold = bold;
    }

    public boolean isItalic()
    {

        return italic;
    }

    public void setItalic(boolean italic)
    {

        this.italic = italic;
    }

    public String getText()
    {

        return text;
    }

    public void setText(String text)
    {

        this.text = text;
    }
}
