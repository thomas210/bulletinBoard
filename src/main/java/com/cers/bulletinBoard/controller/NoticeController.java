package com.cers.bulletinBoard.controller;

import com.cers.bulletinBoard.model.Notice;
import com.cers.bulletinBoard.repository.NoticeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * Notice Repository
 */
@RestController
@CrossOrigin(origins = "http://localhost:4200") // It was necessary to insert the front project url so that it can have permission to access the routes.
@RequestMapping({"/notices"})
public class NoticeController {

    private NoticeRepository repository;

    NoticeController(NoticeRepository noticeRepository) {
        this.repository = noticeRepository;
    }

    /**
     * Method to return all warnings present in the system. Notices will be paged in sets of 5 notices and are
     * sorted by creation date. Notices that have not yet been opened, that have the preview date filled in,
     * are on the front, for the sake of importance.
     * @param pageNumber Page number on pagination.
     * @return List containing the initial information regarding pagination, how many pages it has,
     * number of elements per page, current page, followed by the registered warnings.
     */
    @GetMapping
    public Page<Notice> findAll(@RequestParam("page") int pageNumber){
        Pageable pagination = PageRequest.of(pageNumber, 5, Sort.by("visualizationDate"));
        return this.repository.findAll(pagination);
    }

    /**
     *Method for viewing a warning only. If the notice has not been opened yet, the viewing date is filled in.
     * @param id of the Notice.
     * @return the model Notice
     */
    @GetMapping(path="/{id}")
    public ResponseEntity read(@PathVariable long id){
        return this.repository.findById(id)
                .map(record -> {
                    if (record.getVisualizationDate() == null) {
                        record.setVisualizationDate(new Date());
                        record = this.repository.save(record);
                    }
                    return ResponseEntity.ok().body(record);
                }).orElse(ResponseEntity.notFound().build());
    }

    /**
     * Method for creating the notice. The only mandatory field is the title.
     * The creation date is populated based on the current date.
     * @param model request of the Notice.
     * @return
     */
    @PostMapping
    public Notice create(@RequestBody Notice model){
        model.setPublicationDate(new Date());
        return this.repository.save(model);
    }

    /**
     * Method to update the notice. If the notice has already been opened then the
     * display date field is cleared and it is treated as a new notice.
     * @param id of the Notice
     * @param model request of changes.
     * @return
     */
    @PutMapping(value = "/{id}")
    public ResponseEntity update(@PathVariable("id") long id, @RequestBody Notice model){
        return this.repository.findById(id)
                .map(record -> {
                    record.setTitle(model.getTitle());
                    record.setDescription(model.getDescription());
                    record.setVisualizationDate(null);
                    Notice modelUpdated = this.repository.save(record);
                    return ResponseEntity.ok().body(modelUpdated);
                }).orElse(ResponseEntity.notFound().build());
    }

    /**
     * Method to delete the notice.
     * @param id
     * @return
     */
    @DeleteMapping(path = {"/{id}"})
    public ResponseEntity <?> delete(@PathVariable long id) {
        return this.repository.findById(id)
                .map(record -> {
                    this.repository.deleteById(id);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }




}
