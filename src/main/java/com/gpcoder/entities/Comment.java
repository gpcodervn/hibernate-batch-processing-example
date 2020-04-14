package com.gpcoder.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Data
@Entity
@Table
public class Comment implements Serializable {

	@Id
	@GeneratedValue
	private UUID id;

	@Column
	private String comment;
}
