package webapplication.dao;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import webapplication.vo.PhonebookVO;

import java.util.List;

public class PhonebookDAOTest {
    public static void main(String[] args) {
    	//원래는 junit4로 dao에서 void main 메서드를 작성하여 테스트하려 했으나 
    	//bean파일이 로드되지 않아 오류가 나는 바람에 그냥 따로 test용 main메서드를 작성하여 테스트했습니다.
        // 스프링 설정 파일 로드 (dispatcher-servlet.xml과 spring-database.xml을 로드함)
        ApplicationContext context = new FileSystemXmlApplicationContext("src/main/webapp/WEB-INF/dispatcher-servlet.xml");

        // PhonebookDAO 빈 가져오기
        PhonebookDAO phonebookDAO = context.getBean("phonebookDAO", PhonebookDAO.class);

        // 새로운 전화번호부 항목 삽입
        PhonebookVO user1 = new PhonebookVO();
        user1.setId(1);
        user1.setName("John Doe");
        user1.setHp("123-4567");
        user1.setMemo("test1");

        PhonebookVO user2 = new PhonebookVO(2, "Darius", "111-1111", "test2");
        PhonebookVO user3 = new PhonebookVO(3, "Garen", "222-1111", "test3");
        PhonebookVO user4 = new PhonebookVO(4, "Aatrox", "333-1111", "test4");

        // Insert 테스트
        System.out.println("=== Insert Test ===");
        int insertResult = phonebookDAO.insert(user1);
        System.out.println("Insert result: " + insertResult);
        System.out.println("Inserted ID: " + user1.getId());

        phonebookDAO.insert(user2);
        phonebookDAO.insert(user3);
        phonebookDAO.insert(user4);

        // 전체 조회 테스트
        System.out.println("\n=== Select All Test ===");
        List<PhonebookVO> allEntries = phonebookDAO.selectAll();
        allEntries.forEach(entry -> System.out.println(entry.getId() + ": " + entry.getName() + ", " + entry.getHp() + ", " + entry.getMemo()));

        // ID로 검색 테스트
        System.out.println("\n=== Select By ID Test ===");
        PhonebookVO selectedEntry = phonebookDAO.selectById(user1.getId());
        System.out.println("Selected entry: " + selectedEntry.getName() + ", " + selectedEntry.getHp() + ", " + selectedEntry.getMemo());

        // 전화번호로 검색 테스트
        System.out.println("\n=== Search By Phone Number Test ===");
        List<PhonebookVO> searchResult = phonebookDAO.search("123-4567");
        searchResult.forEach(entry -> System.out.println(entry.getId() + ": " + entry.getName() + ", " + entry.getHp()));

        // 항목 업데이트 테스트
        System.out.println("\n=== Update Test ===");
        System.out.println("id2 -> :" + user3);
        user3.setHp("222-3333");
        int updateResult = phonebookDAO.update(user3);
        System.out.println("Update result: " + updateResult);
        System.out.println("id2 -> :" + user3);

        // 항목 삭제 테스트
        System.out.println("\n=== Delete Test ===");
        int deleteResult = phonebookDAO.delete(user1.getId());
        System.out.println("Delete result: " + deleteResult);

        // 전체 조회 테스트
        System.out.println("\n=== Select All Test ===");
        List<PhonebookVO> allEntries2 = phonebookDAO.selectAll();
        allEntries2.forEach(entry -> System.out.println(entry.getId() + ": " + entry.getName() + ", " + entry.getHp() + ", " + entry.getMemo()));
    }
}
