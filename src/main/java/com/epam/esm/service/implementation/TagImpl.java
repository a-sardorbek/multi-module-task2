package com.epam.esm.service.implementation;

import com.epam.esm.dto.TagDto;
import com.epam.esm.exceptions.custom.TagExistException;
import com.epam.esm.exceptions.custom.TagNotFoundException;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.TagService;
import com.epam.esm.utils.ServiceUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagImpl implements TagService {

    private TagRepository tagRepository;
    public TagImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public int create(TagDto tagDto) {
        boolean tagExists = tagRepository.checkExists(tagDto.getId());
        if(tagExists){
            throw new TagExistException("Tag already exists with id: "+tagDto.getId());
        }
        return tagRepository.insert(tagDto);
    }


    @Override
    public TagDto findById(String id) {
        if(!ServiceUtils.checkIsNumeric(id)) {
            throw new TagNotFoundException("Make sure tag with id: "+id+" is correct");
        }
        if(tagRepository.checkExists(Integer.parseInt(id)) == false) {
            throw new TagNotFoundException("Tag not found with id: "+id);
        }
        return tagRepository.getById(Integer.parseInt(id));
    }


    @Override
    public List<TagDto> findAll() {
        if(tagRepository.getAll().size()==0){
            throw new TagNotFoundException("Empty list");
        }
        return tagRepository.getAll();
    }


    @Override
    public int deleteUsingId(String id) {
        if(!ServiceUtils.checkIsNumeric(id)) {
            throw new TagNotFoundException("Make sure tag with id: "+id+" is correct");
        }
        if(tagRepository.checkExists(Integer.parseInt(id)) == false) {
            throw new TagNotFoundException("Tag not found with id: "+id);
        }else if(tagRepository.checkTagConnectedToGift(Integer.parseInt(id))){
            return tagRepository.deleteById(Integer.parseInt(id), true);
        }
        return tagRepository.deleteById(Integer.parseInt(id), false);
    }


    @Override
    public TagDto updateTagById(String id, String tagName){
        if(!ServiceUtils.checkIsNumeric(id)) {
            throw new TagNotFoundException("Make sure tag with id: "+id+" is correct");
        }
        if(tagRepository.checkExists(Integer.parseInt(id)) == false) {
            throw new TagNotFoundException("Tag not found with id: "+id);
        }
        return tagRepository.updateById(Integer.parseInt(id),tagName);
    }

}
