package webapplication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import webapplication.dao.PhonebookDAO;
import webapplication.vo.PhonebookVO;

import java.util.List;

@Service
public class PhonebookService {

    @Autowired
    private PhonebookDAO phonebookDAO;

    // 새로운 전화번호부 항목 추가
    @Transactional
    public int addEntry(PhonebookVO phonebook) {
        // 필드 길이 제한 검증
        if (!isValidPhonebookEntry(phonebook)) {
            return 0; // 필드가 제한을 초과한 경우 0을 반환
        }

        // 데이터베이스에 저장
        return phonebookDAO.insert(phonebook);
    }

    // 전체 전화번호부 항목 조회
    public List<PhonebookVO> getAllEntries() {
        return phonebookDAO.selectAll();
    }

    // ID로 전화번호부 항목 조회
    public PhonebookVO getEntryById(int id) {
        return phonebookDAO.selectById(id);
    }

    // 전화번호로 전화번호부 항목 검색
    public List<PhonebookVO> searchByPhoneNumber(String phoneNumber) {
        return phonebookDAO.search(phoneNumber);
    }

    // 전화번호부 항목 업데이트
    @Transactional
    public int updateEntry(PhonebookVO phonebook) {
        // 필드 길이 제한 검증
        if (!isValidPhonebookEntry(phonebook)) {
            return 0; // 필드가 제한을 초과한 경우 0을 반환
        }

        // 데이터베이스에 업데이트
        return phonebookDAO.update(phonebook);
    }

    // 전화번호부 항목 삭제
    @Transactional
    public boolean deleteEntry(int id) {
        int result = phonebookDAO.delete(id);
        return result > 0; // 삭제 성공 여부 반환
    }

    // 전화번호부 항목의 필드 길이 검증 메서드
    private boolean isValidPhonebookEntry(PhonebookVO phonebook) {
        if (phonebook.getName().length() > 20) {
            System.out.println("Name cannot exceed 20 characters.");
            return false;
        }
        if (phonebook.getHp().length() > 13) {
            System.out.println("Phone number cannot exceed 13 characters.");
            return false;
        }
        if (phonebook.getMemo().length() > 200) {
            System.out.println("Memo cannot exceed 200 characters.");
            return false;
        }
        return true;
    }
}
