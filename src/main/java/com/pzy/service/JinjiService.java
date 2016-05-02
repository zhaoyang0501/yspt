
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
import com.pzy.entity.Jinji;
import com.pzy.repository.JinjiRepository;
/***
 * 
 * @author qq:263608237
 *
 */
@Service
public class JinjiService {
     @Autowired
     private JinjiRepository jinjiRepository;

 	public List<Jinji> findTop3() {
 		return jinjiRepository.findAll(
 				new PageRequest(0, 15, new Sort(Direction.DESC, "createDate")))
 				.getContent();
 	}
     public List<Jinji> findAll() {
         return (List<Jinji>) jinjiRepository.findAll(new Sort(Direction.DESC, "id"));
     }
     public Page<Jinji> findAll(final int pageNumber, final int pageSize,final String name){
         PageRequest pageRequest = new PageRequest(pageNumber - 1, pageSize, new Sort(Direction.DESC, "id"));
         Specification<Jinji> spec = new Specification<Jinji>() {
              public Predicate toPredicate(Root<Jinji> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
              Predicate predicate = cb.conjunction();
              if (name != null) {
                   predicate.getExpressions().add(cb.like(root.get("name").as(String.class), "%"+name+"%"));
              }
              return predicate;
              }
         };
         Page<Jinji> result = (Page<Jinji>) jinjiRepository.findAll(spec, pageRequest);
         return result;
     	}
     
     public Page<Jinji> findAll(final int pageNumber, final int pageSize,final String key,final Category category){
         PageRequest pageRequest = new PageRequest(pageNumber - 1, pageSize, new Sort(Direction.DESC, "id"));
         Specification<Jinji> spec = new Specification<Jinji>() {
              public Predicate toPredicate(Root<Jinji> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
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
         Page<Jinji> result = (Page<Jinji>) jinjiRepository.findAll(spec, pageRequest);
         return result;
     	}
     
     public Page<Jinji> findAll(final int pageNumber, final int pageSize,final Integer type ){
         PageRequest pageRequest = new PageRequest(pageNumber - 1, pageSize, new Sort(Direction.DESC, "id"));
         Specification<Jinji> spec = new Specification<Jinji>() {
              public Predicate toPredicate(Root<Jinji> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
              Predicate predicate = cb.conjunction();
              if (type != null) {
                  predicate.getExpressions().add(cb.equal(root.get("type").as(Integer.class),type));
               }
              return predicate;
              }
         };
         Page<Jinji> result = (Page<Jinji>) jinjiRepository.findAll(spec, pageRequest);
         return result;
     	}
		public void delete(Long id){
			jinjiRepository.delete(id);
		}
		public Jinji find(Long id){
			  return jinjiRepository.findOne(id);
		}
		public void save(Jinji jinji){
			jinjiRepository.save(jinji);
		}
		public List<Jinji> findBycategory(final Long id){
	         Specification<Jinji> spec = new Specification<Jinji>() {
	              public Predicate toPredicate(Root<Jinji> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
	              Predicate predicate = cb.conjunction();
	              if (id != null) {
	                   predicate.getExpressions().add(cb.equal(root.get("category").get("id").as(Long.class), id));
	              }
	              return predicate;
	              }
	         };
	         return  jinjiRepository.findAll(spec);
	    }
}