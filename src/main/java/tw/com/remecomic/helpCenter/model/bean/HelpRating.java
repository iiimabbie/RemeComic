package tw.com.remecomic.helpCenter.model.bean;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import tw.com.remecomic.userA.model.bean.UserA;

@Data
@Entity
@NoArgsConstructor
@Table(name = "HelpRating")
public class HelpRating {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ratingId")
	private Integer ratingId;

	@Column(name = "rating")
	private Integer rating;

	@OneToOne
	@JoinColumn(name = "userId")
//	@JsonManagedReference(value = "rating-user")
	private UserA ratingToUser;

	@ManyToOne
	@JoinColumn(name = "serverUserId")
//	@JsonBackReference(value = "rating-servUser")
	private UserA ratingToServUser;

	@ManyToOne
	@JoinColumn(name = "reportId")
	@JsonBackReference(value = "rating-report")
	private HelpReport ratingToReport;

}
