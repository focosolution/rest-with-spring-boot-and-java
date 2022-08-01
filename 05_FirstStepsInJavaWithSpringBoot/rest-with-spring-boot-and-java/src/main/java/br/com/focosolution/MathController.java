package br.com.focosolution;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.focosolution.exceptions.UnsuportedMathOperationException;

@RestController
public class MathController {
	
	// Utilizando Path Params, exemplo: http://localhost:8080/sum/1/2
	@RequestMapping(value = "/sum/{numberOne}/{numberTwo}", method=RequestMethod.GET)
	public Double sum(
			@PathVariable(value = "numberOne") String paramNumberOne,
			@PathVariable(value = "numberTwo") String paramNumberTwo
		) {
		System.out.println("paramNumberOne = " + paramNumberOne);
		System.out.println("paramNumberTwo = " + paramNumberTwo);
		if ((paramNumberOne == null) || (paramNumberTwo == null)) {
			throw new UnsuportedMathOperationException("Valor não é numérico!");
		}
		return convertToDouble(paramNumberOne) + convertToDouble(paramNumberTwo);
	}
	
	// Utilizando Path Params, exemplo: http://localhost:8080/multiply/1/2
	@RequestMapping(value = "/multiply/{numberOne}/{numberTwo}", method=RequestMethod.GET)
	public Double multiply(
			@PathVariable(value = "numberOne") String paramNumberOne,
			@PathVariable(value = "numberTwo") String paramNumberTwo
		) {
		System.out.println("paramNumberOne = " + paramNumberOne);
		System.out.println("paramNumberTwo = " + paramNumberTwo);
		if ((paramNumberOne == null) || (paramNumberTwo == null)) {
			throw new UnsuportedMathOperationException("Valor não é numérico!");
		}
		return convertToDouble(paramNumberOne) * convertToDouble(paramNumberTwo);
	}	

	private Double convertToDouble(String paramNumber) {
		Double retorno = null;
		try {
			retorno = Double.valueOf(paramNumber);
		} catch (NumberFormatException e) {
			throw new UnsuportedMathOperationException("Valor não é numérico!");
		}
		if (retorno == null) {
			return 0D;
		}
		return retorno;
	}

}
