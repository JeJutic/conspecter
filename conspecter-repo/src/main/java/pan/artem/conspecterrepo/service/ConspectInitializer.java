package pan.artem.conspecterrepo.service;

import pan.artem.conspecterrepo.entity.Repository;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

public interface ConspectInitializer {

    void initialize(Repository repository, File path) throws IOException, ParseException;
}
