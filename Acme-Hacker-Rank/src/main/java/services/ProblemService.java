
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.ProblemRepository;
import domain.Problem;

@Service
@Transactional
public class ProblemService {

	@Autowired
	private ProblemRepository	problemRepository;


	public Collection<Problem> getProblemsOfParade(final int idPosition) {
		return this.problemRepository.getProblemsOfParade(idPosition);
	}

	public void deleteCollectionOfProblems(final Collection<Problem> problems) {
		this.problemRepository.delete(problems);
	}

}
