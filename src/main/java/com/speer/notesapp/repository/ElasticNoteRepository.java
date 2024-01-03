package com.speer.notesapp.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.speer.notesapp.model.Note;
 
public interface ElasticNoteRepository extends ElasticsearchRepository<Note, Long> {

}
