
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
import com.pzy.entity.Knowledge;
import com.pzy.repository.KnowledgeRepository;
/***
 * 
 * @author qq:263608237
 *
 */
@Service
public class KnowledgeService {
     @Autowired
     private KnowledgeRepository knowledgeRepository;

 	public List<Knowledge> findTop3() {
 		return knowledgeRepository.findAll(
 				new PageRequest(0, 15, new Sort(Direction.DESC, "createDate")))
 				.getContent();
 	}
     public List<Knowledge> findAll() {
         return (List<Knowledge>) knowledgeRepository.findAll(new Sort(Direction.DESC, "id"));
     }
     public Page<Knowledge> findAll(final int pageNumber, final int pageSize,final String name){
         PageRequest pageRequest = new PageRequest(pageNumber - 1, pageSize, new Sort(Direction.DESC, "id"));
         Specification<Knowledge> spec = new Specification<Knowledge>() {
              public Predicate toPredicate(Root<Knowledge> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
              Predicate predicate = cb.conjunction();
              if (name != null) {
                   predicate.getExpressions().add(cb.like(root.get("name").as(String.class), "%"+name+"%"));
              }
              return predicate;
              }
         };
         Page<Knowledge> result = (Page<Knowledge>) knowledgeRepository.findAll(spec, pageRequest);
         return result;
     	}
     
     public Page<Knowledge> findAll(final int pageNumber, final int pageSize,final String key,final Category category){
         PageRequest pageRequest = new PageRequest(pageNumber - 1, pageSize, new Sort(Direction.DESC, "id"));
         Specification<Knowledge> spec = new Specification<Knowledge>() {
              public Predicate toPredicate(Root<Knowledge> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
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
         Page<Knowledge> result = (Page<Knowledge>) knowledgeRepository.findAll(spec, pageRequest);
         return result;
     	}
     
     public Page<Knowledge> findAll(final int pageNumber, final int pageSize,final Integer type ){
         PageRequest pageRequest = new PageRequest(pageNumber - 1, pageSize, new Sort(Direction.DESC, "id"));
         Specification<Knowledge> spec = new Specification<Knowledge>() {
              public Predicate toPredicate(Root<Knowledge> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
              Predicate predicate = cb.conjunction();
              if (type != null) {
                  predicate.getExpressions().add(cb.equal(root.get("type").as(Integer.class),type));
               }
              return predicate;
              }
         };
         Page<Knowledge> result = (Page<Knowledge>) knowledgeRepository.findAll(spec, pageRequest);
         return result;
     	}
		public void delete(Long id){
			knowledgeRepository.delete(id);
		}
		public Knowledge find(Long id){
			  return knowledgeRepository.findOne(id);
		}
		public void save(Knowledge knowledge){
			knowledgeRepository.save(knowledge);
		}
		public List<Knowledge> findBycategory(final Long id){
	         Specification<Knowledge> spec = new Specification<Knowledge>() {
	              public Predicate toPredicate(Root<Knowledge> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
	              Predicate predicate = cb.conjunction();
	              if (id != null) {
	                   predicate.getExpressions().add(cb.equal(root.get("category").get("id").as(Long.class), id));
	              }
	              return predicate;
	              }
	         };
	         return  knowledgeRepository.findAll(spec);
	    }
}