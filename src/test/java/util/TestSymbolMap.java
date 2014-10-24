package util;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Created by pg86 on 10/24/14.
 */
public class TestSymbolMap
{
    @Test
    public void testSymbolMapProcess()
    {

        String line;
        try
        {
            BufferedReader br = new BufferedReader(new FileReader("symbols.txt"));

            while ((line = br.readLine()) != null) {
                // process the line.
                if (line.contains("GREEK"))



                System.out.println(line);

                if (line.contains("OMEGA"))
                    break;

            }

            br.close();

        } catch ( IOException e )
        {
            e.printStackTrace();
        }

    }

}
