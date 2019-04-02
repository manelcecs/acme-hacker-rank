
package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.CompanyRepository;
import domain.Company;

@Service
@Transactional
public class CompanyService {

	@Autowired
	CompanyRepository	companyRepository;


	public Company findByPrincipal(final int idPrincipal) {
		return this.companyRepository.findByPrincipal(idPrincipal);
	}

}
