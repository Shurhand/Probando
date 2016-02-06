package converters;

import java.util.Arrays;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.CategoryRepository;

import domain.Category;

@Component
@Transactional
public class StringToCategoryConverter implements Converter<String, Category> {
	@Autowired
	CategoryRepository categoryRepository;

	@Override
	public Category convert(String text) {
		Category res = null;
		
		int id;
		try {
			if (StringUtils.isEmpty(text)){
				res = null;
			}else {
				id = Integer.valueOf(text);
				res = categoryRepository.findOne(id);
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return res;
	}

}
