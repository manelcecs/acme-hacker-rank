
package utiles;

import org.joda.time.LocalDate;
import org.springframework.validation.BindingResult;

import domain.CreditCard;

public class ValidateCreditCard {

	public static void checkFecha(final CreditCard card, final BindingResult binding) {
		final int annoActual = new LocalDate().getYear();
		final int mesActual = new LocalDate().getMonthOfYear();

		if (annoActual > card.getExpirationYear())
			binding.rejectValue("creditCard.expirationYear", "creditCard.exprirationYear.error");
		if (annoActual <= card.getExpirationYear())
			if (mesActual >= card.getExpirationYear())
				binding.rejectValue("creditCard.expirationMonth", "creditCard.expirationMonth.error");
	}

	public static CreditCard checkNumeroAnno(final CreditCard card) {
		int res = card.getExpirationYear();
		if (res < 100)
			res = res + 2000;
		final CreditCard result = card;
		result.setExpirationYear(res);
		return result;
	}

}
