package pan.artem.conspecterrepo.domain.parser;

import lombok.RequiredArgsConstructor;
import pan.artem.conspecterrepo.properties.AppProperties;

import java.io.IOException;
import java.io.Reader;
import java.util.Optional;

@RequiredArgsConstructor
public class ShortLineReader implements LineReader {

    private final Reader in;
    private final AppProperties.ParsingProperties parsingProperties;
    private int linesRead = 0;

    @Override
    public Optional<String> readLine() throws IOException {   // FIXME: quite not effective - too many method calls instead of a buffer
        StringBuilder sb = new StringBuilder();
        int went = 0;
        int r = in.read();
        if (r < 0) {
            return Optional.empty();
        }
        for (; went < parsingProperties.getMaxLineLength() && r >= 0; went++) {
            linesRead++;
            char c = (char) r;
            if (c == '\n') {
                break;
            }
            sb.append(c);
            r = in.read();
        }
        return Optional.of(sb.toString());
    }

    @Override
    public int linesRead() {
        return linesRead;
    }

    @Override
    public void close() throws IOException {
        in.close();
    }
}
