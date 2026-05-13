		package com.example.demo.Entity;

		import java.time.LocalDate;
		import java.time.LocalDateTime;
		
		import jakarta.persistence.Column;
		import jakarta.persistence.Entity;
		import jakarta.persistence.GeneratedValue;
		import jakarta.persistence.GenerationType;
		import jakarta.persistence.Id;
		import jakarta.persistence.Table;

		@Entity
		@Table(name="login_data")
		public class Login {
			@Id
			@GeneratedValue(strategy=GenerationType.IDENTITY)
			private Long id;

			@Column(nullable=true)
			private String number;

			@Column(nullable=true)
			private String name;

			@Column(nullable=true,unique=true)
			private String email;




			@Column(nullable=true)
			private String password;


			@Column
			private String otp;


			@Column(name="otp_expiry")
			private LocalDateTime otpExpiry;

			 @Column(name="date_of_birth")
			private LocalDate dateOfBirth;

			@Column(name="profile_image")
			private String profileImage;

			@Column(name="dark_mode")
			private Boolean darkMode = false;


			public Long getId(){return id;}

			public String getotp(){return otp;}
			public void setotp(String otp){this.otp=otp;}

			public String getName(){return name;}
			public void setName(String name){this.name=name;}

			public String getNumber(){return number;}
			public void setNumber(String number){this.number=number;}

			public String getEmail(){return email;}
			public void setEmail(String email){this.email=email;}

			public String getPassword(){return password;}
			public void setPassword(String password){this.password=password;}

			public LocalDateTime getOtpExpiry() {
				return otpExpiry;
			}

			public void setOtpExpiry(LocalDateTime otpExpiry) {
				this.otpExpiry = otpExpiry;
			}
			public LocalDate getDateOfBirth() {
          		return dateOfBirth;
			}

			public void setDateOfBirth(LocalDate dateOfBirth) {
				this.dateOfBirth = dateOfBirth;
			}

			public String getProfileImage() {
				return profileImage;
			}

			public void setProfileImage(String profileImage) {
				this.profileImage = profileImage;
			}

			public Boolean getDarkMode() {
				return darkMode;
			}

			public void setDarkMode(Boolean darkMode) {
				this.darkMode = darkMode;
			}

			
		}
