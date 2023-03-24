package ch.opendata.hack.energy.sources.csv;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public final class CsvReader {

    private CsvReader() {
    }

    public static List<String> read(final String source) throws IOException {

        final URL url = new URL(source);
        final URLConnection connection = url.openConnection();
        final List<String> lines = new ArrayList<>();

        String line = "";

        try(InputStreamReader input = new InputStreamReader(connection.getInputStream());
                BufferedReader buffer = new BufferedReader(input)) {

            while ((line = buffer.readLine()) != null) {
                lines.add(line);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
        return lines;
    }
}
