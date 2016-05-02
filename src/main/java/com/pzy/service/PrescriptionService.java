
package com.pzy.service;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.pzy.entity.Category;
import com.pzy.entity.Prescription;
import com.pzy.repository.PrescriptionRepository;
/***
 * 
 * @author qq:263608237
 *
 */
@Service
public class PrescriptionService {
     @Autowired
     private PrescriptionRepository prescriptionRepository;

 	public List<Prescription> findTop3() {
 		return prescriptionRepository.findAll(
 				new PageRequest(0, 15, new Sort(Direction.DESC, "createDate")))
 				.getContent();
 	}
     public List<Prescription> findAll() {
         return (List<Prescription>) prescriptionRepository.findAll(new Sort(Direction.DESC, "id"));
     }
     public Page<Prescription> findAll(final int pageNumber, final int pageSize,final String name){
         PageRequest pageRequest = new PageRequest(pageNumber - 1, pageSize, new Sort(Direction.DESC, "id"));
         Specification<Prescription> spec = new Specification<Prescription>() {
              public Predicate toPredicate(Root<Prescription> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
              Predicate predicate = cb.conjunction();
              if (name != null) {
                   predicate.getExpressions().add(cb.like(root.get("name").as(String.class), "%"+name+"%"));
              }
              return predicate;
              }
         };
         Page<Prescription> result = (Page<Prescription>) prescriptionRepository.findAll(spec, pageRequest);
         return result;
     	}
     
     public Page<Prescription> findAll(final int pageNumber, final int pageSize,final String key,final Category category){
         PageRequest pageRequest = new PageRequest(pageNumber - 1, pageSize, new Sort(Direction.DESC, "id"));
         Specification<Prescription> spec = new Specification<Prescription>() {
              public Predicate toPredicate(Root<Prescription> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
              Predicate predicate = cb.conjunction();
              if (key != null) {
                   predicate.getExpressions().add(cb.like(root.get("title").as(String.class), "%"+key+"%"));
              }
              if (category != null) {
                  predicate.getExpressions().add(cb.equal(root.get("category").as(Category.class), category));
              }
              return predicate;
              }
         };
         Page<Prescription> result = (Page<Prescription>) prescriptionRepository.findAll(spec, pageRequest);
         return result;
     	}
     
     public Page<Prescription> findAll(final int pageNumber, final int pageSize,final Integer type ){
         PageRequest pageRequest = new PageRequest(pageNumber - 1, pageSize, new Sort(Direction.DESC, "id"));
         Specification<Prescription> spec = new Specification<Prescription>() {
              public Predicate toPredicate(Root<Prescription> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
              Predicate predicate = cb.conjunction();
              if (type != null) {
                  predicate.getExpressions().add(cb.equal(root.get("type").as(Integer.class),type));
               }
              return predicate;
              }
         };
         Page<Prescription> result = (Page<Prescription>) prescriptionRepository.findAll(spec, pageRequest);
         return result;
     	}
		public void delete(Long id){
			prescriptionRepository.delete(id);
		}
		public Prescription find(Long id){
			  return prescriptionRepository.findOne(id);
		}
		public void save(Prescription prescription){
			prescriptionRepository.save(prescription);
		}
		public List<Prescription> findBycategory(final Long id){
	         Specification<Prescription> spec = new Specification<Prescription>() {
	              public Predicate toPredicate(Root<Prescription> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
	              Predicate predicate = cb.conjunction();
	              if (id != null) {
	                   predicate.getExpressions().add(cb.equal(root.get("category").get("id").as(Long.class), id));
	              }
	              return predicate;
	              }
	         };
	         return  prescriptionRepository.findAll(spec);
	    }
}