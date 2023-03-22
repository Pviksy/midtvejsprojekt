package com.opgavedb_1.entities;

//Mikkel

public class DataSingleton {

    /*
    * Da det er valgt at en klients personlige oplysninger skal kunne redigeres af
    * en instructor i en seperat scene, bliver vi nødt til at overføre vores data
    * fra den første af disse (Klientliste.fxml / ClientlistController.java).
    * Singleton klassens formål er at opbevare klienten i sin ene instans,
    * så klientens information kan hentes ned fra databasen i den anden scene
    * (RedigerKlient.fxml / UpdateClientController.java).
    */

    private static final DataSingleton instance = new DataSingleton();

    private User client;

    private DataSingleton(){}

    public static DataSingleton getInstance() {
        return instance;
    }

    public User getClient() {
        return client;
    }

    public void setClient(User client) {
        this.client = client;
    }
}
