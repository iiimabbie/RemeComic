package tw.com.remecomic.helpCenter.model.bean;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "HelpQuestion")
public class HelpQuestion {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "helpId")
	private Integer helpId;
	
	@Column(name = "category")
	private Integer category;
	
	@Column(name = "question")
	private String question;
	
	@Column(name = "answer")
	private String answer;

	public HelpQuestion(Integer helpId, Integer category, String question, String answer) {
		super();
		this.helpId = helpId;
		this.category = category;
		this.question = question;
		this.answer = answer;
	}

	public HelpQuestion(Integer category) {
		super();
		this.category = category;
	}
	
	
}
