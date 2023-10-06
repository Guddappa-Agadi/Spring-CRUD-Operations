package com.springboot;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController {

	@Autowired
	private EmployeeRepo eRepo;

	@PostMapping("/employee")
	public ResponseEntity<Employee> saveData(@RequestBody Employee emp) {
		return new ResponseEntity<>(eRepo.save(emp), HttpStatus.CREATED);
	}

	@GetMapping("/employee")
	public ResponseEntity<List<Employee>> getAll() {
		List<Employee> lst = eRepo.findAll();
		return new ResponseEntity<>(lst, HttpStatus.OK);
	}

	@GetMapping("/employee/{id}")
	public ResponseEntity<Employee> getDataById(@PathVariable int id) {
		Optional<Employee> obj = eRepo.findById(id);
		if (obj.isPresent()) {
			return new ResponseEntity<>(obj.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.OK);
		}
	}

	@PutMapping("/employee/{id}")
	public ResponseEntity<Employee> updateDataById(@PathVariable int id, @RequestBody Employee emp) {
		Optional<Employee> obj = eRepo.findById(id);
		if (obj.isPresent()) {
			obj.get().setName(emp.getName());
			obj.get().setSalary(emp.getSalary());
			return new ResponseEntity<>(obj.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.OK);
		}
	}

	@DeleteMapping("/employee/{id}")
	public ResponseEntity<Employee> deleteDataById(@PathVariable int id) {
		Optional<Employee> obj = eRepo.findById(id);
		if (obj.isPresent()) {
			eRepo.deleteById(id);
			return new ResponseEntity<>(obj.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.OK);
		}
	}

	@GetMapping("/employee/page")
	public List<Employee> getPage() {
		Pageable p = PageRequest.of(0, 10, Sort.by("id").ascending());
		Page<Employee> page = eRepo.findAll(p);
		return page.getContent();
	}
}
