package com.org.permission;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.org.permission.server.permission.entity.BasePermissionGroupResource;
import com.org.permission.server.permission.entity.BasePermissionResource;
import com.org.permission.server.permission.enums.ResourceMenuTypeEnum;
import com.org.permission.server.permission.mapper.BasePermissionResourceMapper;
import com.org.permission.server.permission.service.IBasePermissionGroupResourceService;
import com.org.permission.server.permission.service.IBasePermissionResourceService;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrgPermissionServerApplicationTests {

    @Resource
    private IBasePermissionResourceService basePermissionResourceService;
    @Resource
    private IBasePermissionGroupResourceService groupResourceService;

    @Resource
    private BasePermissionResourceMapper permissionResourceMapper;


    //    @Test
    public void contextLoads() {
        String data = getTXT();
        JSONArray jsonArray = JSON.parseArray(data);
        Map<String, JSONObject> map = new HashMap<>();
        Map<String, List<String>> mapArr = new HashMap<>();
        jsonArray.forEach(obj -> {
            JSONObject jsonObject = (JSONObject) obj;
            String menuId = jsonObject.getString("menuId");
            String parentId = jsonObject.getString("parentId");
            map.put(menuId, jsonObject);
            List<String> list = mapArr.get(parentId);
            if (CollectionUtils.isEmpty(list)) {
                list = new ArrayList<>();
            }
            list.add(menuId);
            mapArr.put(parentId, list);
        });
        Map<String, List<String>> mapArr1 = new HashMap<>();
        mapArr.forEach((k, v) -> {
            if (!"top".equals(k)) {
                mapArr1.put(k, v);
            }
        });


        Integer menuType = ResourceMenuTypeEnum.RF.getCode();
        for (int i = 0; i < 50; i++) {
            Long pid = 0L;
            String id = "top_" + i;
            JSONObject obj1 = map.get(id);
            if (obj1 == null) {
                continue;
            }
            BasePermissionResource basePermissionResource1 = build(obj1, menuType);
            basePermissionResource1.setParentId(pid);
            basePermissionResourceService.addBasePermissionResource(basePermissionResource1);
            Long pid0 = basePermissionResource1.getId();
            List<String> child = mapArr1.get(id);
            if (CollectionUtils.isEmpty(child)) {
                continue;
            }
            for (String m : child) {
                JSONObject objChild = map.get(m);
                if (objChild != null) {
                    String childId = objChild.getString("menuId");
                    BasePermissionResource basePermissionResourceChild = build(objChild, menuType);
                    basePermissionResourceChild.setParentId(pid0);
                    basePermissionResourceService.addBasePermissionResource(basePermissionResourceChild);
                    Long pid1 = basePermissionResourceChild.getId();
                    List<String> child1 = mapArr1.get(childId);
                    if (CollectionUtils.isEmpty(child1)) {
                        continue;
                    }
                    for (String n : child1) {
                        JSONObject objChild2 = map.get(n);
                        if (objChild2 != null) {
                            String childId3 = objChild2.getString("menuId");
                            BasePermissionResource basePermissionResourceChild2 = build(objChild2, menuType);
                            basePermissionResourceChild2.setParentId(pid1);
                            basePermissionResourceService.addBasePermissionResource(basePermissionResourceChild2);
                            Long pid4 = basePermissionResourceChild2.getId();
                            List<String> child4 = mapArr1.get(childId3);
                            if (CollectionUtils.isEmpty(child4)) {
                                continue;
                            }
                            for (String j : child4) {
                                JSONObject objChild4 = map.get(j);
                                if (objChild4 != null) {
                                    BasePermissionResource basePermissionResourceChild4 = buildButton(objChild4, menuType);
                                    basePermissionResourceChild4.setParentId(pid4);
                                    basePermissionResourceChild4.setResourceType(2);
                                    basePermissionResourceService.addBasePermissionResource(basePermissionResourceChild4);
                                }
                            }
                        }
                    }
                }
            }

        }


        groupRescous(menuType);
    }

    public void initGroupRescous() {
        groupRescous(ResourceMenuTypeEnum.RF.getCode());
    }

    public void groupRescous(Integer menuType) {
        BasePermissionResource basePermissionResource = new BasePermissionResource();
        basePermissionResource.setType(menuType);
        List<BasePermissionResource> list = basePermissionResourceService.getListBasePermissionResourcesByPOJO(basePermissionResource);
        for (BasePermissionResource resource : list) {
            BasePermissionGroupResource groupResource = new BasePermissionGroupResource();
            groupResource.setGroupId(1L);
            groupResource.setState(1);
            groupResource.setPermissionId(resource.getId());
            groupResource.setCreatedBy(1L);
            groupResource.setCreatedDate(new Date());
            groupResource.setCreatedName("System");
            groupResource.setModifiedBy(1L);
            groupResource.setModifiedDate(new Date());
            groupResource.setModifiedName("System");
            groupResourceService.addBasePermissionGroupResource(groupResource);
            groupResource = new BasePermissionGroupResource();
            groupResource.setGroupId(2L);
            groupResource.setState(1);
            groupResource.setPermissionId(resource.getId());
            groupResource.setCreatedBy(1L);
            groupResource.setCreatedDate(new Date());
            groupResource.setCreatedName("System");
            groupResource.setModifiedBy(1L);
            groupResource.setModifiedDate(new Date());
            groupResource.setModifiedName("System");
            groupResourceService.addBasePermissionGroupResource(groupResource);
        }
    }

    private BasePermissionResource build(JSONObject obj, Integer mentType) {
        BasePermissionResource basePermissionResource = new BasePermissionResource();
        basePermissionResource.setState(1);
        basePermissionResource.setIcon(obj.getString("icon"));
        basePermissionResource.setResourceType(1);
        basePermissionResource.setComment(obj.getString("component"));
        basePermissionResource.setBelong(1L);
        basePermissionResource.setResourceDesc(obj.getString("name"));
        basePermissionResource.setName(obj.getString("name"));
        basePermissionResource.setMenuOrder(1);
        basePermissionResource.setType(mentType);
        basePermissionResource.setUrl(obj.getString("url"));
        basePermissionResource.setBussinessSystem("WMS");
        basePermissionResource.setControlDisplay(1);
        basePermissionResource.setParentId(0L);
        basePermissionResource.setLeafFlag(obj.getBoolean("leaf") ? 1 : 0);
        basePermissionResource.setComponent(obj.getString("component"));
        basePermissionResource.setNumber(obj.getString("number"));
        basePermissionResource.setKeepAlive(obj.getBoolean("keepAlive") ? 1 : 0);
        basePermissionResource.setHidden(obj.getBoolean("hidden") ? 1 : 0);
        basePermissionResource.setRedirect(obj.getString("redirect"));
        basePermissionResource.setMenuOrder(obj.getInteger("menuOrder"));
        basePermissionResource.setIframePath(obj.getString("iframePath"));
        basePermissionResource.setOperations(obj.getString("operations"));
        basePermissionResource.setCreatedBy(1L);
        basePermissionResource.setCreatedDate(new Date());
        basePermissionResource.setCreatedName("System");
        basePermissionResource.setModifiedBy(1L);
        basePermissionResource.setModifiedDate(new Date());
        basePermissionResource.setModifiedName("System");
        return basePermissionResource;
    }

    private BasePermissionResource buildButton(JSONObject obj, Integer menuType) {
        BasePermissionResource basePermissionResource = new BasePermissionResource();
        basePermissionResource.setState(1);
        basePermissionResource.setResourceType(2);
        basePermissionResource.setBelong(1L);
        basePermissionResource.setResourceDesc(obj.getString("name"));
        basePermissionResource.setName(obj.getString("name"));
        basePermissionResource.setMenuOrder(1);
        basePermissionResource.setType(menuType);
        basePermissionResource.setBussinessSystem("WMS");
        basePermissionResource.setControlDisplay(1);
        basePermissionResource.setParentId(0L);
        basePermissionResource.setNumber(obj.getString("operations"));

        basePermissionResource.setOperations(obj.getString("operations"));
        basePermissionResource.setCreatedBy(1L);
        basePermissionResource.setCreatedDate(new Date());
        basePermissionResource.setCreatedName("System");
        basePermissionResource.setModifiedBy(1L);
        basePermissionResource.setModifiedDate(new Date());
        basePermissionResource.setModifiedName("System");
        return basePermissionResource;
    }

    public String getTXT() {
        try {
            StringBuffer stringBuffer = new StringBuffer();
            /* 读入TXT文件 */
            String pathname = "";
            File file = new File(pathname);
            BufferedReader br = new BufferedReader(new FileReader(file));// 构造一个BufferedReader类来读取文件
            String s = null;
            while ((s = br.readLine()) != null) {// 使用readLine方法，一次读一行
                stringBuffer.append(s);
            }
            br.close();
            return stringBuffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getTXTSCM() {
        try {
            StringBuffer stringBuffer = new StringBuffer();
            /* 读入TXT文件 */
            String pathname = "";
            File file = new File(pathname);
            BufferedReader br = new BufferedReader(new FileReader(file));// 构造一个BufferedReader类来读取文件
            String s = null;
            while ((s = br.readLine()) != null) {// 使用readLine方法，一次读一行
                stringBuffer.append(s);
            }
            br.close();
            return stringBuffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public void paltMenu() {
        JSONArray jsonArray = JSON.parseArray(getTXTSCM());
        Map<String, List<JSONObject>> map = new HashMap<>();
        Map<String, JSONObject> objMap = new HashMap<>();
        jsonArray.forEach(item -> {
            JSONObject jsonObject = (JSONObject) item;
            objMap.put(jsonObject.getString("menuId"), jsonObject);
            String parentId = jsonObject.getString("parentId");
            List<JSONObject> objectList = map.get(parentId);
            if (CollectionUtils.isEmpty(objectList)) {
                objectList = new ArrayList<>();
            }
            objectList.add(jsonObject);
            map.put(parentId, objectList);
        });
        map.forEach((key, val) -> {
            if ("970012318287138816".equals(key)) {
                savePlatMenu(0L, objMap.get(key), map);
            }
        });

    }

    /**
     * 平台低开菜单
     */
//    @Test
    public void platMenuAuth() {
        BasePermissionResource basePermissionResource = new BasePermissionResource();
        basePermissionResource.setType(0);
        basePermissionResource.setSource(1);
        basePermissionResource.setBussinessSystem("SCM");
        List<BasePermissionResource> list = basePermissionResourceService.getListBasePermissionResourcesByPOJO(basePermissionResource);
        for (BasePermissionResource resource : list) {
            BasePermissionGroupResource groupResource = new BasePermissionGroupResource();
            groupResource.setGroupId(1L);
            groupResource.setState(1);
            groupResource.setPermissionId(resource.getId());
            groupResource.setCreatedBy(1L);
            groupResource.setCreatedDate(new Date());
            groupResource.setCreatedName("System");
            groupResource.setModifiedBy(1L);
            groupResource.setModifiedDate(new Date());
            groupResource.setModifiedName("System");
            groupResourceService.addBasePermissionGroupResource(groupResource);
            groupResource = new BasePermissionGroupResource();
            groupResource.setGroupId(2L);
            groupResource.setState(1);
            groupResource.setPermissionId(resource.getId());
            groupResource.setCreatedBy(1L);
            groupResource.setCreatedDate(new Date());
            groupResource.setCreatedName("System");
            groupResource.setModifiedBy(1L);
            groupResource.setModifiedDate(new Date());
            groupResource.setModifiedName("System");
            groupResourceService.addBasePermissionGroupResource(groupResource);
        }

    }

    public void savePlatMenu(Long parentId, JSONObject jsonObject, Map<String, List<JSONObject>> map) {
        BasePermissionResource basePermissionResource = buildScm(jsonObject, 0, parentId);
        permissionResourceMapper.addBasePermissionResource(basePermissionResource);
        List<JSONObject> list = map.get(basePermissionResource.getPlatformId());
        if (CollectionUtils.isNotEmpty(list)) {
            list.forEach(item -> {
                savePlatMenu(basePermissionResource.getId(), item, map);
            });
        }
    }

    private BasePermissionResource buildScm(JSONObject obj, Integer mentType, Long parentId) {

        BasePermissionResource basePermissionResource = new BasePermissionResource();
        basePermissionResource.setState(1);
        basePermissionResource.setIcon(Objects.nonNull(obj.getString("icon")) ? obj.getString("icon") : null);
        basePermissionResource.setResourceType(1);
        basePermissionResource.setComment(obj.getString("component"));
        basePermissionResource.setBelong(1L);
        basePermissionResource.setResourceDesc(obj.getString("name"));
        basePermissionResource.setName(obj.getString("name"));
        basePermissionResource.setMenuOrder(1);
        basePermissionResource.setType(mentType);
        basePermissionResource.setUrl(obj.getString("url"));
        basePermissionResource.setBussinessSystem("SCM");
        basePermissionResource.setControlDisplay(1);
        basePermissionResource.setParentId(parentId);
        basePermissionResource.setLeafFlag(obj.getBoolean("leaf") ? 1 : 0);
        basePermissionResource.setComponent(obj.getString("component"));
        basePermissionResource.setNumber(obj.getString("number"));
        basePermissionResource.setKeepAlive(obj.getBoolean("keepAlive") ? 1 : 0);
        basePermissionResource.setHidden(obj.getBoolean("hidden") ? 1 : 0);
        basePermissionResource.setRedirect(obj.getString("redirect"));
        basePermissionResource.setMenuOrder(obj.getInteger("menuOrder"));
        basePermissionResource.setIframePath(obj.getString("iframePath"));
        basePermissionResource.setOperations(obj.getString("operations"));
        basePermissionResource.setCreatedBy(1L);
        basePermissionResource.setCreatedDate(new Date());
        basePermissionResource.setCreatedName("System");
        basePermissionResource.setModifiedBy(1L);
        basePermissionResource.setModifiedDate(new Date());
        basePermissionResource.setModifiedName("System");
        basePermissionResource.setPlatformId(obj.getString("menuId"));
        basePermissionResource.setSource(1);
        return basePermissionResource;
    }



    public void platButton() {
        List<String> list = getTXTSCMButton();
        Map<String, BasePermissionResource> map = new HashMap<>();
        for (String line : list) {
            String[] cell = line.split(",");
            String id = cell[1];
            String code = cell[2];
            String name = cell[3];
            String menuid = cell[4];
            Long parentId = 0L;
            if (map.containsKey(menuid)) {
                parentId = map.get(menuid).getId();
            } else {
                BasePermissionResource basePermissionResource = new BasePermissionResource();
                basePermissionResource.setPlatformId(menuid);
                List<BasePermissionResource> resourceList = permissionResourceMapper.getListBasePermissionResourcesByPOJO(basePermissionResource);
                if (CollectionUtils.isEmpty(resourceList)) {
                    continue;
                }
                map.put(menuid, resourceList.get(0));
                parentId = resourceList.get(0).getId();
            }
            BasePermissionResource addData = buildScmButton(id, name, code, menuid, parentId);
            System.out.println(JSON.toJSONString(addData));
            permissionResourceMapper.addBasePermissionResource(addData);
        }
    }

    public List<String> getTXTSCMButton() {
        try {
            List<String> list = new ArrayList<>();
            /* 读入TXT文件 */
            String pathname = "";
            File file = new File(pathname);
            BufferedReader br = new BufferedReader(new FileReader(file));// 构造一个BufferedReader类来读取文件
            String s = null;
            while ((s = br.readLine()) != null) {// 使用readLine方法，一次读一行
                list.add(s);
            }
            br.close();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private BasePermissionResource buildScmButton(String id, String name, String code, String menuid, Long parentId) {
        BasePermissionResource basePermissionResource = new BasePermissionResource();
        basePermissionResource.setState(1);
        basePermissionResource.setResourceType(2);
        basePermissionResource.setBelong(1L);
        basePermissionResource.setResourceDesc(name);
        basePermissionResource.setName(name);
        basePermissionResource.setMenuOrder(1);
        basePermissionResource.setType(0);
        basePermissionResource.setBussinessSystem("SCM");
        basePermissionResource.setControlDisplay(1);
        basePermissionResource.setParentId(parentId);
        basePermissionResource.setNumber(code);
        basePermissionResource.setOperations(code);
        basePermissionResource.setPlatformId(id);
        basePermissionResource.setCreatedBy(1L);
        basePermissionResource.setCreatedDate(new Date());
        basePermissionResource.setCreatedName("System");
        basePermissionResource.setModifiedBy(1L);
        basePermissionResource.setModifiedDate(new Date());
        basePermissionResource.setModifiedName("System");
        return basePermissionResource;
    }

   @Test
    public void testRedis() {
//       Jedis jedis = new Jedis("127.0.0.1", 6379);
//       System.out.println(jedis.ping());
       System.out.println("1111111111");
    }
}
