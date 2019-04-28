package net.random_development.monitor.service;

import net.random_development.monitor.dto.Resource;

import java.util.List;
import java.util.Optional;

public interface ResourceService {

    List<Resource> list();

    Optional<Resource> get(String resourceName);

}
