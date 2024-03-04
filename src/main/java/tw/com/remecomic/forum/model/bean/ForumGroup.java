package tw.com.remecomic.forum.model.bean;

import java.util.Collection;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import lombok.NoArgsConstructor;
import tw.com.remecomic.userA.model.bean.UserA;

@Data
@NoArgsConstructor
@Entity
@Table(name="ForumGroup")
public class ForumGroup {
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="groupId")
	private Integer groupId;
	
	@Column(name="groupName")
	private String groupName;
		
//	@JsonIgnoreProperties({"gender","birthDate","registerDate",
//		"userPhoto","badges"})
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="userId")
	@JsonIgnore
	private ForumUser managerUser; //多對一fk
	
	@Column(name="buildTime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date buildTime;
	
	@OneToMany(mappedBy = "group",fetch = FetchType.LAZY,
			cascade = CascadeType.ALL,orphanRemoval = true)
	@JsonIgnore
	private Collection<ForumGroupUsers> users;
	
	@PrePersist
	protected void onCreate() {
		buildTime = new Date();
	}
}
