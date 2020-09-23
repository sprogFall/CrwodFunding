package org.fall.service.api;

import crowd.entity.Menu;

import java.util.List;

public interface MenuService {

    List<Menu> getAll();

    void saveMenu(Menu menu);

    void editMenu(Menu menu);

    void removeMenuById(Integer id);
}
