
package services;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.TickerRepository;
import domain.Ticker;

@Service
@Transactional(value = TxType.REQUIRES_NEW)
public class TickerService {

	@Autowired
	private TickerRepository	tickerRepository;


	public Ticker generateTicker(String companyName) throws NoSuchAlgorithmException {
		final Ticker ticker = new Ticker();
		Ticker result = null;
		//FIXME: A la hora de entregar, revisar la codificacion.
		companyName = companyName.replaceAll("[.,:;()�?" + " " + "!�-]", "");

		//Primera parte del ticker
		if (companyName.length() < 4)
			while (companyName.length() < 4)
				companyName = companyName + "X";
		else if (companyName.length() > 4)
			companyName = companyName.substring(0, 4);

		//Generate Random number
		final SecureRandom randomGenerator = SecureRandom.getInstance("SHA1PRNG");
		final Integer randomNumber = randomGenerator.nextInt(9999);
		String randonNumberString = randomNumber.toString();
		while (randonNumberString.length() < 4)
			randonNumberString = "0" + randonNumberString;
		final String identifier = companyName + "-" + randonNumberString;
		ticker.setIdentifier(identifier);

		result = this.tickerRepository.saveAndFlush(ticker);

		return result;
	}

	public void delete(final Ticker ticker) {
		this.tickerRepository.delete(ticker);

	}

}
