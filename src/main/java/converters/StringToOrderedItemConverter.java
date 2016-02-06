package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.OrderedItemRepository;

import domain.OrderedItem;

@Component
@Transactional
public class StringToOrderedItemConverter implements Converter<String, OrderedItem> {
	@Autowired
	OrderedItemRepository orderedItemRepository;

	@Override
	public OrderedItem convert(String text) {
		OrderedItem res;
		int id;
		try {
			if (StringUtils.isEmpty(text))
				res = null;
			else {
				id = Integer.valueOf(text);
				res = orderedItemRepository.findOne(id);
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return res;
	}

}
