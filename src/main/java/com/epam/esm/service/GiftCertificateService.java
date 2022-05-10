package com.epam.esm.service;

import com.epam.esm.base.RootService;
import com.epam.esm.dto.*;
import com.epam.esm.exceptions.custom.GiftCertificateNotFoundException;
import com.epam.esm.exceptions.custom.TagNotFoundException;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.utils.ServiceUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GiftCertificateService implements RootService<GiftCertificateDto> {

    @Autowired
    private GiftCertificateRepository giftCertificateRepository;

    @Autowired
    private TagRepository tagRepository;


    @Override
    public int create(GiftCertificateDto giftCertificateDto) {
        int newGift = 0;
        boolean giftExists = giftCertificateRepository.checkExists(giftCertificateDto.getId());
        if(!giftExists){
            newGift = giftCertificateRepository.insert(giftCertificateDto);
        }
        return newGift;
    }


    @Override
    public GiftCertificateDto findById(String id) {
        if(!ServiceUtils.checkIsNumeric(id)) {
            throw new GiftCertificateNotFoundException();
        }
        if(giftCertificateRepository.checkExists(Integer.parseInt(id)) == false) {
            throw new GiftCertificateNotFoundException();
        }
        return giftCertificateRepository.getById(Integer.parseInt(id));
    }



    @Override
    public List<GiftCertificateDto> findAll() {
        return giftCertificateRepository.getAll();
    }



    @Override
    public int deleteUsingId(String id) {
        if(!ServiceUtils.checkIsNumeric(id)) {
            throw new GiftCertificateNotFoundException();
        }
        if(giftCertificateRepository.checkExists(Integer.parseInt(id)) == false) {
            throw new GiftCertificateNotFoundException();
        }
        return giftCertificateRepository.deleteById(Integer.parseInt(id));
    }


    public Integer insertTagIdToGift(String tag_id, String gift_id){
        if(!ServiceUtils.checkIsNumeric(tag_id) || !ServiceUtils.checkIsNumeric(gift_id)) {
            throw new GiftCertificateNotFoundException();
        }
        boolean gift_by_id = giftCertificateRepository.checkExists(Integer.parseInt(gift_id));
        boolean tag_by_id = tagRepository.checkExists(Integer.parseInt(tag_id));

        if(gift_by_id==false) {
            throw new GiftCertificateNotFoundException();
        }

        if(tag_by_id==false) {
            throw new TagNotFoundException();
        }

        return giftCertificateRepository.insertWithTag(Integer.parseInt(tag_id),Integer.parseInt(gift_id));


    }

    public List<GiftCertificateWithTag> searchByNameOrDescription(SearchGiftDto searchGiftDto){
        List<GiftCertificateWithTag> giftCertificateWithTags = null;
        String name = searchGiftDto.getName();
        String description = searchGiftDto.getDescription();
        String sortBy = searchGiftDto.getSortBy();
        String ascDesc = searchGiftDto.getAscDesc();
        if (StringUtils.isBlank(name)) {
            giftCertificateWithTags = giftCertificateRepository.searchGiftByPartNameDescription(
                    searchGiftDto.getName(),
                    searchGiftDto.getDescription(),sortBy, ascDesc,1);
        }

        if (StringUtils.isBlank(description)) {
            giftCertificateWithTags = giftCertificateRepository.searchGiftByPartNameDescription(
                    searchGiftDto.getName(),
                    searchGiftDto.getDescription(),sortBy, ascDesc,0);
        }

        if (StringUtils.isNotBlank(name) && StringUtils.isNotBlank(description)) {
            giftCertificateWithTags = giftCertificateRepository.searchGiftByPartNameDescription(
                    searchGiftDto.getName(),
                    searchGiftDto.getDescription(),sortBy, ascDesc,2);
        }

        return giftCertificateWithTags;
    }


    public List<GiftCertificateWithTag> getListCertificateWithTags() {
        return giftCertificateRepository.getListGiftCertificatesWithTags();
    }


    public GiftCertificateDto updateGiftById(String id, GiftUpdateDto giftUpdateDto){
        if(!ServiceUtils.checkIsNumeric(id)) {
            throw new GiftCertificateNotFoundException();
        }
        if(giftCertificateRepository.checkExists(Integer.parseInt(id)) == false) {
            throw new GiftCertificateNotFoundException();
        }
        return giftCertificateRepository.updateById(Integer.parseInt(id),giftUpdateDto);
    }

}
