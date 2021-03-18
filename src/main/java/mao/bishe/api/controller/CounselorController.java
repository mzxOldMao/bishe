package mao.bishe.api.controller;

import mao.bishe.api.service.CounselorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/counselor")
public class CounselorController {
    @Autowired
    private CounselorService counselorService;
}
