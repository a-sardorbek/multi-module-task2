package com.epam.esm.service.implementation;

import com.epam.esm.dto.*;
import com.epam.esm.exceptions.custom.*;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.utils.ServiceUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
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
    public int create(GiftCertificateDtoNew giftCertificateDto) {
        if (StringUtils.isBlank(giftCertificateDto.getName())) {
            throw new NameCannotBeBlankException("Enter name first!");
        }

        if (StringUtils.isBlank(giftCertificateDto.getDescription())) {
            throw new NameCannotBeBlankException("Enter description first!");
        }

        if (!NumberUtils.isParsable(String.valueOf(giftCertificateDto.getPrice()))) {
            String price = "";
            if(giftCertificateDto.getPrice()==null){
                price = "is empty";
            }else {
                price = giftCertificateDto.getPrice();
            }
            throw new PriceIncorrectException("The price " + price + ", not valid");
        }

        if (!NumberUtils.isParsable(String.valueOf(giftCertificateDto.getDuration()))) {
            String duration = "";
            if(giftCertificateDto.getDuration()==null){
                duration = "is empty";
            }else {
                duration = giftCertificateDto.getDuration();
            }
            throw new PriceIncorrectException("The duration " + duration + ", not valid");
        }

        boolean giftExists = giftCertificateRepository.checkExistsNew(giftCertificateDto.getName());
        if (giftExists) {
            throw new GiftCertificateExistException("Gift Certificate already exists with Name: " + giftCertificateDto.getName());
        }
        int createdSuccessfully = giftCertificateRepository.insert(giftCertificateDto);

        if(giftCertificateDto.getTagDtoNewList() != null){
            for(TagDtoNew tagDtoNew: giftCertificateDto.getTagDtoNewList()){
                if(tagRepository.checkExistsNew(tagDtoNew.getName())==false){
                    int idTag = tagRepository.getNextId();
                    int idGift = giftCertificateRepository.getLastId();
                    tagRepository.insertNew(tagDtoNew.getName());
                    giftCertificateRepository.insertWithTag(idTag,idGift);
                }else {
                    int idTagExist = tagRepository.getLastTagId(tagDtoNew.getName());
                    int idGift = giftCertificateRepository.getLastId();
                    giftCertificateRepository.insertWithTag(idTagExist,idGift);
                }
            }
        }
        return createdSuccessfully;
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
    public List<GiftCertificateWithTagDtoNew> searchByNameOrDescription(SearchGiftDto searchGiftDto) {

        if(StringUtils.isBlank(searchGiftDto.getSortBy())) searchGiftDto.setSortBy("name");
        if(StringUtils.isBlank(searchGiftDto.getAscDesc())) searchGiftDto.setAscDesc("asc");

        if(checkSortBy(searchGiftDto.getSortBy())){
            if (searchGiftDto.getSortBy().equals("date")) searchGiftDto.setSortBy("create_date");
        }else {
            throw new InputNotMatchException("Enter 'name' or 'date' for sortBy");
        }

        if (checkAscDesc(searchGiftDto.getAscDesc())==false){
            throw new InputNotMatchException("Enter 'asc' or 'desc' for ascDes");
        }

        if (StringUtils.isBlank(searchGiftDto.getName()) && StringUtils.isNotBlank(searchGiftDto.getDescription())) {
                return giftCertificateRepository.searchGiftByPartNameDescription(
                        searchGiftDto.getName(),
                        searchGiftDto.getDescription(),
                        searchGiftDto.getSortBy(),
                        searchGiftDto.getAscDesc(),
                        1);
        }

        if (StringUtils.isNotBlank(searchGiftDto.getName()) && StringUtils.isBlank(searchGiftDto.getDescription())) {
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

        if (StringUtils.isBlank(searchGiftDto.getName()) && StringUtils.isBlank(searchGiftDto.getDescription())) {
            return giftCertificateRepository.searchGiftByPartNameDescription(
                    searchGiftDto.getName(),
                    searchGiftDto.getDescription(),
                    searchGiftDto.getSortBy(),
                    searchGiftDto.getAscDesc(),
                    3);
        }
        return new ArrayList<>();
    }



    @Override
    public List<GiftCertificateWithTagDtoNew> getListCertificateWithTagsNew() {
        return giftCertificateRepository.getListGiftCertificatesWithTagsNew();
    }


    @Override
    public GiftResponse updateGiftById(String id, GiftUpdateDtoNew giftUpdateDto){
        if(!ServiceUtils.checkIsNumeric(id)) {
            throw new GiftCertificateNotFoundException("Make sure that id: "+id+" is correct");
        }
        if(giftCertificateRepository.checkExists(Integer.parseInt(id)) == false) {
            throw new GiftCertificateNotFoundException("Gift Certificate does not exists with id: "+id);
        }

        if (!NumberUtils.isParsable(String.valueOf(giftUpdateDto.getPrice()))) {
            if(giftUpdateDto.getPrice()!=null) {
                throw new PriceIncorrectException("The price " + giftUpdateDto.getPrice() + " is not valid");
            }
        }

        if(giftUpdateDto.getTagDtoNewList() != null){
            for(TagDtoNew tagDtoNew: giftUpdateDto.getTagDtoNewList()){
                int idTag = tagRepository.getNextId();
                if(tagRepository.checkExistsNew(tagDtoNew.getName())==false){
                    tagRepository.insertNew(tagDtoNew.getName());
                    giftCertificateRepository.insertWithTag(idTag,Integer.parseInt(id));
                }else if(giftCertificateRepository.checkTagIdAndGiftIdConnected(Integer.parseInt(id),tagRepository.getLastTagId(tagDtoNew.getName()))==false){
                    int idTagExist = tagRepository.getLastTagId(tagDtoNew.getName());
                    giftCertificateRepository.insertWithTag(idTagExist,Integer.parseInt(id));
                }
            }
        }

        return giftCertificateRepository.updateById(Integer.parseInt(id),giftUpdateDto);
    }

    private boolean checkAscDesc(String ascDesc) {
        if(ascDesc.equals("asc")|| ascDesc.equals("desc")){
            return true;
        }
        return false;
    }

    private boolean checkSortBy(String sortBy) {
        if(sortBy.equals("name")|| sortBy.equals("date")){
            return true;
        }
        return false;
    }


}
