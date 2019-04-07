
package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.TickerRepository;
import domain.Ticker;

@Service
@Transactional
public class TickerService {

	@Autowired
	TickerRepository	tickerRepository;


	public Ticker generateTicker(String companyName) {
		final Ticker ticker = new Ticker();
		Ticker result = null;

		//Primera parte del ticker
		if (companyName.length() < 4)
			while (companyName.length() < 4)
				companyName = companyName + "X";
		else if (companyName.length() > 4)
			companyName = companyName.substring(0, 4);

		int attempts = 50;

		while (attempts > 0) {
			//Generate Random number
			final Integer randomNumber = (int) Math.round((Math.random() * 9999));

			String randonNumberString = randomNumber.toString();

			while (randonNumberString.length() < 4)
				randonNumberString = "0" + randonNumberString;

			final String identifier = companyName + "-" + randonNumberString;
			ticker.setIdentifier(identifier);

			try {
				result = this.tickerRepository.saveAndFlush(ticker);
				attempts = 0;
			} catch (final Throwable oops) {
				oops.printStackTrace();
				attempts--;
			}
		}

		return result;
	}

	public void delete(final Ticker ticker) {
		this.tickerRepository.delete(ticker);

	}

}
