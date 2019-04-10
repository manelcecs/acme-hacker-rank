
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.CurriculaRepository;
import domain.Curricula;

@Service
@Transactional
public class CurriculaService {

	@Autowired
	private CurriculaRepository	curriculaRepository;


	public Curricula findOne(final int idCurricula) {
		return this.curriculaRepository.findOne(idCurricula);
	}

	public Collection<Curricula> getCurriculasOfHacker(final int idHacker) {
		return this.curriculaRepository.getCurriculasOfHacker(idHacker);
	}
}
