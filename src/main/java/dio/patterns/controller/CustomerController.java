package dio.patterns.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dio.patterns.model.CustomerModel;
import dio.patterns.service.CustomerService;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

	private CustomerService customerService;

	public CustomerController(CustomerService customerService) {
		this.customerService = customerService;
	}


	@GetMapping
	public ResponseEntity<Iterable<CustomerModel>> buscarTodos() {
		return ResponseEntity.ok(customerService.buscarTodos());
	}

	@GetMapping("/{id}")
	public ResponseEntity<CustomerModel> buscarPorId(@PathVariable Long id) {
		return ResponseEntity.ok(customerService.buscarPorId(id));
	}

	@PostMapping
	public ResponseEntity<CustomerModel> inserir(@RequestBody CustomerModel customerModel) {
		customerService.inserir(customerModel);
		return ResponseEntity.ok(customerModel);
	}

	@PutMapping("/{id}")
	public ResponseEntity<CustomerModel> atualizar(@PathVariable Long id, @RequestBody CustomerModel customerModel) {
		customerService.atualizar(id, customerModel);
		return ResponseEntity.ok(customerModel);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletar(@PathVariable Long id) {
		customerService.deletar(id);
		return ResponseEntity.ok().build();
	}
}
