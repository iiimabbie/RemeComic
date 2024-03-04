package tw.com.remecomic.forum.model.bean;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name="ForumNotifiyType")
public class ForumNotifyType {
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="notifyTypeId")
	private Integer notifyTypeId;
	
	@Column(name="typeContent")
	private String typeContent;
	
}
