package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Store;

@Component
@Transactional
public class StoreToStringConverter implements Converter<Store, String> {

	@Override
	public String convert(Store store) {
		String res;

		if (store == null)
			res = null;
		else
			res = String.valueOf(store.getId());

		return res;

	}

}
