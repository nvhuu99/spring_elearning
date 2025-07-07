package com.demo.models.dataconvert;

import com.demo.models.enums.CertificateName;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class CertificateNameConverter implements AttributeConverter<CertificateName, String> {

    @Override
    public String convertToDatabaseColumn(CertificateName attribute) {
        return attribute != null ? attribute.getValue() : null;
    }

    @Override
    public CertificateName convertToEntityAttribute(String dbData) {
        return dbData != null ? CertificateName.fromValue(dbData) : null;
    }
}