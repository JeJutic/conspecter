package pan.artem.conspecterrepo.service;

import dto.outer.RepositoryDto;

import java.util.List;

public interface RepositoryService {

    List<RepositoryDto> findAll();
}
