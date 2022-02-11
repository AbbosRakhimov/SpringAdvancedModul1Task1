package uz.pdp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import uz.pdp.entity.Address;
import uz.pdp.entity.Company;
import uz.pdp.payload.CompanyDto;
import uz.pdp.payload.ResponseClass;
import uz.pdp.repository.AddressRepository;
import uz.pdp.repository.CompanyRepository;

@Service
public class CompanyService {

	@Autowired
	CompanyRepository  companyRepository;
	
	@Autowired
	AddressRepository addressRepository;
	
	public ResponseClass addCompany(CompanyDto companyDto) {
		if(companyRepository.existsByCorpName(companyDto.getCorpName()))
			return new ResponseClass("Company exist", false);

		Address address = new Address(companyDto.getStreet(), companyDto.getHomeNumber());
		Address address2 = addressRepository.save(address);
		Company company = new Company(companyDto.getCorpName(), companyDto.getDirectorName(),address2);
		companyRepository.save(company);
		return new ResponseClass("Company added", true);
	}
	public Page<Company> getAllCampoy(int page){
		Pageable pageable=PageRequest.of(page, 5);
		Page<Company> cPage = companyRepository.findAll(pageable);
		return cPage;
	}
	public List<Company> getCompanies(){
		return companyRepository.findAll();
	}
	public ResponseClass getCompany(Integer id) {
		Optional<Company> cOptional=companyRepository.findById(id);
		if(cOptional.isPresent()) {
			return new ResponseClass("access successfull", true , cOptional.get());
		}
		return new ResponseClass("Company not found", false);
	}
	public ResponseClass editCompany(Integer id, CompanyDto companyDto) {
		if(companyRepository.existsByCorpNameAndIdNot(companyDto.getCorpName(), id))
		return new ResponseClass("Company exist", false);
		Optional<Company> cOptional=companyRepository.findById(id);
		if(!cOptional.isPresent())
			return new ResponseClass("Company not found", false);
		Optional< Address> aOptional = addressRepository.findById(id);
		if(!aOptional.isPresent())
			return new ResponseClass("Address not found", false);
		Address address = aOptional.get();
		address.setHomeNumber(companyDto.getHomeNumber());
		address.setStreet(companyDto.getStreet());
		Address address2 = addressRepository.save(address);
		Company company = cOptional.get();
		company.setAddress(address2);
		company.setCorpName(companyDto.getCorpName());
		company.setDirectorName(companyDto.getDirectorName());
		companyRepository.save(company);
		return new ResponseClass("Company edited", true);
	}
	public ResponseClass deleteCompany(Integer id) {
		Optional<Company> cOptional = companyRepository.findById(id);
		if(cOptional.isPresent()) {
			companyRepository.deleteById(id);
			return new ResponseClass("Company deleted", true);
		}
		return new ResponseClass("Company not found", false);
	}
}
