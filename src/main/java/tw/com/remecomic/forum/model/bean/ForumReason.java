package tw.com.remecomic.forum.model.bean;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name="ForumReason")
public class ForumReason {
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="reasonId")
	private Integer reasonId;
	
	@Column(name="reasonContent")
	private String reasonContent;
	
	@OneToMany(mappedBy = "reason",cascade = CascadeType.ALL,orphanRemoval = true)
	@JsonIgnoreProperties({"reason"})
	private Collection<ForumPostReport> reports; //使用該理由進行的檢舉
}
