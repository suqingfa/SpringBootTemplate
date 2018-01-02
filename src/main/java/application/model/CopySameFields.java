package application.model;

import java.lang.reflect.Method;
import java.util.*;

final class CopySameFields
{
    private static final Map<String, Set<String>> classFieldsCache = new HashMap<>();

    private CopySameFields()
    {
    }

    public static <F, T> void copySameFields(F f, T t)
    {
        Set<String> fields = getClassFields(t.getClass());
        for (Method method : t.getClass().getMethods())
        {
            if (!method.getName().startsWith("set") || method.getParameterCount() != 1)
            {
                continue;
            }
            String fieldName = method.getName().substring(3);
            if (!fields.contains(fieldName))
            {
                continue;
            }

            try
            {
                method.setAccessible(true);
                Object value = f.getClass().getMethod("get" + fieldName).invoke(f);
                method.invoke(t, value);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    private static Set<String> getClassFields(Class clazz)
    {
        synchronized (classFieldsCache)
        {
            Set<String> strings = classFieldsCache.get(clazz.getName());
            if (strings == null)
            {
                Method[] methods = clazz.getMethods();
                strings = new HashSet<>(methods.length);
                for (Method method : methods)
                {
                    if (method.getName().equals("getClass") || method.getParameterCount() != 0)
                    {
                        continue;
                    }

                    if (method.getName().startsWith("get"))
                    {
                        strings.add(method.getName().substring(3));
                    }
                }
                classFieldsCache.put(clazz.getName(), strings);
            }
            return strings;
        }
    }
}
