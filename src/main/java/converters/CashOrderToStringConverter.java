package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.CashOrder;

@Component
@Transactional
public class CashOrderToStringConverter implements Converter<CashOrder, String> {

	@Override
	public String convert(CashOrder cashOrder) {
		String res;

		if (cashOrder == null)
			res = null;
		else
			res = String.valueOf(cashOrder.getId());

		return res;

	}

}
