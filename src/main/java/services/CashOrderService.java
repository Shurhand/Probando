package services;

import java.util.Collection;
import java.util.Random;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.CashOrderRepository;
import security.Authority;
import security.LoginService;
import domain.CashOrder;
import domain.Consumer;

@Service
@Transactional
public class CashOrderService {

	// ================== Managed repository ==================
	@Autowired
	private CashOrderRepository cashOrderRepository;

	// ================== Supporting services ==================
	@Autowired
	private ConsumerService consumerService;

	// ================== Constructors ==================
	public CashOrderService() {
		super();
	}

	// ================== Simple CRUD methods ==================

	public CashOrder create() {
		CashOrder result;
		String ticker;

		result = new CashOrder();
		ticker = TickerGenerator();
		result.setTicker(ticker);

		return result;
	}

	public CashOrder save(CashOrder order) {
		Assert.notNull(order);
		CashOrder result;

		result = cashOrderRepository.save(order);

		return result;
	}

	public void delete(CashOrder order) {
		Assert.notNull(order);
		Assert.isTrue(order.getId() != 0);

		cashOrderRepository.delete(order);

	}

	public CashOrder findOne(int cashOrderID) {
		Assert.isTrue(cashOrderID != 0);

		CashOrder result;

		result = cashOrderRepository.findOne(cashOrderID);
		Assert.notNull(result);

		return result;
	}

	public Collection<CashOrder> findAll() {
		Collection<CashOrder> result;
		boolean admin = false;
		Collection<Authority> authority;

		authority = LoginService.getPrincipal().getAuthorities();
		for (Authority a : authority) {
			if (a.getAuthority().equals(Authority.ADMIN)) {
				admin = true;
			}
		}
		Assert.isTrue(admin);

		result = cashOrderRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Collection<CashOrder> findByPrincipal() {
		Collection<CashOrder> res;
		Consumer consumer;
		consumer = consumerService.findPrincipal();
		res = cashOrderRepository.findByConsumerId(consumer.getId());

		return res;
	}

	// ================== Other business methods ==================

	public Collection<CashOrder> getRegisteredCashOrders() {
		Collection<CashOrder> result;

		result = cashOrderRepository.getRegisteredCashOrders();
		Assert.notNull(result);

		return result;
	}

	private String TickerGenerator() {
		UUID codigoUUID;
		Integer digitos;
		String codigo1, ticker, tickerFinal;
		String[] codigo2;
		Random random;

		random = new Random();
		digitos = 000000 + random.nextInt(999999);
		codigo1 = String.format("%06d", digitos);
		codigoUUID = UUID.randomUUID();
		ticker = codigoUUID.toString();
		codigo2 = ticker.split("-");
		tickerFinal = codigo1 + "-" + codigo2[1];

		return tickerFinal;
	}
}
