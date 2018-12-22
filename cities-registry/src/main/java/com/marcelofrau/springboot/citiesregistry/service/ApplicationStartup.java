package com.marcelofrau.springboot.citiesregistry.service;

import com.marcelofrau.springboot.citiesregistry.model.City;
import com.marcelofrau.springboot.citiesregistry.model.CityConnection;
import com.marcelofrau.springboot.citiesregistry.repository.CityRepository;
import com.marcelofrau.springboot.citiesregistry.repository.CityConnectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

/**
 * This class is used to register initial data, it can be changed or adjusted to
 * have newer or lesser information. This is used to fulfill the initial data
 * with controlled data to be used on the springboot.
 */
@Component
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {

    private final CityRepository cityRepository;
    private final CityConnectionRepository cityTimeConnectionRepository;

    public ApplicationStartup(
            @Autowired CityRepository cityRepository,
            @Autowired CityConnectionRepository cityTimeConnectionRepository) {
        this.cityRepository = cityRepository;
        this.cityTimeConnectionRepository = cityTimeConnectionRepository;
    }

    /**
     * This will be triggered when the application starts.
     * @param event Event indicating the application is ready to use.
     */
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        initializeCities();
    }

    /**
     * This method initializes all the initial cities and connections
     * to be tested on the springboot. This is just for initial purposes,
     * it can be removed later on the project.
     *
     * The cities and connections can be inserted on the controllers.
     * For more information please see the respective classes.
     *
     * @see com.marcelofrau.springboot.citiesregistry.controller.CitiesController
     * @see com.marcelofrau.springboot.citiesregistry.controller.ConnectionsController
     */
    @Transactional
    private void initializeCities() {
        if (cityRepository.count() > 0) {
            //database already initialized here
            return;
        }

        final City campinas = new City("Campinas", "SP");
        final City saoPaulo = new City("Sao Paulo", "SP");
        final City guaratingueta = new City("Guaratingueta", "SP");
        final City rioDeJaneiro = new City("Rio de Janeiro", "RJ");
        final City patoBranco = new City("Pato Branco", "RS");
        final City campinaGrande = new City("Campina Grande", "PB");
        final City salvador = new City("Salvador", "BA");
        final City maceio = new City("Maceio", "PA");
        final City curitiba = new City("Curitiba", "PR");

        cityRepository.save(campinas);
        cityRepository.save(saoPaulo);
        cityRepository.save(guaratingueta);
        cityRepository.save(rioDeJaneiro);
        cityRepository.save(patoBranco);
        cityRepository.save(campinaGrande);
        cityRepository.save(salvador);
        cityRepository.save(maceio);
        cityRepository.save(curitiba);

        cityTimeConnectionRepository.save(new CityConnection(campinas, saoPaulo, 1d));
        cityTimeConnectionRepository.save(new CityConnection(saoPaulo, rioDeJaneiro, 5d));
        cityTimeConnectionRepository.save(new CityConnection(saoPaulo, maceio, 8d));
        cityTimeConnectionRepository.save(new CityConnection(campinas, maceio, 16d));
        cityTimeConnectionRepository.save(new CityConnection(rioDeJaneiro, maceio, 2d));
//        cityTimeConnectionRepository.save(new CityConnection(saoPaulo, guaratingueta, 4d));
//        cityTimeConnectionRepository.save(new CityConnection(saoPaulo, salvador, 50d));
//        cityTimeConnectionRepository.save(new CityConnection(saoPaulo, campinaGrande, 80d));
//        cityTimeConnectionRepository.save(new CityConnection(campinas, campinaGrande, 8d));
//        cityTimeConnectionRepository.save(new CityConnection(patoBranco, guaratingueta, 4d));
//        cityTimeConnectionRepository.save(new CityConnection(curitiba, salvador, 50d));
//        cityTimeConnectionRepository.save(new CityConnection(patoBranco, saoPaulo, 32d));
//        cityTimeConnectionRepository.save(new CityConnection(curitiba, saoPaulo, 24d));
//        cityTimeConnectionRepository.save(new CityConnection(campinas, curitiba, 24d));

    }
}
