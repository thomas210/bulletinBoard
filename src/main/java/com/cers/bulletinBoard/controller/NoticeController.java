package com.cers.bulletinBoard.controller;

import com.cers.bulletinBoard.model.Notice;
import com.cers.bulletinBoard.repository.NoticeRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping({"/notices"})
public class NoticeController {

    private NoticeRepository repository;

    NoticeController(NoticeRepository noticeRepository) {
        this.repository = noticeRepository;
    }

    @GetMapping
    public List<Notice> findAll(){
        return this.repository.findAll();
    }

//    TODO: ajeitar aqui depois.
    @GetMapping(path="/{id}")
    public ResponseEntity read(@PathVariable long id){
        return this.repository.findById(id).map(record -> ResponseEntity.ok().body(record)).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Notice create(@RequestBody Notice model){
        model.setPublicationDate(new Date());
        return this.repository.save(model);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity update(@PathVariable("id") long id, @RequestBody Notice model){
        return this.repository.findById(id)
                .map(record -> {
                    record.setTitle(model.getTitle());
                    record.setDescription(model.getDescription());
                    Notice modelUpdated = this.repository.save(record);
                    return ResponseEntity.ok().body(modelUpdated);
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping(path = {"/{id}"})
    public ResponseEntity <?> delete(@PathVariable long id) {
        return this.repository.findById(id)
                .map(record -> {
                    this.repository.deleteById(id);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }




}