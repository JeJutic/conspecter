package pan.artem.conspecterrepo.service;

import dto.ConspectDto;

import java.util.List;

public interface ConspectService {


    List<ConspectDto> findAllByRepoName(String username, String repoName);
}
