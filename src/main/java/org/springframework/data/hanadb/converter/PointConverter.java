package org.springframework.data.hanadb.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.hanadb.data.Point;

public interface PointCollectionConverter<T> extends Converter<T, Point>{

}
