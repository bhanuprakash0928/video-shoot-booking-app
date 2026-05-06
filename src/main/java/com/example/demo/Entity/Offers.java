package com.example.demo.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="Offers")
public class Offers {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Column(nullable=false)
	private String title;

	@Column(nullable=false)
	private String smallD;

	@Column(nullable=false)
	private String largeD;

	@Column(nullable=false)
	private String image;

	@Column
	private int discount;

	@Column(nullable=false)
	private Double amount;

	@Column(nullable=false)
	private int slot;

	@Column(nullable=false)
	private int duration;

	@Column
	 @JsonFormat(pattern = "yyyy-MM-dd")
	private String date;
	 @JsonFormat(pattern = "HH:mm")
	private String startTime;
	 @JsonFormat(pattern = "HH:mm")
	private String endTime;

	public Long getId(){return id;}

	public String getTitle(){return title;}
	public void setTitle(String title){this.title=title;}

	public String getSmallD(){return smallD;}
	public void setSmallD(String smallD){this.smallD=smallD;}

	public String getLargeD(){return largeD;}
	public void setLargeD(String largeD){this.largeD=largeD;}

	public Double getAmount(){return amount;}
	public void setAmount(Double amount){this.amount=amount;}

	public int getSlot(){return slot;}
	public void setSlot(int slot){this.slot=slot;}

	public int getDuration(){return duration;}
	public void setDuration(int duration){this.duration=duration;}

	public String getImage(){return image;}
	public void setImage(String image){this.image=image;}

	public int getDiscount(){return discount;}
	public void setDiscount(int discount){this.discount=discount;}


	public String getDate(){return date;}
	public void setDate(String date){this.date=date;}

	public String getStartTime(){return startTime;}
	public void setStartTime(String startTime){this.startTime=startTime;}

	public String getEndTime(){return endTime;}
	public void setEndTime(String endTime){this.endTime=endTime;}
	
}