package pan.artem.conspecterrepo.service;

import lombok.AllArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.RegexFileFilter;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pan.artem.conspecterrepo.entity.Repository;
import pan.artem.conspecterrepo.exception.RepoInitializationException;
import pan.artem.conspecterrepo.repository.RepoRepository;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Collection;

@AllArgsConstructor
@Service
public class RepoInitializerImpl implements RepoInitializer {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final RepoRepository repoRepository;
    private final ConspectInitializer conspectInitializer;

    private void cloneRepo(File path, String url) throws GitAPIException {
        path.mkdirs();

        try (Git result = Git.cloneRepository()
                .setURI(url)
                .setDirectory(path)
                .call()) {
            logger.info("Having repository: {}", result);
        }
    }

    private void pullRepo(File path, String url) throws IOException, GitAPIException {
        try (Git git = Git.open(path)) {
            var config = git.getRepository().getConfig();
            config.setString("branch", "master", "merge", "refs/heads/master");
            config.setString("branch", "master", "remote", "origin");
            config.setString("remote", "origin", "fetch", "+refs/heads/*:refs/remotes/origin/*");
            config.setString("remote", "origin", "url", url);
            config.save();

            var result = git.pull().call();
            logger.info("Git pull result: {}", result);
        }
    }

    @Transactional
    @Override
    public void initialize(String author, String repoName, String url) {
        Repository repository;
        var repos = repoRepository.findAllByAuthorAndName(author, repoName);
        File path = new File(author, repoName);

        if (repos.isEmpty()) {
            logger.info("Cloning repo {}:{} to {}...", author, repoName, path);
            try {
                cloneRepo(path, url);
            } catch (IOException | GitAPIException e) {
                throw new RepoInitializationException(
                        "Unable to clone repository with path: " + path + " and url: " + url, e
                );
            }
            repository = new Repository();
            repository.setAuthor(author);
            repository.setName(repoName);
            repository = repoRepository.save(repository);
        } else {
            try {
                pullRepo(path, url);
            } catch (IOException | GitAPIException e) {
                throw new RepoInitializationException(
                        "Unable to pull repository with path: " + path, e
                );
            }
            repository = repos.get(0);
        }

        Collection<File> files = FileUtils.listFiles(
                path,
                new RegexFileFilter(".*\\.tex"),
                DirectoryFileFilter.DIRECTORY
        );
        logger.info("Files found: {}", files);

        for (File file : files) {
            try {
                conspectInitializer.initialize(repository, file);
            } catch (IOException | ParseException e) {
                throw new RepoInitializationException(
                        "Unable to initialize conspect with path: " + path +
                                " for repository with url: " + url, e
                );
            }
        }
        // TODO: prune empty conspects
    }
}
