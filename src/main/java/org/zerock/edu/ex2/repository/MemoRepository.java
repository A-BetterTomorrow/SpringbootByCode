package org.zerock.edu.ex2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.edu.ex2.entity.Memo;

public interface MemoRepository extends JpaRepository<Memo, Long> {}
