package mao.bishe.api.service.impl;

import mao.bishe.api.dao.CounselorDao;
import mao.bishe.api.service.CounselorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CounselorServiceImpl implements CounselorService {
    @Autowired
    private CounselorDao counselorDao;
}
