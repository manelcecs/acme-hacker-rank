
package utiles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import services.TickerService;
import domain.Ticker;

@Service
public class IntermediaryBetweenTransactions {

	@Autowired
	TickerService	tickerService;


	public Ticker generateTicker(final String companyName) {
		boolean exito = false;
		Ticker result = null;
		int attempt = 1;

		while (!exito)
			try {
				result = this.tickerService.generateTicker(companyName, attempt);
				exito = true;
			} catch (final Throwable oops) {
				oops.printStackTrace();
				attempt--;
			}
		return result;
	}

}
