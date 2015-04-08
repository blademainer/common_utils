/**
 * YIXUN_1.5_EE
 */
package com.xiongyingqi.util;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Method;
import java.util.*;

/**
 * @author 瑛琪 <a href="http://xiongyingqi.com">xiongyingqi.com</a>
 * @version 2013-10-17 下午12:19:48
 */
public class SpringMVCHelper {
	/**
	 * 获取包下所有被Controller注解的类所表示的路径 <br>
	 * 2013-10-17 下午1:02:45
	 * 
	 * @param pkg
	 * @return
	 */
	public static Collection<String> getAnnotationMappedPaths(Package pkg) {
		if (pkg == null) {
			return null;
		}
		Collection<String> rs = new LinkedHashSet<String>();
		Set<Class<?>> classes = PackageUtil.getclass(pkg);
		for (Iterator iterator = classes.iterator(); iterator.hasNext();) {
			Class<?> clazz = (Class<?>) iterator.next();
			if (clazz.isAnnotationPresent(Controller.class)) {
				String[] clazzPaths = null;
				if (clazz.isAnnotationPresent(RequestMapping.class)) {
					RequestMapping typeMapping = clazz.getAnnotation(RequestMapping.class);
					clazzPaths = typeMapping.value();
				}

				String[] methodPaths = null;
				Collection<String> methodPathCollection = new ArrayList<String>();
				Method[] methods = clazz.getDeclaredMethods();
				for (int i = 0; i < methods.length; i++) {
					Method method = methods[i];
					if (method.isAnnotationPresent(RequestMapping.class)) {
						RequestMapping typeMapping = method.getAnnotation(RequestMapping.class);
						String[] methodPathsVar = typeMapping.value();
						Collections.addAll(methodPathCollection, methodPathsVar);
					}
				}
				if (methodPathCollection.size() > 0) {
					methodPaths = methodPathCollection.toArray(new String[] {});
				}

				if (clazzPaths != null && clazzPaths.length > 0 && methodPaths != null
						&& methodPaths.length > 0) {
					for (int i = 0; i < clazzPaths.length; i++) {
						String typePath = clazzPaths[i];
						typePath = checkForPath(typePath);
						for (int j = 0; j < methodPaths.length; j++) {
							String methodPath = methodPaths[j];
							methodPath = checkForPath(methodPath);
							String mappedPath = typePath + methodPath;
							rs.add(mappedPath);
						}
					}
				} else if ((clazzPaths != null && clazzPaths.length > 0)
						&& (methodPaths == null || methodPaths.length == 0)) {
					for (int i = 0; i < clazzPaths.length; i++) {
						String typePath = clazzPaths[i];
						typePath = checkForPath(typePath);
						rs.add(typePath);
					}
				} else if ((methodPaths != null && methodPaths.length > 0)
						&& (clazzPaths == null || clazzPaths.length == 0)) {
                    EntityHelper.print(methodPaths);
                    for (int i = 0; i < clazzPaths.length; i++) {
						String typePath = clazzPaths[i];
						typePath = checkForPath(typePath);
						rs.add(typePath);
					}
				}
			}
		}
		return rs;
	}

	private static String checkForPath(String path) {
		if (!path.startsWith("/")) {
			path = "/" + path;
		}
		if (path.endsWith("/")) {
			path = path.substring(0, path.length() - 1);
		}
		return path;
	}
}
