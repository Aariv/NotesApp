package com.speer.notesapp.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.elasticsearch.annotations.Document;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
@Document(indexName = "note")
public class Note {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String title;

	@Column(length = 1000)
	private String description;

	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date date;

	@ManyToOne
	private User user;
	
	@ManyToMany
    @JoinTable(
        name = "note_shared_users",
        joinColumns = @JoinColumn(name = "note_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> sharedWith = new HashSet<>();

	public void addSharedWith(User user) {
		sharedWith.add(user);
	}

}