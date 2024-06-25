package com.example.modo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.modo.domain.MoimSchedule;

@Repository
public interface MoimScheduleRepository extends JpaRepository<MoimSchedule, Long> {

	
//	List<MoimSchedule> findMoimScheduleByMoim_IdOrderByScheduleNoDesc(Long id);
	@Query(value = "SELECT ms.* " +
            "FROM moim_schedule ms " +
            "WHERE ms.moim_id = :id " +
            "ORDER BY ms.schedule_no DESC", 
	    nativeQuery = true)
	List<MoimSchedule> findMoimSchedulesByMoimId(@Param("id") Long id);

    List<MoimSchedule> findAllBy();

    @Query("SELECT ms FROM MoimSchedule ms JOIN ms.joinedMember jm WHERE jm.id IN :moimMemberIds")
    List<MoimSchedule> findByJoinedMemberInQuery(@Param("moimMemberIds") List<Long> moimMemberIds);

    List<Long> findDistinctJoinedMemberBy();

    
}
