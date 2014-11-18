package util;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * Created by pg86 on 10/24/14.
 */
public class TestSymbolMap
{
    boolean isDone;

    @Test
    public void testSymbolMapProcess()
    {

        String line;
        try
        {
            BufferedReader br = new BufferedReader(new FileReader("symbols.txt"));

            while ((line = br.readLine()) != null) {
                // process the line.
             //   if (line.contains("GREEK"))



                System.out.println(line);

//                if (line.contains("OMEGA"))
//                    break;

            }

            br.close();

        } catch ( IOException e )
        {
            e.printStackTrace();
        }

    }

    @Test
    public void testLambda()
    {

        StringBuilder symbols = new StringBuilder();

        BinaryOperator<Long> add = (x, y) -> x + y;

        long j = add.apply(5l, 4l);

        System.out.println(j);

        try (Stream<String> stream = Files.lines(Paths.get("symbols.txt"), Charset.defaultCharset()))
        {
            stream.forEach(e -> processLines(e, symbols));

        } catch (IOException ex) {
            // do something with exception
        }

        System.out.println(symbols.toString());
    }

    private void processLines(String line, StringBuilder symbols)
    {
        if (line.contains("GREEK") && !isDone)
        {
            Pattern p = Pattern.compile(".*GREEK CAPITAL\\s(.*?)\".*class=\"named\"><code>&amp;(.*?);.*");
            Matcher m = p.matcher(line);

            if (m.find())
            {
              //  System.out.println(m.group(1));
                if (symbols.length() > 0)
                {
                    symbols.append(",");
                }
                symbols.append("\"");
                symbols.append(m.group(2));
                symbols.append("\"");

            }
        }

        if (line.contains("OMEGA"))
            isDone = true;
    }

}
