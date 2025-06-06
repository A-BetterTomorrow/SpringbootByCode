package org.zerock.edu.ex2.repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.zerock.edu.ex2.entity.Memo;

import jakarta.transaction.Transactional;

@SpringBootTest
public class MemoRepositoryTests 
{
	@Autowired
	MemoRepository memoRepository;

	@Test
	public void testClass() {
		System.out.println("memoRepository.getClass().getName()=" + memoRepository.getClass().getName());
	}

	@Test
	public void testInsertDummies() {
		IntStream.rangeClosed(1, 100).forEach(i -> {
			Memo memo = Memo.builder().memoText("Sample..." + i).build();
			memoRepository.save(memo);
		});
	}
	
	@Test
	public void testSelect() {
		Long mno = 101L;
		Optional<Memo> result = memoRepository.findById(mno);
		System.out.println("1---------------------------");
		if(result.isPresent()) {
			System.out.println("2---------------------------");
			Memo memo = result.get();
			System.out.println("memo="+memo);
		}
	}
	
//	@Transactional
//	@Test
//	public void testSelect2() {
//		Long mno = 100L;
//		Memo memo = memoRepository.getOne(mno);
//		System.out.println("1---------------------------");
//			System.out.println("memo="+memo);
//	}

	@Test
	public void testUpdate() {
		Memo memo = Memo.builder().mno(102L).memoText("Update Text100").createDate("20250510").build();
		System.out.println("1---------------------------");
		System.out.println(memoRepository.save(memo));
		System.out.println("2---------------------------");
	}

	@Test
	public void testDelete() {
		Long mno = 100L;
		memoRepository.deleteById(mno);
	}

	@Test
	public void testPageDefault() {
		Sort sort = Sort.by("mno").descending();

	    //1페이지 10개
	    Pageable pageable = PageRequest.of(0,10, sort);

	    Page<Memo> result = memoRepository.findAll(pageable);

	    System.out.println(result);

	    System.out.println("---------------------------------------");

	    System.out.println("Total Pages: "+result.getTotalPages()); //총 몇 페이지

	    System.out.println("Total Count: "+result.getTotalElements()); //전체 개수

	    System.out.println("Page Number: "+result.getNumber()); //현재 페이지 번호 0부터 시작

	    System.out.println("Page Size: "+result.getSize()); //페이지당 데이터 개수 

	    System.out.println("has next page?: "+result.hasNext()); //다음 페이지 존재 여부 

	    System.out.println("first page?: "+result.isFirst()); //시작 페이지(0) 여부 

	}
	@Test
	public void testQueryMethods(){
		List<Memo> list = memoRepository.findByMnoBetweenOrderByMnoDesc(70L, 80L);
		for(Memo memo : list) {
			System.out.println(memo);
		}
	}

	@Test
	public void testQueryMethodWithPagable() {
		Pageable pageable = PageRequest.of(0, 10, Sort.by("mno").descending());
		Page<Memo> result = memoRepository.findByMnoBetween(10L,50L, pageable);
		result.get().forEach(memo -> System.out.println(memo));
	}
	
	@Commit
	@Transactional
	@Test
	public void testDeleteQueryMethods() {
		memoRepository.deleteMemoByMnoLessThan(10L);
	}
	
	@Test
	public void testGetListDesc() {
		List<Memo> list = memoRepository.getListDesc();
		for(Memo memo : list) {
			System.out.println(memo);
		}
	}
	
	//@Commit
	//@Transactional
	@Test
	public void testupdateMemoText() {
		memoRepository.updateMemoText(10L, "bbbbbbbbbbb");
	}


	//
	@Test
	public void testgetListWithQueryObject() {
		Pageable pageable = PageRequest.of(0, 10);
		Page<Object[]> result = memoRepository.getListWithQueryObject(10L, pageable);
		//System.out.println(result);
		//result.get().forEach(memo -> System.out.println(memo));
	}
}
