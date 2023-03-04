package com.driver.services.impl;

import com.driver.model.Admin;
import com.driver.model.Country;
import com.driver.model.CountryName;
import com.driver.model.ServiceProvider;
import com.driver.repository.AdminRepository;
import com.driver.repository.CountryRepository;
import com.driver.repository.ServiceProviderRepository;
import com.driver.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    AdminRepository adminRepository1;

    @Autowired
    ServiceProviderRepository serviceProviderRepository1;

    @Autowired
    CountryRepository countryRepository1;

    @Override
    public Admin register(String username, String password) {
        //create an admin and return
        Admin admin = new Admin();
        admin.setPassword(password);
        admin.setUsername(username);

        adminRepository1.save(admin);
        return admin;
    }

    @Override
    public Admin addServiceProvider(int adminId, String providerName) {
        //add a serviceProvider under the admin and return updated admin
        Admin admin = adminRepository1.findById(adminId).get();
        ServiceProvider serviceProvider = new ServiceProvider();

        serviceProvider.setAdmin(admin);
        serviceProvider.setName(providerName);

        admin.getServiceProviders().add(serviceProvider);
        adminRepository1.save(admin);

        return admin;
    }

    @Override
    public ServiceProvider addCountry(int serviceProviderId, String countryName) throws Exception{
        //add a country under the serviceProvider and return respective service provider
        //country name would be a 3-character string out of ind, aus, usa, chi, jpn. Each character can be in uppercase or lowercase
        //In case country name is not amongst the above mentioned strings, throw "Country not found" exception

        if(countryName.equalsIgnoreCase("ind") || countryName.equalsIgnoreCase("usa") || countryName.equalsIgnoreCase("aus")||countryName.equalsIgnoreCase("jpn")||countryName.equalsIgnoreCase("chi")){

            Country country = new Country();
            ServiceProvider serviceProvider = serviceProviderRepository1.findById(serviceProviderId).get();

            if (countryName.equalsIgnoreCase("ind")){
                country.setCountryName(CountryName.IND);
                country.setCode(CountryName.IND.toCode());
            }
            if (countryName.equalsIgnoreCase("usa")){
                country.setCountryName(CountryName.USA);
                country.setCode(CountryName.USA.toCode());
            }
            if (countryName.equalsIgnoreCase("aus")){
                country.setCountryName(CountryName.AUS);
                country.setCode(CountryName.AUS.toCode());
            }
            if (countryName.equalsIgnoreCase("jpn")){
                country.setCountryName(CountryName.JPN);
                country.setCode(CountryName.JPN.toCode());
            }
            if (countryName.equalsIgnoreCase("chi")){
                country.setCountryName(CountryName.CHI);
                country.setCode(CountryName.CHI.toCode());
            }
            country.setServiceProvider(serviceProvider);
            serviceProvider.getCountryList().add(country);
            serviceProviderRepository1.save(serviceProvider);

            return serviceProvider;
        }
        else
            throw new Exception("Country not found");
    }
}