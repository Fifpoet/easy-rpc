package cn.colins.rpc.common.extension;

import cn.colins.rpc.common.exception.EasyRpcRunException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: easy-rpc
 * @description:
 * @author: colins
 * @create: 2023-06-17 22:02
 **/
public class EasyRpcExtensionLoader<T> {
    private static final Logger log = LoggerFactory.getLogger(EasyRpcExtensionLoader.class);

    private final static String DIR_PATH = "META-INF/rpc/";

    // 配置文件读取缓存
    private static final Map<Class<?>, Map<String, String>> EXTENSION_INFO_CACHE = new ConcurrentHashMap<Class<?>, Map<String, String>>(32);

    // EasyRpcExtensionLoader缓存
    private static final Map<Class<?>, EasyRpcExtensionLoader<?>> EXTENSION_LOADERS = new ConcurrentHashMap(32);
    // 拓展的示例对象缓存
    private static final Map<String, Object> EXTENSION_INSTANCE = new ConcurrentHashMap(32);

    private final Class<?> currentClassType;

    private final Map<String, String> currentKeyValue;

    static {
        // 拓展加载
        loadDirectory();
    }

    private EasyRpcExtensionLoader(Class<?> type, Map<String, String> keyValue) {
        this.currentClassType = type;
        this.currentKeyValue = keyValue;
    }


    public static <T> EasyRpcExtensionLoader<T> getExtensionLoader(Class<T> type) {
        if (type == null) {
            throw new EasyRpcRunException("Easy-Rpc Extension type == null");
        }
        // 校验当前类型是否为接口
        if (!type.isInterface()) {
            throw new EasyRpcRunException("Easy-Rpc Extension type (" + type + ") is not an interface!");
        }
        // 校验该接口是否存在
        Map<String, String> keyValue = EXTENSION_INFO_CACHE.get(type);
        if (keyValue == null) {
            throw new EasyRpcRunException("Easy-Rpc Extension type  (" + type + ") not find");
        }

        EasyRpcExtensionLoader<T> easyRpcExtensionLoader = (EasyRpcExtensionLoader<T>) EXTENSION_LOADERS.get(type);
        // 内存中不存在则直接new一个扩展
        if (easyRpcExtensionLoader == null) {
            EXTENSION_LOADERS.putIfAbsent(type, new EasyRpcExtensionLoader<T>(type, keyValue));
            easyRpcExtensionLoader = (EasyRpcExtensionLoader<T>) EXTENSION_LOADERS.get(type);
        }
        return easyRpcExtensionLoader;
    }

    public T getExtensionByAlias(String name) {
        if (name == null) {
            throw new EasyRpcRunException("Easy-Rpc Extension instance name == null");
        }
        // 获取实例class
        String classInfo = this.currentKeyValue.get(name);
        if (classInfo == null) {
            throw new EasyRpcRunException("Easy-Rpc Extension instance name (" + name + ") not find classInfo");
        }
        // 先从缓存中获取
        Object instance = EXTENSION_INSTANCE.get(name);
        if (instance == null) {
            try {
                Class<?> beanClass = Class.forName(classInfo);
                if(!this.currentClassType.isAssignableFrom(beanClass)){
                    throw new EasyRpcRunException(" no implementation interface: " + this.currentClassType);
                }
                EXTENSION_INSTANCE.putIfAbsent(name,beanClass.newInstance());
                instance = EXTENSION_INSTANCE.get(name);
            } catch (ClassNotFoundException e) {
                throw new EasyRpcRunException("Easy-Rpc Extension instance class (" + classInfo + ") not find ");
            }catch (Exception e){
                throw new EasyRpcRunException("Easy-Rpc Extension instance class (" + classInfo + ") error：" + e.getMessage());
            }
        }
        return (T) instance;
    }

    private final static void loadDirectory() {
        try {
            List<String> fileName = getFileName();
            for (String className : fileName) {
                try {
                    Class<?> loadClass = Class.forName(className);
                    // 校验当前类型是否为接口
                    if (!loadClass.isInterface()) {
                        throw new EasyRpcRunException("Easy-Rpc Extension type (" + loadClass + ") is not an interface!");
                    }
                    EXTENSION_INFO_CACHE.put(loadClass, loadFileContent(className));
                } catch (ClassNotFoundException e) {
                    throw new EasyRpcRunException("Easy-Rpc extension loader fail: ("+ className +") class not find ");
                }
            }
        } catch (IOException e) {
            throw new EasyRpcRunException("Easy-Rpc extension loader fail: " + e.getMessage());
        }
    }

    private final static List<String> getFileName() throws IOException {
        List<String> fileNames = new ArrayList<>(16);
        Enumeration<URL> urls = EasyRpcExtensionLoader.class.getClassLoader().getResources(DIR_PATH);
        while (urls.hasMoreElements()) {
            URL resource = urls.nextElement();
            fileNames.addAll(loadResource(resource));
        }
        return fileNames;

    }

    private final static Map<String, String> loadFileContent(String fileName) throws IOException {
        Map<String, String> keyValue = new HashMap<>(16);
        Enumeration<URL> resources = EasyRpcExtensionLoader.class.getClassLoader().getResources(DIR_PATH + fileName);
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            loadResource(resource).forEach(item -> {
                // 定位 # 字符
                final int ci = item.indexOf('#');
                if (ci >= 0) {
                    // 截取 # 之前的字符串，# 之后的内容为注释，需要忽略
                    item = item.substring(0, ci);
                }
                item = item.trim();
                if (item.length() > 0) {
                    int i = item.indexOf('=');
                    if (i > 0) {
                        // 以等于号 = 为界，截取键与值
                        keyValue.put(item.substring(0, i).trim(), item.substring(i + 1).trim());
                    }
                }
            });
        }
        return keyValue;
    }

    private final static List<String> loadResource(URL resource) throws IOException {
        List<String> urlResource = new ArrayList<>(16);
        BufferedReader reader = new BufferedReader(new InputStreamReader(resource.openStream(), "utf-8"));
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                urlResource.add(line);
            }
        } finally {
            reader.close();
        }
        return urlResource;
    }

}
