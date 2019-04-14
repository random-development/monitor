package net.random_development.monitor.service;

import net.random_development.monitor.dto.Resource;

import java.util.List;

public interface ResourceService {

    List<Resource> list();

    Resource get(String resourceName);

}
