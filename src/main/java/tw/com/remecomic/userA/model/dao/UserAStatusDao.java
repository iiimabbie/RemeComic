package tw.com.remecomic.userA.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import tw.com.remecomic.userA.model.bean.UserAStatus;

public interface UserAStatusDao extends JpaRepository<UserAStatus, Integer> {

}
