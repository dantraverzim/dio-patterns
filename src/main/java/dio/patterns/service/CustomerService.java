package dio.patterns.service;

import dio.patterns.model.CustomerModel;

/**
 * Interface que define o padrão <b>Strategy</b> no domínio de cliente. Com
 * isso, se necessário, podemos ter multiplas implementações dessa mesma
 * interface.
 * 
 * @author falvojr
 */
public interface CustomerService {

	Iterable<CustomerModel> buscarTodos();

	CustomerModel buscarPorId(Long id);

	void inserir(CustomerModel customerModel);

	void atualizar(Long id, CustomerModel customerModel);

	void deletar(Long id);

}
