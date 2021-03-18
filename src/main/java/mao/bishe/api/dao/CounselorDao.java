package mao.bishe.api.dao;

import mao.bishe.api.model.Counselor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CounselorDao extends JpaRepository<Counselor, Long> {
    
}
