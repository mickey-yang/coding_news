package com.myang.codingnews.repositories;

import com.myang.codingnews.model.Source;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SourceRepository extends JpaRepository<Source, String> {

}
