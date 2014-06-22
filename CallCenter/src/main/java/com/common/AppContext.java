package com.common;

import com.common.core.tree.TreeNode;
import com.common.core.util.JsonHelper;
import com.common.security.entity.DataPrivilege;
import com.common.security.entity.Node;
import com.common.security.entity.Resource;
import com.common.security.service.DataPrivilegeManager;
import com.common.security.service.NodeManager;
import com.common.security.util.TreeUtil;
import com.redcard.system.entity.Dictionary;
import com.redcard.system.entity.SysParameter;
import com.redcard.system.service.DictionaryManager;
import com.redcard.system.service.JobScheduler;
import com.redcard.system.service.SysJobManager;
import com.redcard.system.service.SysParameterManager;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Allen
 * Date: 10/10/12
 */
@Component
public class AppContext implements ApplicationListener {

    private static Logger logger = LoggerFactory.getLogger(AppContext.class);

    private static volatile AppContext instance = null;

    private Map<Integer, HttpSession> sessionMap = new HashMap<Integer, HttpSession>();

    private enum DataType {DICTIONARY, RESOURCE, NODE, DATA_PRIVILEGE, SYS_PARAMETER}

    private int[] cacheStatus;

    @Autowired
    private NodeManager nodeManager;

    @Autowired
    private DataPrivilegeManager dataPrivilegeManager;

    @Autowired
    private DictionaryManager dictionaryManager;

    @Autowired
    private SysParameterManager sysParameterManager;

    @Autowired
    private JobScheduler jobScheduler;

    @Autowired
    private SysJobManager sysJobManager;

    @Autowired
    private ContextHolder contextHolder;

    private DataSync dataSync;

    private List<Resource> resources = new ArrayList<Resource>();

    private Map<String, DataPrivilege> dataPrivileges = new HashMap<String, DataPrivilege>();

    private Map<Integer, List<Dictionary>> dictMap = new HashMap<Integer, List<Dictionary>>();

    private TreeNode rootNode;   //模块根节点

    private Map<String, TreeNode> linkNodeMap = new HashMap<String, TreeNode>();  //用于根据请求地址快速检索模块节点

    private Map<String, SysParameter> parameters = new HashMap<String, SysParameter>();  //系统参数，要求参数名唯一

    private boolean hasStarted = false;    //系统是否已启动

    public AppContext() {
        cacheStatus = new int[DataType.values().length];
        for (int i = 0; i < cacheStatus.length; i++) {
            cacheStatus[i] = Constant.CACHE_NORMAL;
        }
    }

    /**
     * spring 启动和 spring mvc启动会分别调用一次该方法
     *
     * @param event event
     */
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof ContextRefreshedEvent && !hasStarted) {
            reloadAll();
            AppContext.instance = this;
            jobScheduler.initAllJob();
            hasStarted = true;
        }
    }

    public static AppContext getInstance() {
        return AppContext.instance;
    }

    public void reloadAll() {
        loadDictionary();
        loadResources();
        loadNodes();
        loadDataPrivileges();
        loadSysParameters();
    }

    /**
     * 同步缓存数据
     *
     * @param async    是否采用异步方式
     * @param dataType 数据类型
     */
    public synchronized void syncData(boolean async, DataType... dataType) {
        for (DataType type : dataType) {
            cacheStatus[type.ordinal()] = Constant.CACHE_EXPIRED;
        }
        if (async) {
            dataSync = new DataSync();
            dataSync.start();
        } else {
            syncCache();
        }
    }

    /**
     * 根据实体同步缓存数据
     *
     * @param clazz 实体类型
     */
    public void syncData(Class clazz) {
        if (Dictionary.class.equals(clazz)) {
            syncData(true, DataType.DICTIONARY);
        } else if (Node.class.equals(clazz)) {
            syncData(true, DataType.NODE);
        } else if (DataPrivilege.class.equals(clazz)) {
            syncData(true, DataType.DATA_PRIVILEGE);
        } else if (SysParameter.class.equals(clazz)) {
            syncData(true, DataType.SYS_PARAMETER);
        }
    }

    /**
     * 检查当前缓存数据状态并且同步
     */
    public synchronized void syncCache() {
        for (int i = 0; i < cacheStatus.length; i++) {
            int status = cacheStatus[i];
            if (status == Constant.CACHE_NORMAL)
                continue;
            DataType type = DataType.values()[i];
            switch (type) {
                case DICTIONARY:
                    loadDictionary();
                    break;
                case RESOURCE:
                    loadResources();
                    break;
                case NODE:
                    loadNodes();
                    break;
                case DATA_PRIVILEGE:
                    loadDataPrivileges();
                    break;
                case SYS_PARAMETER:
                    loadSysParameters();
            }
        }
    }

    private void loadDictionary() {
        logger.debug("load cache for dictionary");
        this.dictMap.clear();
        List<Dictionary> list = dictionaryManager.findCachedDictionary();
        for (Dictionary dictionary : list) {
            if (Constant.SYSTEM_DISABLE == dictionary.getStatus()) {
                continue;
            }
            if (this.dictMap.containsKey(dictionary.getType())) {
                this.dictMap.get(dictionary.getType()).add(dictionary);
            } else {
                List subList = new ArrayList<Dictionary>();
                subList.add(dictionary);
                this.dictMap.put(dictionary.getType(), subList);
            }
        }
        cacheStatus[DataType.DICTIONARY.ordinal()] = Constant.CACHE_NORMAL;
    }

    private void loadResources() {
        logger.debug("load cache for resource");
        this.resources.clear();
        ClassPathResource classPathResource = new ClassPathResource("resource.xml");
        try {
            this.resources = JsonHelper.deserialize(classPathResource.getInputStream(), List.class);
            cacheStatus[DataType.RESOURCE.ordinal()] = Constant.CACHE_NORMAL;
        } catch (IOException e) {
            logger.error("load resources error:{}", e.getMessage());
        }
    }

    private void loadNodes() {
        logger.debug("load cache for node");
        this.linkNodeMap.clear();
        rootNode = TreeUtil.buildNodeTreeV2(nodeManager.findCachedNode(), this.linkNodeMap);
        assert (rootNode != null);
        if (rootNode == null) {
            throw new RuntimeException("Fail to load node tree, please check node data.");
        }
        cacheStatus[DataType.NODE.ordinal()] = Constant.CACHE_NORMAL;
    }

    private void loadDataPrivileges() {
        logger.debug("load cache for data privilege");
        this.dataPrivileges.clear();
        List<DataPrivilege> privileges = this.dataPrivilegeManager.findCachedDataPrivilege();
        for (DataPrivilege privilege : privileges) {
            this.dataPrivileges.put(privilege.getMaster(), privilege);
        }
        cacheStatus[DataType.DATA_PRIVILEGE.ordinal()] = Constant.CACHE_NORMAL;
    }

    private void loadSysParameters() {
        logger.debug("load cache for sysParameter");
        this.parameters.clear();
        List<SysParameter> list = sysParameterManager.findCachedParameter();
        for (SysParameter sysParameter : list) {
            if (Constant.SYSTEM_DISABLE == sysParameter.getStatus()) {
                continue;
            }
            this.parameters.put(sysParameter.getName(), sysParameter);
        }
        cacheStatus[DataType.SYS_PARAMETER.ordinal()] = Constant.CACHE_NORMAL;
    }

    public List<Resource> getResources() {
        if (this.resources == null) {
            return new ArrayList<Resource>();
        }
        return this.resources;
    }

    public DataPrivilege getDataPrivilege(Class clazz) {
        return this.dataPrivileges.get(clazz.getSimpleName());
    }

    public String getDictName(Integer type, String value) {
        List<Dictionary> subList = this.dictMap.get(type);
        if (subList != null && subList.size() > 0) {
            for (Dictionary dictionary : subList) {
                if (dictionary.getValue().equals(value)) {
                    return dictionary.getName();
                }
            }
        }
        return "";
    }

    public List<Dictionary> getDictionary(Integer type) {
        List<Dictionary> subList = this.dictMap.get(type);
        return subList != null ? subList : new ArrayList<Dictionary>();
    }

    public SysParameter getSysParameter(String name) {
        return this.parameters.get(name);
    }

    public String getSysParameterValue(String name) {
        SysParameter sysParameter = this.getSysParameter(name);
        return sysParameter == null ? "" : sysParameter.getValue();
    }

    public Integer getIntParameterValue(String name) {
        String value = this.getSysParameterValue(name);
        if (StringUtils.isBlank(value)) {
            return null;
        }
        try {
            return Integer.valueOf(value);
        } catch (NumberFormatException e) {
            logger.error("can't get int value for parameter {}, the value should be numerical", name);
        }
        return null;
    }

    public int getIntParameterValue(String name, int defaultValue) {
        Integer value = getIntParameterValue(name);
        return value == null ? defaultValue : value.intValue();
    }

    /**
     * 根据节点链接查找对应的模块节点
     *
     * @param link 节点链接
     * @return 模块节点
     */
    public TreeNode findTreeNode(String link) {
        return this.linkNodeMap.get(link);
    }

    public void signIn(Integer userId, HttpSession session) {
        this.sessionMap.put(userId, session);
    }

    public void signOff(Integer userId) {
        this.sessionMap.remove(userId);
    }

    public HttpSession getSession(Integer userId) {
        return this.sessionMap.get(userId);
    }

    class DataSync extends Thread {
        @Override
        public void run() {
            logger.debug("reload cache[{}]", Thread.currentThread().getName());
            syncCache();
        }
    }
}
