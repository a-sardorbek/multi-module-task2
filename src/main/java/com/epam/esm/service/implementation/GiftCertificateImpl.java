package com.epam.esm.service.implementation;

import com.epam.esm.dto.*;
import com.epam.esm.exceptions.custom.*;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.utils.ServiceUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GiftCertificateImpl implements GiftCertificateService {


    private GiftCertificateRepository giftCertificateRepository;
    private TagRepository tagRepository;

    public GiftCertificateImpl(GiftCertificateRepository giftCertificateRepository, TagRepository tagRepository) {
        this.giftCertificateRepository = giftCertificateRepository;
        this.tagRepository = tagRepository;
    }

    @Override
    public int create(GiftCertificateDto giftCertificateDto) {
        boolean giftExists = giftCertificateRepository.checkExists(giftCertificateDto.getId());
        if(giftExists){
            throw new GiftCertificateExistException("Gift Certificate already exists with id: "+giftCertificateDto.getId());
        }
        return giftCertificateRepository.insert(giftCertificateDto);
    }


    @Override
    public GiftResponse findById(String id) {
        if(!ServiceUtils.checkIsNumeric(id)) {
            throw new GiftCertificateNotFoundException("Make sure that id: "+id+" is correct");
        }
        if(giftCertificateRepository.checkExists(Integer.parseInt(id)) == false) {
            throw new GiftCertificateNotFoundException("Gift Certificate does not exists with id: "+id);
        }
        return giftCertificateRepository.getById(Integer.parseInt(id));
    }


    @Override
    public List<GiftCertificateDto> findAll() {
        if(giftCertificateRepository.getAll().size()==0){
            throw new GiftCertificateNotFoundException("Empty list");
        }
        return giftCertificateRepository.getAll();
    }


    @Override
    public int deleteUsingId(String id) {
        if(!ServiceUtils.checkIsNumeric(id)) {
            throw new GiftCertificateNotFoundException("Make sure that id: "+id+" is correct");
        }
        if(giftCertificateRepository.checkExists(Integer.parseInt(id)) == false) {
            throw new GiftCertificateNotFoundException("Gift Certificate does not exists with id: "+id);
        }else if(giftCertificateRepository.checkTagConnectedToGift(Integer.parseInt(id))){
            return giftCertificateRepository.deleteById(Integer.parseInt(id), true);
        }
        return giftCertificateRepository.deleteById(Integer.parseInt(id),false);
    }


    @Override
    public int insertTagIdToGift(String tagId, String giftId){
        if(!ServiceUtils.checkIsNumeric(tagId) || tagRepository.checkExists(Integer.parseInt(tagId)) == false) {
            throw new TagNotFoundException("Tag not found by id: "+tagId);
        }
        if(!ServiceUtils.checkIsNumeric(giftId) || giftCertificateRepository.checkExists(Integer.parseInt(giftId)) == false) {
            throw new GiftCertificateNotFoundException("Gift Certificate does not exists with id: "+giftId);
        }

        if(giftCertificateRepository.checkTagIdAndGiftIdConnected(Integer.parseInt(giftId),Integer.parseInt(tagId))){
            throw new GiftAndTagWithGivenIdsConnectedException("Gift id: "+giftId+" and Tag id: "+tagId+" already connected");
        }

        return giftCertificateRepository.insertWithTag(Integer.parseInt(tagId),Integer.parseInt(giftId));


    }

    @Override
    public List<GiftCertificateWithTagDtoNew> searchByNameOrDescription(SearchGiftDto searchGiftDto){

        if(checkValidInput(searchGiftDto.getSortBy(),searchGiftDto.getAscDesc())==false){
            throw new InputNotMatchException("Enter 'name' or 'description' for sortBy, 'asc' or 'desc' for ascDesc fields");
        }

        if (StringUtils.isBlank(searchGiftDto.getName())) {
            return giftCertificateRepository.searchGiftByPartNameDescription(
                    searchGiftDto.getName(),
                    searchGiftDto.getDescription(),
                    searchGiftDto.getSortBy(),
                    searchGiftDto.getAscDesc(),
                    1);
        }

        if (StringUtils.isBlank(searchGiftDto.getDescription())) {
            return  giftCertificateRepository.searchGiftByPartNameDescription(
                    searchGiftDto.getName(),
                    searchGiftDto.getDescription(),
                    searchGiftDto.getSortBy(),
                    searchGiftDto.getAscDesc(),
                    0);
        }

        if (StringUtils.isNotBlank(searchGiftDto.getName()) && StringUtils.isNotBlank(searchGiftDto.getDescription())) {
            return giftCertificateRepository.searchGiftByPartNameDescription(
                    searchGiftDto.getName(),
                    searchGiftDto.getDescription(),
                    searchGiftDto.getSortBy(),
                    searchGiftDto.getAscDesc(),
                    2);
        }
        return new ArrayList<>();

    }



    @Override
    public List<GiftCertificateWithTagDtoNew> getListCertificateWithTagsNew() {
        return giftCertificateRepository.getListGiftCertificatesWithTagsNew();
    }


    @Override
    public GiftResponse updateGiftById(String id, GiftUpdateDto giftUpdateDto){
        if(!ServiceUtils.checkIsNumeric(id)) {
            throw new GiftCertificateNotFoundException("Make sure that id: "+id+" is correct");
        }
        if(giftCertificateRepository.checkExists(Integer.parseInt(id)) == false) {
            throw new GiftCertificateNotFoundException("Gift Certificate does not exists with id: "+id);
        }

        return giftCertificateRepository.updateById(Integer.parseInt(id),giftUpdateDto);
    }


    private boolean checkValidInput(String sortBy, String ascDesc) {
        if(sortBy.equals("name") || sortBy.equals("description") && ascDesc.equals("asc") || ascDesc.equals("desc")){
            return true;
        }
        return false;
    }

}
