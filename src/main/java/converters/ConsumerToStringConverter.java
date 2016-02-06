package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Consumer;

@Component
@Transactional
public class ConsumerToStringConverter implements Converter<Consumer, String> {

	@Override
	public String convert(Consumer consumer) {
		String res;

		if (consumer == null)
			res = null;
		else
			res = String.valueOf(consumer.getId());

		return res;

	}

}
