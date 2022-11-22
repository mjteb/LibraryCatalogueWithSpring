package com.lb.librarycatalogue.repository;

import com.lb.librarycatalogue.entity.LibraryMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibraryMemberRepository extends JpaRepository <LibraryMemberEntity, String> {
}
