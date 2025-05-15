package org.zerock.edu.ex2.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.edu.ex2.entity.Memo;

import jakarta.transaction.Transactional;


public interface MemoRepository extends JpaRepository<Memo, Long> {
	List<Memo> findByMnoBetweenOrderByMnoDesc(Long from, Long to);
	Page<Memo> findByMnoBetween(Long from, Long to, Pageable pageable);
	void deleteMemoByMnoLessThan(Long num);
	
	@Query("select m from Memo m order by m.mno desc")
	List<Memo> getListDesc();
	
	@Transactional
	@Modifying
	@Query("update Memo m set m.memoText = :memoText where m.mno = :mno ")
	int updateMemoText(@Param("mno") Long mno, @Param("memoText") String memoText );

	@Transactional
	@Modifying
	@Query("update Memo m set m.memoText = :#{#param.memoText} where m.mno = :#{#param.mno} ")
	int updateMemoText(@Param("param") Memo memo );

	@Query(value = "select m from Memo m where m.mno > :mno",
	        countQuery = "select count(m) from Memo m where m.mno > :mno" )
	Page<Memo> getListWithQuery(Long mno, Pageable pageable);

	@Query(value = "select m.mno, m.memoText, CURRENT_DATE from Memo m where m.mno > :mno",
	        countQuery = "select count(m) from Memo m where m.mno > :mno" )
	//필요한 칼럼들(엔티티 객체의 속성들)만 지정해서 추출하는 기능 
	Page<Object[]> getListWithQueryObject(Long mno, Pageable pageable);

	
	@Query(value = "select * from memo where mno > 0", nativeQuery = true)
	//nativeQuery속성을 true로 지정하면 일반 SQL을 그대로 사용할 수 있음 
	List<Object[]> getNativeResult();

}
