package pan.artem.conspecterrepo.domain;

import dto.inner.TaskDto;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pan.artem.conspecterrepo.domain.parser.LatexReader;
import pan.artem.conspecterrepo.properties.AppProperties;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.*;

@AllArgsConstructor
public class ConspectAnalyzerImpl implements ConspectAnalyzer {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final AppProperties.ParsingProperties parsingProperties;

    private int countCommandEntrances(String line, String command) {
        int ans = 0;

        for (int i = 0; i <= line.length() - command.length(); i++) {
            int j = 0;
            for (; j < command.length(); j++) {
                if (line.charAt(i + j) != command.charAt(j)) {
                    break;
                }
            }
            i += j;
            if (j == command.length()) {
                ans++;
            }
        }

        return ans;
    }

    private String getLastN(Deque<String> lines, int n) {
        List<String> removed = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            removed.add(lines.removeLast());
        }

        StringBuilder res = new StringBuilder();
        Collections.reverse(removed);
        for (var line : removed) {
            lines.addLast(line);
            res.append(line).append('\n');
        }
        return res.toString();
    }

    private int removeFirstN(Deque<String> lines, int n) {
        int remCnt = 0;
        for (int i = 0; i < n; i++) {
            String removed = lines.removeFirst();
            remCnt += removed.length();
        }
        return remCnt;
    }

    @Override
    public List<TaskDto> analyze(File path) throws IOException, ParseException {
        LatexReader latexReader = new LatexReader(new BufferedReader(
                new FileReader(path, StandardCharsets.UTF_8)
        ), parsingProperties);
        StringBuilder headers = new StringBuilder();
        String line;
        while (true) {
            var optionalLine = latexReader.readLine();
            if (optionalLine.isEmpty()) {
                throw new ParseException("No \\begin{document} found", headers.length());
            }
            line = optionalLine.get();
            headers.append(line).append('\n');
            if (countCommandEntrances(line, "\\begin{document}") == 1) {
                break;
            }
            if (headers.length() > parsingProperties.getMaxLineLength()) {
                throw new ParseException("Too many headers " + headers, headers.length());
            }
        }

        List<TaskDto> tasks = new ArrayList<>();
//        TaskSaver taskSaver = new TaskSaver(headers.toString());
        Deque<String> lines = new LinkedList<>();
        lines.add(line);
        Deque<Integer> nested = new LinkedList<>();
        int cur = 0;
        int charSum = 0;
        nested.add(cur);
        while (true) {
            cur++;
            var optional = latexReader.readLine();
            if (optional.isEmpty()) {
                break;
            }
            line = optional.get();
            lines.addLast(line);
            charSum += line.length();

            int beginCount = countCommandEntrances(line, "\\begin");
            int endCount = countCommandEntrances(line, "\\end");

            for (int i = 0; i < beginCount - endCount; i++) {
                nested.addLast(cur);
            }

            if (nested.isEmpty()) {
                lines.removeLast();
                charSum = 0;
            } else {
                for (int i = 0; i < endCount - beginCount; i++) {
                    if (nested.isEmpty()) {
                        throw new ParseException("More \\end than \\begin", latexReader.linesRead());
                    }
                    int st = nested.removeLast();
                    if (charSum > parsingProperties.getMinTaskSize()) {
                        String taskText = getLastN(lines, cur - st + 1);
                        tasks.addAll(
                                new TaskMakerImpl().makeTasks(taskText, 3)
                        );
                    }
                }

                while (charSum > parsingProperties.getMaxLineLength()) {
                    int first = nested.removeFirst();
                    if (nested.isEmpty()) {
                        lines.clear();
                        charSum = 0;
                        break;
                    }
                    charSum -= removeFirstN(lines, nested.getFirst() - first);
                }
            }
            logger.debug("Nested tags parsed by the end of conspect parsing: {}", nested);
        }
        latexReader.close();
        if (!nested.isEmpty()) {
            throw new ParseException("More \\begin than \\end", latexReader.linesRead());
        }
        return tasks;
    }
}
