package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Tax;

@Component
@Transactional
public class TaxToStringConverter implements Converter<Tax, String> {

	@Override
	public String convert(Tax tax) {
		String res;

		if (tax == null)
			res = null;
		else
			res = String.valueOf(tax.getId());

		return res;

	}

}
