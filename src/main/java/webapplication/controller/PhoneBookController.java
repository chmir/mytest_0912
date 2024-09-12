package webapplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import webapplication.service.PhonebookService;
import webapplication.vo.PhonebookVO;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Controller
public class PhoneBookController {

    @Autowired
    PhonebookService phonebookService;

    @GetMapping("/index")
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("index");
        return mav;
    }

    // 전화번호로 검색
    @GetMapping("/search")
    public ModelAndView search(@RequestParam("hp") String phoneNumber) {
        List<PhonebookVO> result = phonebookService.searchByPhoneNumber(phoneNumber);
        ModelAndView mav = new ModelAndView("index");
        mav.addObject("searchResult", result);
        return mav;
    }

    // 선택된 전화번호부 항목의 상세 정보를 가져옴
    @GetMapping("/detail")
    public ModelAndView detail(@RequestParam("id") int id) {
        PhonebookVO phonebook = phonebookService.getEntryById(id);
        ModelAndView mav = new ModelAndView("index");
        mav.addObject("detail", phonebook);
        return mav;
    }

    // 수정 처리
    @PostMapping("/update")
    public ModelAndView update(@RequestParam("id") int id,
                               @RequestParam("name") String name,
                               @RequestParam("hp") String phoneNumber,
                               @RequestParam("memo") String memo) throws UnsupportedEncodingException {

        // UTF-8로 변환
        name = new String(name.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        phoneNumber = new String(phoneNumber.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        memo = new String(memo.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);

        PhonebookVO phonebook = new PhonebookVO(id, name, phoneNumber, memo);
        phonebookService.updateEntry(phonebook);
        return new ModelAndView("redirect:/index");
    }

    // 삭제 처리
    @PostMapping("/delete")
    public ModelAndView delete(@RequestParam("id") int id) {
        phonebookService.deleteEntry(id);
        return new ModelAndView("redirect:/index");
    }

    // 새로운 사용자 추가 처리
    @PostMapping("/addUser")
    public ModelAndView addUser(@RequestParam("name") String name,
                                @RequestParam("hp") String phoneNumber,
                                @RequestParam("memo") String memo) throws UnsupportedEncodingException {

        // UTF-8로 변환
        name = new String(name.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        phoneNumber = new String(phoneNumber.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        memo = new String(memo.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);

        PhonebookVO phonebook = new PhonebookVO();
        phonebook.setName(name);
        phonebook.setHp(phoneNumber);
        phonebook.setMemo(memo);

        phonebookService.addEntry(phonebook);

        return new ModelAndView("redirect:/index");
    }
}
