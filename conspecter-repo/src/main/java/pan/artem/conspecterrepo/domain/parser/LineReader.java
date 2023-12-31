package pan.artem.conspecterrepo.domain.parser;

import java.io.Closeable;
import java.io.IOException;
import java.util.Optional;

public interface LineReader extends Closeable {

    Optional<String> readLine() throws IOException;

    int linesRead();
}
