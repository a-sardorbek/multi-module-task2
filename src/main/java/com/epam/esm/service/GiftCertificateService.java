package com.epam.esm.service;
import com.epam.esm.dto.*;

import java.util.List;

public interface GiftCertificateService {

     int create(GiftCertificateDtoNew giftCertificateDto);
     GiftResponse findById(String id);
     List<GiftCertificateDto> findAll();
     int deleteUsingId(String id);
     List<GiftCertificateWithTagDtoNew> searchByNameOrDescription(SearchGiftDto searchGiftDto);
     GiftResponse updateGiftById(String id, GiftUpdateDtoNew giftUpdateDto);
     List<GiftCertificateWithTagDtoNew> getListCertificateWithTagsNew();

}
