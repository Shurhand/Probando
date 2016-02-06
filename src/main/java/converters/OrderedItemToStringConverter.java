package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.OrderedItem;

@Component
@Transactional
public class OrderedItemToStringConverter implements Converter<OrderedItem, String> {

	@Override
	public String convert(OrderedItem orderedItem) {
		String res;

		if (orderedItem == null)
			res = null;
		else
			res = String.valueOf(orderedItem.getId());

		return res;

	}

}
