package pan.artem.conspecterrepo.domain;

import dto.inner.TaskDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pan.artem.conspecterrepo.properties.AppProperties;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.ParseException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ConspectAnalyzerImplTest {

    ConspectAnalyzerImpl conspectAnalyzer;

    @BeforeEach
    void init() {
        var props = new AppProperties.ParsingProperties();
        props.setMaxLineLength(1000);
        props.setMinTaskSize(100);
        conspectAnalyzer = new ConspectAnalyzerImpl(props);
    }

    @Test
    void analyze() throws IOException, ParseException {
        File file = File.createTempFile("temp", "");
        Files.writeString(file.toPath(), """
\\documentclass{article}
\\usepackage{graphicx}

\\begin{document}
    \\begin{block}
        \\begin{exercise*}
            so many text so many text and f0rmUl@s 2 here it is 214321 sdf+ kasld;l
        \\end{exercise*}

        \\begin{solution*}
            2 here it is 214321 sdf+ kasld;l so many text so many text and f0rmUl@S
        \\end{solution*}
    \\end{block}

    \\begin{exercise}
        no
    \\end{exercise}

    \\begin{solution}
        2 here it is 214321 sdf+ kasld;l so many text so many text and f0rmUl@S
        2 here it is 214321 sdf+ kasld;l so many text so many text and f0rmUl@S
    \\end{solution}
\\end{document}
                                """);

        List<TaskDto> tasks = conspectAnalyzer.analyze(file);

        assertEquals(5 * 3, tasks.size());
        tasks.forEach(t -> {
            assertFalse(t.text().isEmpty());
            assertFalse(t.answer().isEmpty());
        });

        file.delete();
    }
}