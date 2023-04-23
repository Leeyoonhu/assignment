package com.assignment.repository;

import com.assignment.domain.Reserve;
import com.assignment.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ReserveRepository extends JpaRepository<Reserve, Integer> {
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO reserve (yadm_nm, hosp_url, addr, telno, cl_cd_nm, id, name, phone_no, symptom, upload_file, date) " +
            "VALUES (:#{#reserve.yadmNm}, :#{#reserve.hospUrl}, :#{#reserve.addr}, :#{#reserve.telno}, :#{#reserve.clCdNm}, " +
            ":#{#reserve.id}, :#{#reserve.name}, :#{#reserve.phoneNo}, :#{#reserve.symptom}, :#{#reserve.uploadFile}, :#{#reserve.date})", nativeQuery = true)
    void insert(Reserve reserve);

    @Query(value = "SELECT * FROM reserve WHERE id = :id ORDER BY date ASC", nativeQuery = true)
    List<Reserve> findById(String id);

    @Query(value = "SELECT * FROM reserve WHERE id = :id AND yadm_nm = :yadmNm AND date = :date", nativeQuery = true)
    Reserve findByIdAndYadmNmAndDate(String id, String yadmNm, String date);
}
