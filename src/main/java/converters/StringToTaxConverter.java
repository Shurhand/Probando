package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.TaxRepository;

import domain.Tax;

@Component
@Transactional
public class StringToTaxConverter implements Converter<String, Tax> {
	@Autowired
	TaxRepository taxRepository;

	@Override
	public Tax convert(String text) {
		Tax res;
		int id;
		try {
			if (StringUtils.isEmpty(text))
				res = null;
			else {
				id = Integer.valueOf(text);
				res = taxRepository.findOne(id);
				System.out.println(res);
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return res;
	}

}
