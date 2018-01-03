package application.model;

import com.fasterxml.jackson.databind.ObjectMapper;

abstract class ModelBase<T>
{
    <F> T copyFields(F f, Class<? extends T> clazz) throws Exception
    {
        ObjectMapper objectMapper = new ObjectMapper();
        byte[] json = objectMapper.writeValueAsBytes(f);
        return objectMapper.readValue(json, clazz);
    }

    protected abstract void set(T t) throws Exception;
}
