package com.lb.librarycatalogue.specification;

import com.lb.librarycatalogue.entity.BooksEntity;
import com.lb.librarycatalogue.entity.BooksEntity_;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class BookSpecifications {

    public static Specification<BooksEntity> likeAuthor(String author){
        return ((root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get(BooksEntity_.AUTHOR),author);
        });
    }

    public static Specification<BooksEntity> likeTitle(String title){
        return ((root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.like(root.get(BooksEntity_.TITLE),"%"+ title + "%");
        });
    }

    public static Specification<BooksEntity> equalIsbn(String isbn){
        return ((root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get(BooksEntity_.ISBN),isbn);
        });
    }

}
