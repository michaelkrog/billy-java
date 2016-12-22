package com.previsto.mapping;

import com.previsto.model.City;
import com.previsto.model.Contact;
import com.previsto.model.ZipCode;
import java.util.List;

public abstract class ContactMapping {
 
    private ResponseMeta meta;
    private List<City> cities;
    private List<ZipCode> zipcodes;

    public ResponseMeta getMeta() {
        return meta;
    }

    public void setMeta(ResponseMeta meta) {
        this.meta = meta;
    }

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }

    public List<ZipCode> getZipcodes() {
        return zipcodes;
    }

    public void setZipcodes(List<ZipCode> zipcodes) {
        this.zipcodes = zipcodes;
    }

    protected void resolveExtraData(Contact contact) {
        if (contact.getCityId() != null && getCities() != null) {
            for (City city : getCities()) {
                if (city.getId().equals(contact.getCityId())) {
                    contact.setCityText(city.getName());
                    contact.setCountryId(city.getCountryId());
                }
            }
        }
        if (contact.getZipcodeId() != null && getZipcodes() != null) {
            for (ZipCode zip : getZipcodes()) {
                if (zip.getId().equals(contact.getZipcodeId())) {
                    contact.setCityText(zip.getZipcode());
                    contact.setCountryId(zip.getCountryId());
                }
            }
        }
    }
    
}