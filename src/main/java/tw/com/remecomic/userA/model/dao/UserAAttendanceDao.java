package tw.com.remecomic.userA.model.dao;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import tw.com.remecomic.userA.model.bean.UserAAttendance;

public interface UserAAttendanceDao extends JpaRepository<UserAAttendance, Integer> {

	@Query("SELECT u FROM UserAAttendance u WHERE u.user.userId = :userId AND u.attendDate = :attendDate")
	Optional<UserAAttendance> findByUserIdAndAttendDate(@Param("userId") Integer userId,
			@Param("attendDate") Date attendDate);

}
