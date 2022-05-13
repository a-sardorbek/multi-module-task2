package com.epam.esm.service;
import com.epam.esm.dto.*;

import java.util.List;

public interface GiftCertificateService {

     int create(GiftCertificateDto giftCertificateDto);
     GiftResponse findById(String id);
     List<GiftCertificateDto> findAll();
     int deleteUsingId(String id);
     int insertTagIdToGift(String tagId, String giftId);
     List<GiftCertificateWithTagDto> searchByNameOrDescription(SearchGiftDto searchGiftDto);
     GiftResponse updateGiftById(String id, GiftUpdateDto giftUpdateDto);
     List<GiftCertificateWithTagDtoNew> getListCertificateWithTagsNew();

}
