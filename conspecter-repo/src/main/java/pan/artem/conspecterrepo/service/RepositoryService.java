package pan.artem.conspecterrepo.service;

import dto.RepositoryDto;

import java.util.List;

public interface RepositoryService {

    List<RepositoryDto> findAll();
}
