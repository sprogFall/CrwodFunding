package org.fall.service.impl;

import crowd.entity.Menu;
import crowd.entity.MenuExample;
import org.fall.mapper.MenuMapper;
import org.fall.service.api.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    MenuMapper menuMapper;

    @Override
    public List<Menu> getAll() {
        return menuMapper.selectByExample(new MenuExample());
    }

    @Override
    public void saveMenu(Menu menu) {
        menuMapper.insert(menu);
    }

    @Override
    public void editMenu(Menu menu) {
        // 有选择地更新，如果menu中有的值为null（如这里的pid），则不会更新该内容
        menuMapper.updateByPrimaryKeySelective(menu);
    }

    @Override
    public void removeMenuById(Integer id) {
        menuMapper.deleteByPrimaryKey(id);
    }
}
