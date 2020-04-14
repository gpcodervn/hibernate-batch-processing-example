package com.gpcoder.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Data
@Entity
@Table
public class Tag implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tag_generator")
	@SequenceGenerator(name="tag_generator", sequenceName = "tag_id_seq", allocationSize=1)
	private Long id;

	@Column
	private String name;
}
