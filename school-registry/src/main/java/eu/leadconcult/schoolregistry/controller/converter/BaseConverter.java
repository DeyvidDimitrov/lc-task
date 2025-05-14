package eu.leadconcult.schoolregistry.controller.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.converter.Converter;

public abstract class BaseConverter<S, T> implements Converter<S, T> {

    @Lazy
    @Autowired
    private CollectionConversionService conversionService;

    protected CollectionConversionService conversionService() {
        return conversionService;
    }
}
