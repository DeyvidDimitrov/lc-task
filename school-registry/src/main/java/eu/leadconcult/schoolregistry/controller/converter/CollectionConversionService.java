package eu.leadconcult.schoolregistry.controller.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Primary
@Component
@RequiredArgsConstructor
public class CollectionConversionService implements ConversionService {

    private final ConversionService conversionService;

    @Override
    public boolean canConvert(Class<?> sourceType, Class<?> targetType) {
        return conversionService.canConvert(sourceType, targetType);
    }

    @Override
    public boolean canConvert(TypeDescriptor sourceType, TypeDescriptor targetType) {
        return conversionService.canConvert(sourceType, targetType);
    }

    @Override
    public <T> T convert(Object source, Class<T> targetType) {
        return conversionService.convert(source, targetType);
    }

    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        return conversionService.convert(source, sourceType, targetType);
    }

    public <T> List<T> convert(List<?> source, Class<T> targetType) {
        return source.stream().map(object -> convert(object, targetType)).toList();
    }

    public <T> List<T> convert(Collection<?> source, Class<T> targetType) {
        return source.stream().map(object -> convert(object, targetType)).toList();
    }

    public <T> Page<T> convert(Page<?> source, Class<T> targetType) {
        return source.map(object -> convert(object, targetType));
    }

}
