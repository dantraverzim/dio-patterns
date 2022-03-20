package dio.patterns.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dio.patterns.model.CustomerModel;
import dio.patterns.repository.CustomerRepository;
import dio.patterns.model.AddressModel;
import dio.patterns.repository.AddressRepository;
import dio.patterns.service.CustomerService;
import dio.patterns.service.ViaCepService;

/**
 * Implementação da <b>Strategy</b> {@link CustomerService}, a qual pode ser
 * injetada pelo Spring (via {@link Autowired}). Com isso, como essa classe é um
 * {@link Service}, ela será tratada como um <b>Singleton</b>.
 * 
 * @author falvojr
 */
@Service
public class CustomerServiceImpl implements CustomerService {

	// Singleton: Injetar os componentes do Spring com @Autowired.
	@Autowired
	private CustomerRepository clienteRepository;
	@Autowired
	private AddressRepository enderecoRepository;
	@Autowired
	private ViaCepService viaCepService;
	
	// Strategy: Implementar os métodos definidos na interface.
	// Facade: Abstrair integrações com subsistemas, provendo uma interface simples.

	@Override
	public Iterable<CustomerModel> buscarTodos() {
		// Buscar todos os Clientes.
		return clienteRepository.findAll();
	}

	@Override
	public CustomerModel buscarPorId(Long id) {
		// Buscar Cliente por ID.
		Optional<CustomerModel> cliente = clienteRepository.findById(id);
		return cliente.get();
	}

	@Override
	public void inserir(CustomerModel customerModel) {
		salvarClienteComCep(customerModel);
	}

	@Override
	public void atualizar(Long id, CustomerModel customerModel) {
		// Buscar Cliente por ID, caso exista:
		Optional<CustomerModel> clienteBd = clienteRepository.findById(id);
		if (clienteBd.isPresent()) {
			salvarClienteComCep(customerModel);
		}
	}

	@Override
	public void deletar(Long id) {
		// Deletar Cliente por ID.
		clienteRepository.deleteById(id);
	}

	private void salvarClienteComCep(CustomerModel customerModel) {
		// Verificar se o Endereco do Cliente já existe (pelo CEP).
		String cep = customerModel.getAddressModel().getCep();
		AddressModel addressModel = enderecoRepository.findById(cep).orElseGet(() -> {
			// Caso não exista, integrar com o ViaCEP e persistir o retorno.
			AddressModel novoAddressModel = viaCepService.consultarCep(cep);
			enderecoRepository.save(novoAddressModel);
			return novoAddressModel;
		});
		customerModel.setAddressModel(addressModel);
		// Inserir Cliente, vinculando o Endereco (novo ou existente).
		clienteRepository.save(customerModel);
	}

}
