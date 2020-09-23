package org.fall.mvc.handler;

import com.sun.media.jfxmediaimpl.MediaUtils;
import crowd.entity.Menu;
import org.fall.service.api.MenuService;
import org.fall.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MenuHandler {

    @Autowired
    MenuService menuService;

    @ResponseBody
    @RequestMapping("/menu/do/get.json")
    public ResultEntity<Menu> getWholeTree(){
        // 通过service层方法得到全部Menu对象的List
        List<Menu> menuList = menuService.getAll();

        // 声明一个Menu对象root，用来存放找到的根节点
        Menu root = null;

        // 使用map表示每一个菜单与id的对应关系
        Map<Integer,Menu> menuMap = new HashMap<>();

        // 将菜单id与菜单对象以K-V对模式存入map
        for(Menu menu: menuList){
            menuMap.put(menu.getId(),menu);
        }

        for (Menu menu : menuList){
            Integer pid = menu.getPid();

            if (pid == null){
                // pid为null表示该menu是根节点
                root = menu;
                continue;
            }
            // 通过当前遍历的menu的pid得到其父节点
            Menu father = menuMap.get(pid);
            // 为父节点添加子节点为当前节点
            father.getChildren().add(menu);
        }

        return ResultEntity.successWithData(root);
    }

    @ResponseBody
    @RequestMapping("/menu/save.json")
    public ResultEntity<String> saveMenu(Menu menu){

        menuService.saveMenu(menu);

        return ResultEntity.successWithoutData();
    }

    @ResponseBody
    @RequestMapping("/menu/edit.json")
    public ResultEntity<String> editMenu(Menu menu){
        menuService.editMenu(menu);
        return ResultEntity.successWithoutData();
    }


    @ResponseBody
    @RequestMapping("/menu/remove.json")
    public ResultEntity<String> removeMenu(Integer id){
        menuService.removeMenuById(id);
        return ResultEntity.successWithoutData();
    }

}
