package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.CashOrderRepository;

import domain.CashOrder;

@Component
@Transactional
public class StringToCashOrderConverter implements Converter<String, CashOrder> {
	@Autowired
	CashOrderRepository cashOrderRepository;

	@Override
	public CashOrder convert(String text) {
		CashOrder res;
		int id;
		System.out.println("Entro aqui");
		try {
			if (StringUtils.isEmpty(text))
				res = null;
			else {
				id = Integer.valueOf(text);
				res = cashOrderRepository.findOne(id);
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return res;
	}

}
